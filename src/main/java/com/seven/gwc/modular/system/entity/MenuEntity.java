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
 * description : 菜单实体
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 菜单编号 */
    private String code;

    /** 菜单父编号 */
    private String pcode;

    /** 当前菜单的所有父菜单编号 */
    private String pcodes;

    /** 菜单名称 */
    private String name;

    /** 菜单图标 */
    private String icon;

    /** url地址 */
    private String url;

    /** 菜单排序 */
    private Integer sort;

    /** 菜单层级 */
    private Integer levels;

    /** 是否是菜单(字典) */
    private String menuFlag;

    /** 备注 */
    private String description;

    /** 菜单状态(字典) */
    private String status;

    /** 是否打开新页面的标识(字典) */
    private String newPageFlag;

    /** 是否打开(字典) */
    private String openFlag;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 编辑时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建人 */
    private String createUser;

    /** 编辑人 */
    private String updateUser;

    /** 菜单父级id */
    @TableField(exist=false)
    private String pId;

    /** 菜单父级名称 */
    @TableField(exist=false)
    private String pcodeName;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcodes() {
        return pcodes;
    }

    public void setPcodes(String pcodes) {
        this.pcodes = pcodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewPageFlag() {
        return newPageFlag;
    }

    public void setNewPageFlag(String newPageFlag) {
        this.newPageFlag = newPageFlag;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getPcodeName() {
        return pcodeName;
    }

    public void setPcodeName(String pcodeName) {
        this.pcodeName = pcodeName;
    }
}
