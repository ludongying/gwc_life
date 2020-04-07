package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.DecisionMapper;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.DecisionProduceDO;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.dto.PlotSeverityDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.PlotSeverityEnum;
import com.seven.gwc.modular.lawrecord.enums.RecordTipEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipStatusEnum;
import com.seven.gwc.modular.lawrecord.service.*;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * description : 决定服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-06
 */
@Service
@Slf4j
public class DecisionServiceImpl extends ServiceImpl<DecisionMapper, DecisionEntity> implements DecisionService {


    @Autowired
    private LawRecordService lawRecordService;
    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private InquireService inquireService;
    @Autowired
    private InquireSafeService inquireSafeService;
    @Autowired
    private InquisitionService inquisitionService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateDecision(DecisionVO vo) {
        String id=vo.getId();
        //新建
        if(Objects.isNull(id) || id.trim().isEmpty()){
            LawRecordEntity lawRecordEntity =
                    lawRecordService.createLawRecord(vo.getUserId(),vo.getLawType());
            vo.setId(lawRecordEntity.getId());
        }
        this.saveOrUpdate(vo);
        BaseResult baseResult = new BaseResult(true, "");
        baseResult.setContent(vo.getId());
        instrumentService.generateInstrument(vo.getId(), DecisionEntity.class);
        return baseResult;
    }

    @Override
    public DecisionDTO detail(String id) {
        DecisionEntity decisionEntity = this.getById(id);
        if(Objects.nonNull(decisionEntity)){
            DecisionDTO decisionDTO=new DecisionDTO();
            BeanUtils.copyProperties(decisionEntity,decisionDTO);
      /*      decisionDTO.setAddress(); */
            return decisionDTO;
        }
        return null;
    }


    @Override
    public Map<String, String> getParams(String id) {
        DecisionEntity entity = this.getById(id);
        if(Objects.nonNull(entity)){
            return new DecisionProduceDO(entity).toMap();
        }
        return null;
    }

    @Override
    public Boolean shipStatusIsEscape(String id, Integer lawType) {
        Integer status;
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            status =inquireService.getById(id).getShipStatus();
        }else{
            status =inquireSafeService.getById(id).getShipStatus();
        }
        return ShipStatusEnum.ESCAPE.getCode().equals(status);
    }

    @Override
    public void updateSeverity(String id) {
        DecisionEntity decisionEntity = this.getById(id);
        if(Objects.nonNull(decisionEntity)){
            Integer severity = decisionEntity.getSeverity();
            if(PlotSeverityEnum.GENERAL.getCode().equals(severity)){
                decisionEntity.setSeverity(PlotSeverityEnum.SERIOUS.getCode());
                this.updateById(decisionEntity);
            }
        }
    }

    @Override
    public List<PlotSeverityDTO> listSeverity(String id) {
        InquisitionEntity inquisitionEntity = inquisitionService.getById(id);
        return Arrays.stream(PlotSeverityEnum.values()).map(PlotSeverityDTO::new).peek(p->{
            if(Objects.nonNull(inquisitionEntity)){
                RecordTipEnum recordTipEnum = RecordTipEnum.findBySeverity(p.getCode(),
                        inquisitionEntity.getShipPower(), inquisitionEntity.getShipPowerUnit());
                p.setContent(recordTipEnum.getMessage());
            }
        }).collect(Collectors.toList());
    }

}
