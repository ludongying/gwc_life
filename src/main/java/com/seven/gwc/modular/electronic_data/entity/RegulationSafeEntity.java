package com.seven.gwc.modular.electronic_data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class RegulationSafeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 法规id */
    private String lawRegularId;

    /** 文档名称 */
    private String name;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 法律法规/航行安全 */
    private String type;

}
