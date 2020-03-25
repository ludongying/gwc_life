package com.seven.gwc.modular.lawrecord.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * @author : zzl
 * @Date: 2020-03-24
 * @description : 案件类型
 */
@Data
public class LawTypeDTO implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 案件类型
     */
    private Integer lawType;
    /**
     * 处罚人类型
     */
    private Integer punishPersonType;

    /**
     * 案件编号
     */
    private String  lawCaseCode;

    /**
     * 文书标记
     */
    private Boolean writFlag;


}
