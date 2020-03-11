package com.seven.gwc.modular.lawrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
     * 被询问人
     */
    private String investigateTel;
    /**
     * 案件类型
     */
    private Integer lawType;
    /**
     * 案件类型名称
     */
    private Integer lawTypeName;
    /**
     * 被执法人
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

}
