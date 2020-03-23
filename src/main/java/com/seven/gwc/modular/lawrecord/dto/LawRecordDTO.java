package com.seven.gwc.modular.lawrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seven.gwc.modular.lawrecord.entity.InquireBase;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-03-08
 * @description :
 */
@Data
public class LawRecordDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 案件状态
     */
    private Integer status;

    /**
     * 案件编号
     */
    private String lawCaseCode;
    /**
     * 被执法船
     */
    private String shipName;
    /**
     * 案件类型
     */
    private Integer lawType;
    /**
     * 案件类型名称
     */
    private String lawTypeName;

    /**
     * 被询问人
     */
    private String investigateTel;

    /**
     * 被询问人电话
     */
    private String investigateName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    /**
     * 案件来源
     */
    private Integer lawCaseSource;

    /**
     * 案件来源
     */
    private String lawCaseSourceName;

    /**
     * 处罚人类型
     */
    private Integer punishPersonType;

    /**
     * 被询问人信息
     */
    @JsonIgnore
    private List<InquireBase> inquires;

}
