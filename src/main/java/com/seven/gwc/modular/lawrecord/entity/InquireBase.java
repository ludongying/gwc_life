package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected String investigateName;

    /** 手机号码 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected String investigateTel;

    /** 渔船名号 */
    protected String shipName;

    /** 性别(枚举) */
    protected Integer investigateSex;

    /** 年龄 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected Integer investigateAge;

    /** 职务 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected Integer investigatePosition;

    /** 地址 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected String investigateAddr;

    /** 是否携带身份证*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected Integer identityCase;

    /** 身份证号 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    protected String identityCard;
    /** 船主姓名 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String shipOwner;
    /** 船上总人数 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer shipMember;
    /** 查获时候捕鱼状态 */
    private Integer shipStatus;

}
