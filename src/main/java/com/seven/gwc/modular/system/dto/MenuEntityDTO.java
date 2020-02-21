package com.seven.gwc.modular.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * description : 菜单表
 *
 * @author : GD
 * @date : 2019-9-30 10:11
 */
@Data
public class MenuEntityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 菜单编号 */
    private String code;
    /** 菜单父级id */
    private Long pid;
    /** 菜单父编号 */
    private String pcode;
    /** 菜单父级名称 */
    private String pcodeName;
    /** 菜单名称 */
    private String name;
    /** 菜单图标 */
    private String icon;
    /** url地址 */
    private String url;
    /** 菜单排序号 */
    private Integer sort;
    /** 菜单层级 */
    private Integer levels;
    /** 是否是菜单(字典) */
    private String menuFlag;
    /** 备注 */
    private String description;

}
