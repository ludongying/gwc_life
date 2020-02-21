package com.seven.gwc.modular.sailor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 执法船id */
    private String shipId;

    /** 用户id */
    private Long personId;

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
    private Integer political;

    //姓名
    @TableField(exist = false)
    private String personName;

    //所属执法船
    @TableField (exist = false)
    private String lawShipName;

    //出生年月
    @TableField (exist = false)
    private String brithday;

    //岗位
    @TableField (exist = false)
    private String position;

    //联系方式
    @TableField (exist = false)
    private String phone;

    //证书
    @TableField (exist = false)
    private String certificate;

}
