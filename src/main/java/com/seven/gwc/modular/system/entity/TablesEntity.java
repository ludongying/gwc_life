package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 表结构实体
 * @author: GD
 * @since: 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TABLES")
public class TablesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 表名称
    @TableField("TABLE_NAME")
    private String tableName;

    // 表描述
    @TableField("TABLE_COMMENT")
    private String tableComment;


}
