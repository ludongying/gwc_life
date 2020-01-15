package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("sys_dict_type")
public class DictTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 字典类型id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 字典类型编码
    private String code;

    // 字典类型名称
    private String name;

    // 字典描述
    private String description;

    // 是否是系统字典，Y-是，N-否
    private String systemFlag;

    // 状态(字典)
    private String status;

    // 排序
    private Integer sort;

    // 添加时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    // 创建人
    private String createUser;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    // 修改人
    private String updateUser;

}
