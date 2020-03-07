package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.ReasonDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.service.ReasonService;
import com.seven.gwc.modular.lawrecord.vo.ReasonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-05
 * @description :
 */
@Service
public class ReasonServiceImpl implements ReasonService {

    @Autowired
    private LawRecordService lawRecordService;

    @Override
    public BaseResult updateReason(ReasonVO vo) {
        String id=vo.getId();
        LawRecordEntity lawRecordEntity;
        //新建
        if(Objects.isNull(id) || id.trim().isEmpty()){
            lawRecordEntity = lawRecordService.createLawRecord(vo.getUserId(),vo.getLawType());
            vo.setId(id);
        }else{
            lawRecordEntity = lawRecordService.getById(id);
        }
        lawRecordEntity.setMainReason(vo.getMainReason());
        lawRecordEntity.setSecondReason(vo.getSecondReasonStr());
        lawRecordEntity.init(vo.getUserId());
        lawRecordService.saveOrUpdate(lawRecordEntity);
        BaseResult baseResult = new BaseResult(true, "");
        baseResult.setContent(vo.getId());
        return baseResult;
    }

    @Override
    public ReasonDTO detail(String id) {
        LawRecordEntity lawRecordEntity = lawRecordService.getById(id);
        if(Objects.nonNull(lawRecordEntity)){
            return new ReasonDTO(lawRecordEntity);
        }
        return null;
    }
}
