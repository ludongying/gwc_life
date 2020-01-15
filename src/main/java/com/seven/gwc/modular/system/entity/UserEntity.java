package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 用户实体
 * @author: GD
 * @since: 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class UserEntity extends BaseEntity {

    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 头像
    private String avatar;

    // 账号
    private String account;

    // 密码
    private String password;

    // md5密码盐
    private String salt;

    // 名字
    private String name;

    // 生日
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    // 性别(字典)
    private String sex;

    // 电子邮件
    private String email;

    // 电话
    private String phone;

    // 角色id(多个逗号隔开)
    private String roleId;

    // 部门id(多个逗号隔开)
    private Long deptId;

    // 岗位id(多个逗号隔开)
    private String positionId;

    // 状态(字典)
    private String status;

    // 创建时间
    private Date createTime;

    // 创建人
    private Long createUser;

    // 更新时间
    private Date updateTime;

    // 更新人
    private Long updateUser;

    // 乐观锁
    private Integer version;

    // 角色父级名称
    @TableField(exist=false)
    private String roleName;

    // 部门父级名称
    @TableField(exist=false)
    private String deptName;

}
