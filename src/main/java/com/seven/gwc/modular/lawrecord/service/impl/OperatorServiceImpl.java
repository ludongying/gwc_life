package com.seven.gwc.modular.lawrecord.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.modular.lawrecord.dao.OperatorMapper;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.seven.gwc.modular.lawrecord.service.OperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * description : 执法人员服务实现类
 *
 * @author : ZZL
 * @date : 2020-02-29
 */
@Service
@Slf4j
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, OperatorEntity> implements OperatorService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOperator(String userId,String operatorEntities,String recordId) {
        if(Objects.nonNull(operatorEntities) && !operatorEntities.trim().isEmpty()){
            List<OperatorEntity> operators = JSON.parseArray(operatorEntities, OperatorEntity.class);
            if(Objects.nonNull(recordId)){
                if(Objects.nonNull(operators) && !operators.isEmpty()){
                            operators.forEach(operatorEntity -> {
                                String id = operatorEntity.getId();
                                if(Objects.nonNull(id) && id.trim().isEmpty()){operatorEntity.setId(null);}
                                operatorEntity.setRecordId(recordId);
                                operatorEntity.init(userId);
                            });
                            this.saveOrUpdateBatch(operators);
                        }
                    }
            }
        return true;
    }

    @Override
    public List<OperatorEntity> getByRecord(String recordId) {
        LambdaQueryWrapper<OperatorEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OperatorEntity::getRecordId,recordId)
                .eq(OperatorEntity::getDeleteFlag,Boolean.TRUE);
        return this.list(wrapper);
    }
}
