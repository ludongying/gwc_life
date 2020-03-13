package com.seven.gwc.modular.work.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 工作日志记录实体
 *
 * @author : 李晓晖
 * @date : 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_work_log")
public class ShipWorkLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 编码
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 记录时间（yyyy-MM-dd HH:mm:ss）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordDate;

    /**
     * 记录结束时间（yyyy-MM-dd HH:mm:ss）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordEndDate;

    /**
     * 记录内容
     */
    private String content;

    /**
     * 工作日程类型
     */
    private Integer recordType;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
