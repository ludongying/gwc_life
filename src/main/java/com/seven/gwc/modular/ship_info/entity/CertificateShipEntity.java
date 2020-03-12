package com.seven.gwc.modular.ship_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * description : 证书信息实体
 *
 * @author : LDY
 * @date : 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ship_info_certificate")
public class CertificateShipEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 证书编码 */
    private String certificateId;

    /** 证书名称 */
    private String name;

    /** 归属类型：船员or船舶 */
    private String ownerType;
    @TableField(exist=false)
    private String ownerTypeName;

    /** 证书类型 */
    private String certificateType;
    @TableField(exist=false)
    private String certificateTypeName;

    /** 签发机构 */
    private String issuer;

    /** 签发日期 */
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    /** 到期日期 */
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outDate;

    /** 存放地点 */
    private String storePlace;

    /** 是否常用 */
    private Integer isOften;

    /** 窗口期 */
    private Integer windowPhase;

    /** 责任部门 */
    private String responseDept;

    /** 发证地点 */
    private String certificatePlace;

    /** 联系方式 */
    private String contact;

    /** 附件 */
    private String attachFilePath;

    /** 备注 */
    private String remark;

}
