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
 * description : dictionary实体
 *
 * @author : LM
 * @date : 2019-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
public class DictEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 字典类型id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    // 父ID
    private String pid;

    /** 所属字典类型的id */
    private String dictTypeId;

    /** 字典名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 字典的描述 */
    private String description;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /** 更新时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /** 创建人 */
    private String createUser;

    /** 修改人 */
    private String updateUser;

    @TableField(exist=false)
    private String pName;

}
