package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcIdBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * description : 执法人员实体
 *
 * @author : ZZL
 * @date : 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_operator")
public class OperatorEntity extends GwcIdBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 记录id */
    private String recordId;

    /** 执法人员 */
    private String lawPersonName;

    /** 执法编码 */
    private String lawCredentialCode;

    /** 执法人员ID */
    private String lawPersonId;

}
