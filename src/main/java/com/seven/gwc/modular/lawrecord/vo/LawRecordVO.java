package com.seven.gwc.modular.lawrecord.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.core.util.DateTimeUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-08
 * @description :
 */
@Data
public class LawRecordVO implements Serializable {

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
     * 被执法人
     */
    private String investigateName;

    /**
     *创建开始时间
     */
    private String createStartTime;

    /**
     *创建结束时间
     */
    private String createEndTime;



}
