package com.seven.gwc.modular.sailor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 船员信息实体
 *
 * @author : LDY
 * @date : 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_person")
public class PersonEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 执法船id */
    private String shipId;

    /** 用户id */
    private String personId;

    /** 手机ip */
    private String phoneIp;

    /** 民族 */
    private String nation;

    /** 身份证号 */
    private String idNumber;

    /** 籍贯 */
    private String birthPlace;

    /** 职务 */
    private String duty;

    /** 证书id */
    private String certificateId;

    /** 政治面貌（枚举） */
    private String political;

    /** 地址 */
    private String adress;
//
//    //用户编码
//    @TableField(exist = false)
//    private String UserId;

    //姓名
    @TableField(exist = false)
    private String personName;

    //性别
    @TableField(exist = false)
    private String sex;

    //所属执法船
    @TableField (exist = false)
    private String lawShipName;

    //出生年月
    @TableField (exist = false)
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    //年龄
    @TableField (exist = false)
    private Integer age;

    //岗位
    @TableField (exist = false)
    private String positionId;
    //岗位名称
    @TableField (exist = false)
    private String positionName;


    //联系方式
    @TableField (exist = false)
    private String phone;

    //邮箱
    @TableField (exist = false)
    private String email;

    //证书
    @TableField (exist = false)
    private String certificate;

    //部门编码
    @TableField (exist = false)
    private String deptId;

    //部门名称
    @TableField (exist = false)
    private String deptName;

}
