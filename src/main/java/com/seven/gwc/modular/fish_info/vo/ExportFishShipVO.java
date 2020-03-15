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

    /** 名称 */
    @FieldName(value = "船名称")
    private String name;

    /** 管理类别-字典 */
    @FieldName(value = "管理类别")
    private String areaTypeName;

    /** 编码 */
    @FieldName(value = "编码")
    private String code;

    /** 船籍 */
    @FieldName(value = "船籍")
    private String registry;

    /** 船舶类型-字典 */
    @FieldName(value = "船舶类型")
    private String shipTypeName;

    /** 船体材质 */
    @FieldName(value = "船体材质")
    private String hullMaterial;

    /** 作业类型-字典 */
    @FieldName(value = "作业类型")
    private String workTypeName;

    /** 船长度 */
    @FieldName(value = "船长度")
    private Double shipLong;

    /** 船宽度 */
    @FieldName(value = "船宽度")
    private Double shipWide;

    /** 船深 */
    @FieldName(value = "船深")
    private Double shipDeep;

    /** 马力 */
    @FieldName(value = "马力")
    private Double totalPower;

    /** 吨位 */
    @FieldName(value = "总吨位")
    private String tonnage;

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

    /** 手机号码 */
    @FieldName(value = "手机号码")
    private String phone;

    /** 地址 */
    @FieldName(value = "地址")
    private String address;

    /** 出海状态 */
    @FieldName(value = "出海状态")
    private String seaStateName;

    /** 是否重点 */
    @FieldName(value = "是否重点")
    private String keyPoints;

}
