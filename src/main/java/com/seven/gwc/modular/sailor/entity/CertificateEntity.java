package com.seven.gwc.modular.sailor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class CertificateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    /** 证书编码 */
    private String certificateId;

    /** 证书名称 */
    private String name;

    /** 归属类型：船员or船舶 */
    private Integer ownerType;

    /** 证书类型 */
    private Integer certificateType;

    /** 签发机构 */
    private String issuer;

    /** 签发日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issueDate;

    /** 到期日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outDate;

    /** 存放地点 */
    private Integer storePlace;

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
    private String attachment;

    /** 备注 */
    private String remark;

}
