package com.seven.gwc.modular.ship_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 执法船信息管理实体
 *
 * @author : LDY
 * @date: 2020-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_info_ship")
public class ShipEntity extends GwcBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 编码
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    // 船舶编码
    private String shipCode;

    // 名称
    private String name;

    // 船主名称
    private String owner;

    // 船籍
    private String nationality;

    // 船舶类型
    private Integer type;

    // 总长
    private Double lengthTotal;

    // 船长
    private Double length;

    // 船宽
    private Double width;

    // 船体材质
    private String material;

    // 船深
    private Double deep;

    // 总吨位
    private Double tonnage;

    // 净吨位
    private Double tonnageNet;

    // 核定航区
    private String area;

    // 核载人数
    private Integer peopleNum;

    // 主功率
    private Double mainPower;

    // 船舶图片
    private String imageFilePath;

    // 船舶图片url
    @TableField(exist = false)
    private String imageUrl;

    // 证书编码
    private String certificateId;

    // 备注
    private String remark;

    // 船舶制造厂
    private String manufacturer;

    // 完成日期
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    // 核定干舷
    private Integer gunwaleCount;

    @TableField(exist=false)
    private String nationalityDesp;

    @TableField(exist = false)
    private String typeDesp;

}
