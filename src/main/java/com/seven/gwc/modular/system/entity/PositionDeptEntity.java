package com.seven.gwc.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 岗位与部门关联实体
 * @author: GD
 * @since: 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_position_dept")
public class PositionDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 岗位ID
    private Long positionId;

    // 部门ID
    private Long deptId;

}
