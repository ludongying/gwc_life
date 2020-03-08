package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * description : 笔录实体
 *
 * @author : ZZL
 * @date : 2020-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_inquire_safe")
public class InquireSafeEntity extends GwcBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 记录id */
    private String id;

    /** 姓名 */
    private String investigateName;

    /** 性别 */
    private Integer investigateSex;

    /** 年龄 */
    private Integer investigateAge;

    /** 职务 */
    private Integer investigatePosition;

    /** 手机号码 */
    private String investigateTel;

    /** 地址 */
    private String investigateAddr;

    /** 身份证号 */
    private String identityCard;

    /** 渔船名称 */
    private String shipName;

    /** 船主姓名 */
    private String shipOwner;

    /** 船上总人数 */
    private Integer shipMember;

    /** 船名号书写情况 */
    private Integer shipCaseName;

    /** 船籍港涂写情况 */
    private Integer shipCaseRegistry;

    /** 船籍 */
    private String shipRegistry;

    /** 船名牌悬挂情况 */
    private Integer shipCaseCard;

    /** 救生圈数量 */
    private Integer lifebuoyCount;

    /** 灭火器数量 */
    private Integer extinguisherCount;

    /** 救生衣数量 */
    private Integer lifeVestCount;

    /** 救生筏数量 */
    private Integer lifeRaftCount;

    /** 职务船证数量 */
    private Integer shipCertificateCount;

    /** 普通船证数量 */
    private Integer shipCommonCertificateCount;

    /** 船的长度 */
    private Integer shipLength;

    /** 查获时候捕鱼状态 */
    private String shipStatus;



}
