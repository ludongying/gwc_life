package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.DecisionBase;
import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据 安全决定
 */
@Data
public class DecisionSafeDO extends DecisionDO{

    public DecisionSafeDO(DecisionBase decisionBase){
        super(decisionBase);
    }

}
