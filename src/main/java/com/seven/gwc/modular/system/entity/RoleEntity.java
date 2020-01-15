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
 * @Description: 角色实体
 * @author: GD
 * @since: 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 父角色id
    private Long pid;

    // 角色名称
    private String name;

    // 提示
    private String description;

    // 排序
    private Integer sort;

    // 乐观锁
    private Integer version;

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

    // 父级名称
    @TableField(exist=false)
    @JsonProperty("pName")
    private String pName;


}
