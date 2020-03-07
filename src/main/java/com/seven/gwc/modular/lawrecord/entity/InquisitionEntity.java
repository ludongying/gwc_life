package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 勘验笔录实体
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_inquisition")
public class InquisitionEntity extends GwcBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 船名号书写情况 */
    private Integer shipCaseName;

    /** 船籍港涂写情况 */
    private Integer shipCaseRegistry;

    /** 船籍 */
    private String shipRegistry;

    /** 船名牌悬挂情况 */
    private Integer shipCaseCard;

    /** 师傅携带证件 */
    private Integer shipCaseCredentials;

    /** 是否有残留渔获物 */
    private Integer shipCaseFish;

    /** 捕捞许可证编号 */
    private String shipCredentialsCode;

    /** 船号 */
    private String shipCode;

    /** 作业方式 */
    private Integer shipOperatePerson;

    /** 主机功率 */
    private String shipPower;

    /** 持证人姓名 */
    private String shipCredentialsOwner;

    /** 是否携带身份证*/
    private Integer shipIdentityCase;

    /** 身份证号 */
    private String shipCredentialsOwnerIdentity;

    /** 查获时渔船状态(枚举) */
    private Integer shipStatus;



}
