package com.seven.gwc.modular.fish_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 渔船信息实体
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_fish_ship")
public class FishShipEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 渔船id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 名称 */
    private String name;

    /** 管理类别-字典 */
    private String areaType;
    @TableField(exist=false)
    private String areaTypeName;

    /** 编码 */
    private String code;

    /** 船籍 */
    private String registry;

    /** 船舶类型-字典 */
    private String shipType;
    @TableField(exist=false)
    private String shipTypeName;

    /** 船体材质 */
    private String hullMaterial;

    /** 作业类型-字典 */
    private String workType;
    @TableField(exist=false)
    private String workTypeName;

    /** 船长度 */
    private Double shipLong;

    /** 船长度 */
    private Double shipWide;

    /** 船深 */
    private Double shipDeep;

    /** 主机总功率 */
    private Double totalPower;

    /** 吨位 */
    private String tonnage;

    /** 建成日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date productDate;

    /** 船主名称 */
    private String shipOwner;

    /** 证件号码 */
    private String identity;

    /** 手机号码 */
    private String phone;

    /** 地址 */
    private String address;

    /** 出海状态 */
    private String seaState;
    @TableField(exist=false)
    private String seaStateName;

    /** 是否重点 */
    private Boolean keyPoints;

    /** 文件名 */
    private String fileName;

    /** 路径 */
    private String filePath;

    /** 文档名 */
    private String fullName;
}
