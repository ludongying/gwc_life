package com.seven.gwc.modular.electronic_data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.seven.gwc.core.base.GwcBaseEntity;
import com.seven.gwc.modular.system.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * description : 法律法规/航线安全实体
 *
 * @author : SHQ
 * @date : 2020-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_regulation_safe")
public class RegulationSafeEntity extends GwcBaseEntity implements  Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /** 法规id */
    private String lawRegularId;

    @TableField(exist = false)
    private String lawRegularName;

    /** 文档名称 */
    private String name;

    /** 法律法规/航行安全 */
    private String type;

    /** 附件名称 */
    private String fileName;

    /** 附件地址*/
    private String filePath;


}
