package com.seven.gwc.modular.lawrecord.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.seven.gwc.modular.lawrecord.dao.OperatorMapper;
import com.seven.gwc.modular.lawrecord.service.OperatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
    public boolean updateOperator(String userId,String operatorEntities,String recordId) {
        if(Objects.nonNull(operatorEntities) && !operatorEntities.trim().isEmpty()){
            List<OperatorEntity> operators = JSON.parseArray(operatorEntities, OperatorEntity.class);
            if(Objects.nonNull(operators) && !operators.isEmpty()){
                if(Objects.nonNull(recordId)){
                    operators=operators.stream().filter(operatorEntity ->
                          !operatorEntity.getId().trim().isEmpty() ||
                          !operatorEntity.getLawPersonName().trim().isEmpty() ||
                          !operatorEntity.getLawCredentialCode().isEmpty()
                     ).collect(Collectors.toList());
                    if(!operators.isEmpty()){
                        operators.forEach(operatorEntity -> {
                            operatorEntity.setRecordId(recordId);
                            operatorEntity.init(userId);
                        });
                        this.saveOrUpdateBatch(operators);
                    }
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
