package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 附件实体
 * @author: GD
 * @since: 2019-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_enclosure")
public class EnclosureEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 附件名称
    private String name;

    // 附件url
    private String url;

    // PDF附件url
    private String pdfUrl;

    // 附件后缀
    private String suffix;

    // 附件大小
    private Long fileSize;

    // 外键表
    private String forSurface;

    // 外键ID
    private Long forId;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 创建人id
    private Long createId;

    // 创建人名称
    private String createUser;

}
