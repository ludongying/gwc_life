package com.seven.gwc.modular.lawrecord.entity;

import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-22
 * @description :
 */
@Data
public class InquireBase extends GwcBaseEntity {
    /** 询问笔录id */
    private String id;
    /** 姓名 */
    protected String investigateName;

    /** 手机号码 */
    protected String investigateTel;
}
