package com.seven.gwc.modular.lawrecord.service.impl;


import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.dto.DecisionSafeDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.DecisionSafeVO;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.DecisionSafeEntity;
import com.seven.gwc.modular.lawrecord.dao.DecisionSafeMapper;
import com.seven.gwc.modular.lawrecord.service.DecisionSafeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * description : 决定服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-07
 */
@Service
public class DecisionSafeServiceImpl extends ServiceImpl<DecisionSafeMapper, DecisionSafeEntity> implements DecisionSafeService {

    @Autowired
    private LawRecordService lawRecordService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateDecisionSafe(DecisionSafeVO vo) {
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
        return baseResult;
    }

    @Override
    public DecisionSafeDTO detail(String id) {
        DecisionSafeEntity decisionSafeEntity = this.getById(id);
        if(Objects.nonNull(decisionSafeEntity)){
            DecisionSafeDTO decisionSafeDTO=new DecisionSafeDTO();
            BeanUtils.copyProperties(decisionSafeEntity,decisionSafeDTO);
            decisionSafeDTO.setAddress();
            return decisionSafeDTO;
        }
        return null;
    }
}
