package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 角色和菜单关联实体
 * @author: GD
 * @since: 2019-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_relation")
public class RelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 菜单id
    private Long menuId;

    // 角色id
    private Long roleId;

}
