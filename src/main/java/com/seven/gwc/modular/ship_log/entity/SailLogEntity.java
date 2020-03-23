package com.seven.gwc.modular.ship_log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 航行日志实体
 *
 * @author : 李晓晖
 * @date : 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_sail_log")
public class SailLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 编码
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String shipId;
//    private Date date;

    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 内容 */
    private String content;

    /** 天气 */
    private String weather;

    /** 创建人 */
    private String createPerson;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String updatePerson;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;


    //船舶名称
    @TableField(exist = false)
    private String ShipName;

    //姓名
    @TableField(exist = false)
    private String createPersonName;
}
