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
 * @Description: 岗位实体
 * @author: GD
 * @since: 2019-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_position")
public class PositionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 岗位名称
    private String name;

    // 岗位编码
    private String code;

    // 排序
    private Integer sort;

    // 状态
    private String state;

    // 备注
    private String description;

    // 创建id
    private Long createId;

    // 创建人名称
    private String createUser;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 数据范围
    private String dataScope;

}
