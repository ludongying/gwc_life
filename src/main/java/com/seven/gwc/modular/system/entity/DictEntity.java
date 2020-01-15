package com.seven.gwc.modular.system.entity;

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
 * @Description: dictionary实体
 * @author: LM
 * @since: 2019-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
public class DictEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    // 字典类型id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 所属字典类型的id
    private Long dictTypeId;

    // 字典编码
    private String code;

    // 字典名称
    private String name;

    // 上级代码id
    private Long parentId;

    @TableField(exist=false)
    private String parentName;

    // 所有上级id
    private String parentIds;

    // 状态（字典）
    private String status;

    // 排序
    private Integer sort;

    // 字典的描述
    private String description;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime = new Date();

    // 更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // 创建人
    private String createUser;

    // 修改人
    private String updateUser;


}
