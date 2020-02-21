package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * description : 字段结构实体
 *
 * @author : GD
 * @date : 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("COLUMNS")
public class ColumnsEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 字段名称 */
    @TableField("COLUMN_NAME")
    private String columnName;

    /** 字段描述 */
    @TableField("COLUMN_COMMENT")
    private String columnComment;

}
