package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.enums.WhetherEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据 生产决定
 */
@Data
@Accessors(chain = true)
public class DecisionProduceDO extends DecisionDO {

/**
 * 证据是否有物价局价格指导
 */
protected String A006;
/**
 * 证据是否有禁渔期通告（农业部）
 */
protected String A007;

public DecisionProduceDO(DecisionEntity entity){
        super(entity);
        WhetherEnum a006 = WhetherEnum.findByCode(entity.getCaseBureauPrice());
        WhetherEnum a007=WhetherEnum.findByCode(entity.getCaseNotification());
        this.setA006(Objects.nonNull(a006)?a006.getMessage():"").setA007(Objects.nonNull(a007)?a007.getMessage():"");
   }
}
