package com.seven.gwc.modular.equipment_info.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 设备维护服务类
 *
 * @author : LDY
 * @date : 2020-03-12
 */

public interface EquipMaintainService extends IService<EquipMaintainEntity> {

    /**
     * 设备维护查询列表
     *
     * @param equipMaintain 实体对象
     * @return List<设备维护服务对象>
     */
    List<EquipMaintainEntity> selectEquipMaintain(EquipMaintainEntity equipMaintain);

    /**
     * 设备维护新建
     *
     * @param equipMaintain 实体对象
     * @param user 当前用户
     */
    void addEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user);

    /**
     * 设备维护删除
     *
     * @param equipMaintainId 唯一标识
     * @param user 当前用户
     */
    void deleteEquipMaintain(String equipMaintainId, ShiroUser user);

    /**
     * 设备维护编辑
     *
     * @param equipMaintain 实体对象
     * @param user 当前用户
     */
    void editEquipMaintain(EquipMaintainEntity equipMaintain, ShiroUser user);

    /**
     * 根据维保表id获取维保详情
     * @param equipMaintainId 唯一标识
     * @return
     */
    EquipMaintainEntity getOneById(String equipMaintainId);

}
