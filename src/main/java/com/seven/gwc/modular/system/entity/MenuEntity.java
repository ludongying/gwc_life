package com.seven.gwc.modular.system.entity;

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
 * @Description: 菜单实体
 * @author: GD
 * @since: 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 菜单编号
    private String code;

    // 菜单父编号
    private String pcode;

    // 当前菜单的所有父菜单编号
    private String pcodes;

    // 菜单名称
    private String name;

    // 菜单图标
    private String icon;

    // url地址
    private String url;

    // 菜单排序
    private Integer sort;

    // 菜单层级
    private Integer levels;

    // 是否是菜单(字典)
    private String menuFlag;

    // 备注
    private String description;

    // 菜单状态(字典)
    private String status;

    // 是否打开新页面的标识(字典)
    private String newPageFlag;

    // 是否打开(字典)
    private String openFlag;

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

    // 菜单父级id
    @TableField(exist=false)
    private Long pId;

    // 菜单父级名称
    @TableField(exist=false)
    private String pcodeName;




}
