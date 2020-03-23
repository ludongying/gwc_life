package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.seven.gwc.modular.lawrecord.dao.InquireMapper;
import com.seven.gwc.modular.lawrecord.service.InquireService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
        vo.setRecordId(vo.getId());
        this.saveOrUpdate(vo);
        //补录信息新建修改
        LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(InquireEntity::getId,vo.getId()).eq(InquireEntity::getDeleteFlag,Boolean.TRUE);
        List<InquireEntity> list = this.list(wrapper);
        List<InquireEntity> inquireEntities = vo.listInquire();
        if(Objects.nonNull(inquireEntities) && !inquireEntities.isEmpty()){
            inquireEntities.forEach(inquireEntity -> inquireEntity.setRecordId(vo.getId()));
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
        return baseResult;
    }

    @Override
    public InquireDTO detail(String id) {
        InquireEntity inquireEntity = this.getById(id);
        if(Objects.nonNull(inquireEntity)){
            InquireDTO inquireDTO=new InquireDTO();
            BeanUtils.copyProperties(inquireEntity,inquireDTO);
            inquireDTO.setAddress();
            //补录信息
            LambdaQueryWrapper<InquireEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.ne(InquireEntity::getId,id).eq(InquireEntity::getRecordId,id).eq(InquireEntity::getDeleteFlag,Boolean.TRUE);
            List<InquireEntity> list = this.list(wrapper);
            if(Objects.nonNull(list) && !list.isEmpty()){
                inquireDTO.setInquireContent(list);
            }
            return inquireDTO;
        }
        return null;
    }


}
