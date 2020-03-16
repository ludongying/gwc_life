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

    /** 渔船编码 */
    private String code;

    /** 船名 */
    private String name;

    /** 船籍 */
    private String registry;

    //水域分类
    private String watersType;
    @TableField(exist=false)
    private String watersTypeName;

    /** 区域分类-字典 */
    private String areaType;
    @TableField(exist=false)
    private String areaTypeName;

    /** 船舶类型-字典 */
    private String shipType;

    @TableField(exist=false)
    private String shipTypeName;

    /** 船体材质 */
    private String hullMaterial;

    /** 船长度 */
    private Double shipLong;

    /** 船长度 */
    private Double shipWide;

    /** 船深 */
    private Double shipDeep;

    /** 总吨位 */
    private String tonnage;

    /** 主机总功率 */
    private Double totalPower;

    /** 作业类型-字典 */
    private String workType;
    @TableField(exist=false)
    private String workTypeName;

    //作业方式
    private String practice;
    @TableField(exist=false)
    private String practiceName;

    /** 建成日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date productDate;

    /** 船主名称 */
    private String shipOwner;

    /** 证件号码 */
    private String identity;

    /** 船主地址 */
    private String address;

    //核定航区
    private String approvedArea;

    //核定成员
    private String authorizedMember;

    /** 文件名 */
    private String fileName;



    /** 出海状态 */
    private String seaState;
    @TableField(exist=false)
    private String seaStateName;

    /** 是否重点 */
    private Boolean keyPoints;

    /** 路径 */
    private String filePath;

    /** 文档名 */
    private String fullName;
}
