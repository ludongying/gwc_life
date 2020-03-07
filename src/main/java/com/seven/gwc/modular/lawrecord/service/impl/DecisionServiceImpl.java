package com.seven.gwc.modular.lawrecord.service.impl;


import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.dao.DecisionMapper;
import com.seven.gwc.modular.lawrecord.service.DecisionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Objects;


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


    @Override
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
        return baseResult;
    }

    @Override
    public DecisionDTO detail(String id) {
        DecisionEntity decisionEntity = this.getById(id);
        if(Objects.nonNull(decisionEntity)){
            DecisionDTO decisionDTO=new DecisionDTO();
            BeanUtils.copyProperties(decisionEntity,decisionDTO);
            decisionDTO.setAddress();
            return decisionDTO;
        }
        return null;
    }
}
