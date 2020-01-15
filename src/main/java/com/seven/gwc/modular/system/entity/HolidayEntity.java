package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 假日实体
 * @author: GD
 * @since: 2019-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_holiday")
public class HolidayEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 假日名称
    private String name;

    // 排序
    private Integer sort;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // 创建用户
    private Long createUser;

    // 修改用户
    private Long updateUser;

}
