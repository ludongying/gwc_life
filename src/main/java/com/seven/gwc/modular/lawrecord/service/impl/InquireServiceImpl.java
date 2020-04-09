package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.InquireMapper;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.InquireProduceDO;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.InquireProduceSupplementDO;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.dto.InquireSupplementDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.seven.gwc.modular.lawrecord.enums.ShipStatusEnum;
import com.seven.gwc.modular.lawrecord.service.*;
import com.seven.gwc.modular.lawrecord.vo.InquireSupplementVO;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * description : 询问笔录服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Service
@Slf4j
public class InquireServiceImpl extends ServiceImpl<InquireMapper, InquireEntity> implements InquireService {
    @Autowired
    private LawRecordService lawRecordService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private DecisionService decisionService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private InquireMapper inquireMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateInquire(InquireVO vo) {
        String id=vo.getId();
        //新建
        if(Objects.isNull(id) || id.trim().isEmpty()){
            LawRecordEntity lawRecordEntity =
                    lawRecordService.createLawRecord(vo.getUserId(),vo.getLawType());
            vo.setId(lawRecordEntity.getId());
        }
        if(ShipStatusEnum.ESCAPE.getCode().equals(vo.getShipStatus())){
            decisionService.updateSeverity(vo.getId());
        }
        vo.setRecordId(vo.getId());
        this.saveOrUpdate(vo);
        //补录信息新建修改
        LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(InquireEntity::getId,vo.getId()).eq(InquireEntity::getDeleteFlag,Boolean.TRUE);
        List<InquireEntity> list = this.list(wrapper);
        List<InquireSupplementVO> inquireSupplementVOS = vo.listInquire();
        List<InquireEntity> inquireEntities=null;
        if(Objects.nonNull(inquireSupplementVOS) && !inquireSupplementVOS.isEmpty()){
            inquireEntities=new ArrayList<>(inquireSupplementVOS);
            inquireEntities.forEach(inquireEntity -> inquireEntity.setRecordId(vo.getId()));
            //存储办案机关人员
            List<OperatorEntity> operatorList = inquireSupplementVOS.stream().map(InquireSupplementVO::getOperators)
                    .flatMap(Collection::stream).peek(i->i.init(i.getId(),vo.getUserId())).collect(Collectors.toList());
            if(!operatorList.isEmpty()){
               operatorService.saveOrUpdateBatch(operatorList);
            }
        }
        //原来有数据
        if(Objects.nonNull(list) && !list.isEmpty()) {
            if(Objects.nonNull(inquireEntities)){
                List<String> ids = inquireEntities.stream().map(InquireEntity::getId).filter(Objects::nonNull).collect(Collectors.toList());
                if(!ids.isEmpty()){
                    List<InquireEntity> left = list.stream().filter(inquireEntity -> !ids.contains(inquireEntity.getId())).peek(inquireEntity -> inquireEntity.setDeleteFlag(Boolean.FALSE)).collect(Collectors.toList());
                    if(!left.isEmpty()){ inquireEntities.addAll(left); }
                    this.saveOrUpdateBatch(inquireEntities);
                }else{
                    list.forEach(inquireEntity -> inquireEntity.setDeleteFlag(Boolean.FALSE));
                    inquireEntities.addAll(list);
                    this.saveOrUpdateBatch(inquireEntities);
                }
            }else{
                //全部删除
                list.forEach(inquireEntity -> inquireEntity.setDeleteFlag(Boolean.FALSE));
                this.saveOrUpdateBatch(list);
            }
        }else{
            if(Objects.nonNull(inquireEntities) && !inquireEntities.isEmpty()){
                this.saveOrUpdateBatch(inquireEntities);
            }
        }
        BaseResult baseResult = new BaseResult(true, "");
        baseResult.setContent(vo.getId());
        instrumentService.generateInstrument(vo.getId(), InquireEntity.class);
        return baseResult;
    }

    @Override
    public InquireDTO detail(String id) {
        InquireEntity inquireEntity = this.getById(id);
        if(Objects.nonNull(inquireEntity)){
            InquireDTO inquireDTO=new InquireDTO();
            BeanUtils.copyProperties(inquireEntity,inquireDTO);
           /* inquireDTO.setAddress();*/
            //补录信息
            inquireDTO.setInquireContent(getInquireSupplement(id));
            return inquireDTO;
        }
        return null;
    }

    @Override
    public BaseResult detailList(String id) {
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        LambdaQueryWrapper<InquireEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(InquireEntity::getRecordId, id);
        List<InquireEntity> inquireEntityList = inquireMapper.selectList(lambdaQuery);
        List<InquireDTO> datas = new ArrayList<>();
        for (InquireEntity inquireEntity : inquireEntityList) {
            InquireDTO inquireDTO = this.getInquireDTO(inquireEntity.getId());
            inquireDTO.setDetailContent();
            datas.add(inquireDTO);
        }
        result.setContent(datas);
        return result;
    }

    @Override
    public List<InquireDTO> inquireDTODetail(String id) {
        LambdaQueryWrapper<InquireEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(InquireEntity::getRecordId, id);
        List<InquireEntity> inquireEntityList = inquireMapper.selectList(lambdaQuery);
        List<InquireDTO> inquireDTOList = new ArrayList<>();
        for (InquireEntity inquireEntity : inquireEntityList) {
            InquireDTO inquireDTO = this.getInquireDTO(inquireEntity.getId());
            inquireDTO.setDetailContent();
            inquireDTOList.add(inquireDTO);
        }
        return inquireDTOList;
    }

    private InquireDTO getInquireDTO(String id) {
        LambdaQueryWrapper<InquireEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(InquireEntity::getId, id);
        InquireEntity inquireEntity = inquireMapper.selectOne(lambdaQuery);
        if(Objects.nonNull(inquireEntity)){
            InquireDTO inquireDTO=new InquireDTO();
            BeanUtils.copyProperties(inquireEntity,inquireDTO);
   /*         inquireDTO.setAddress();*/
            //补录信息
            inquireDTO.setInquireContent(getInquireSupplement(id));
            return inquireDTO;
        }
        return null;
    }

    @Override
    public  List<InquireSupplementDTO> getInquireSupplement(String id){
        LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(InquireEntity::getId,id).eq(InquireEntity::getRecordId,id).eq(InquireEntity::getDeleteFlag,Boolean.TRUE);
        List<InquireEntity> list = this.list(wrapper);
        if(Objects.nonNull(list) && !list.isEmpty()){
            List<InquireSupplementDTO> inquireSupplementDTOS=new ArrayList<>();
            for (InquireEntity entity : list) {
                inquireSupplementDTOS.add(parseInquireSupplementDTO(entity));
            }
            return inquireSupplementDTOS;
        }
        return null;
    }

    public InquireSupplementDTO parseInquireSupplementDTO(InquireEntity entity){
        InquireSupplementDTO dto=new InquireSupplementDTO();
        BeanUtils.copyProperties(entity,dto);
        dto.setTime();
        dto.setOperators(operatorService.getByRecord(dto.getId()));
        return dto;
    }


    @Override
    public Map<String, String> getSupplementParams(InquireEntity entity) {
        InquireSupplementDTO inquireSupplementDTO = parseInquireSupplementDTO(entity);
        if(Objects.nonNull(entity)){
            return new InquireProduceSupplementDO(inquireSupplementDTO).toMap();
        }
        return null;
    }

    @Override
    public Map<String, String> getParams(String id) {
        InquireEntity entity = this.getById(id);
        if(Objects.nonNull(entity)){
            return new InquireProduceDO(entity).toMap();
        }
        return null;
    }

    @Override
    public List<InquireEntity> getSupplement(String id){
        LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(InquireEntity::getDeleteFlag,Boolean.TRUE)
                .ne(InquireEntity::getId,id).eq(InquireEntity::getRecordId,id);
        return this.list(wrapper);
    }

    @Override
    public Integer getSupplementCount(String id){
        LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(InquireEntity::getDeleteFlag,Boolean.TRUE)
                .ne(InquireEntity::getId,id).eq(InquireEntity::getRecordId,id);
        return this.count(wrapper);

    }
}
