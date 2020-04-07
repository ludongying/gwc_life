package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * description : 决定实体-生产
 *
 * @author : ZZL
 * @date : 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_decision")
public class DecisionEntity extends DecisionBase {

    private static final long serialVersionUID = 1L;


    /** 证据是否有物价局价格指导 */
    private Integer caseBureauPrice;

    /** 证据是否有禁渔期通告(农业部) */
    private Integer caseNotification;


}
