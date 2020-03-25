package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * description : 决定实体-安全
 *
 * @author : ZZL
 * @date : 2020-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_decision_safe")
public class DecisionSafeEntity extends DecisionBase {

    private static final long serialVersionUID = 1L;


}
