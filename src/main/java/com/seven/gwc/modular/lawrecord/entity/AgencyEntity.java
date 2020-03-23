package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * description : 办案机关实体
 *
 * @author : ZZL
 * @date : 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_agency")
public class  AgencyEntity extends GwcBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 记录id */
    private String id;
    /** 执法船号 */
    private String lawShipCode;
    /**
     * 单位简称
     */
    private String shortName;

    /** 执法单位 */
    private String enforcementAgency;

    /** 案件编号 罚 因该为年份 这是被设计坑了*/
    private Integer lawCaseFineCode;

    /** 案件编号 */
    private Integer lawCaseCode;

    /** 案件来源 */
    private Integer lawCaseSource;

    /** 案发经度 */
    private String lawCaseLon;

    /** 案发纬度 */
    private String lawCaseLat;

    /** 案发地点 */
    private String lawCaseAddr;

    /** 案发时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date lawCaseDate;

    /** 联系人 */
    private String agencyPerson;

    /** 电话 */
    private String agencyTel;

    /** 地址 */
    private String agencyAddr;

    /** 指定银行 */
    private String bank;

    /** 复议机关 */
    private String reviewAgency;

    /** 诉讼机关 */
    private String lawsuitAgency;

    /** 勘探开始时间  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date prospectStartDate;

    /** 勘探结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date prospectEndDate;

    /** 询问开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date inquireStartDate;

    /** 询问结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date inquireEndDate;

    /** 电话请示时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date telApplyDate;

    /** 受案时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date acceptDate;

    /** 处理时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date dealDate;

    /** 处罚审批时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date punishDate;

    /** 处决时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date decisionDate;

    /** 结案时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date finishDate;

    /** 保管年限 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date saveDate;

}
