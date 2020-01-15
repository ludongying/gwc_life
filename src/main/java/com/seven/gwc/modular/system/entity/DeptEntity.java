package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 部门实体
 * @author: GD
 * @since: 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
public class DeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 父部门id */
    private Long pid;

    // 父级ids
    private String pids;

    // 简称
    private String simpleName;

    // 全称
    private String fullName;

    // 描述
    private String description;

    // 版本（乐观锁保留字段）
    private Integer version;

    // 排序
    private Integer sort;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // 创建人
    private Long createUser;

    // 修改人
    private Long updateUser;

    // 部门父级名称
    @TableField(exist = false)
    @JsonProperty("pName")
    private String pName;

}
