package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据 生产决定
 */
@Data
public class DecisionProduceDO extends DecisionDO {

    /**
     * 证据是否有物价局价格指导
     */
  protected String A006;
    /**
     * 证据是否有禁渔期通告（农业部）
     */
  protected String A007;
}
