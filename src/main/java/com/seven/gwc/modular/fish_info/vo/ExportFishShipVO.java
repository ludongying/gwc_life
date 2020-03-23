package com.seven.gwc.modular.fish_info.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.modular.lawrecord.data.export.anno.FieldName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldName(value = "渔船信息.xlsx")
public class ExportFishShipVO {

    /** 渔船编码 */
    @FieldName(value = "渔船编码")
    private String code;

    /** 船名 */
    @FieldName(value = "船名")
    private String name;

    /** 船籍 */
    @FieldName(value = "船籍")
    private String registry;

    /** 水域分类 */
    @FieldName(value = "水域分类")
    private String watersTypeName;

    /** 区域分类 */
    @FieldName(value = "区域分类")
    private String areaTypeName;

    /** 船舶类型-字典 */
    @FieldName(value = "船舶类型")
    private String shipTypeName;

    /** 船体材质 */
    @FieldName(value = "船体材质")
    private String hullMaterial;

    /** 船长 */
    @FieldName(value = "船长")
    private Double shipLong;

    /** 船宽 */
    @FieldName(value = "船宽")
    private Double shipWide;

    /** 船深 */
    @FieldName(value = "船深")
    private Double shipDeep;

    /** 总吨位 */
    @FieldName(value = "总吨位")
    private String tonnage;

    /** 主机总功率 */
    @FieldName(value = "主机总功率")
    private Double totalPower;

    /** 作业类型 */
    @FieldName(value = "作业类型")
    private String workTypeName;

    /** 作业方式 */
    @FieldName(value = "作业方式")
    private String practiceName;

    /** 建成日期 */
    @FieldName(value = "建成日期")
    private String productDate;

    /** 船龄 */
    @FieldName(value = "船龄")
    private Integer shipAge;

    /** 船主名称 */
    @FieldName(value = "船主名称")
    private String shipOwner;

    /** 证件号码 */
    @FieldName(value = "证件号码")
    private String identity;

    /** 船主地址 */
    @FieldName(value = "船主地址")
    private String address;

    /** 核定航区 */
    @FieldName(value = "核定航区")
    private String approvedArea;

    /** 核定成员 */
    @FieldName(value = "核定成员")
    private String authorizedMember;

}
