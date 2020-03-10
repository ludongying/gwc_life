package com.seven.gwc.modular.equipment_info.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 设备信息服务类
 *
 * @author : LDY
 * @date : 2020-03-09
 */

public interface EquipService extends IService<EquipEntity> {

    /**
     * 设备信息查询列表
     *
     * @param equipName 名称
     * @return List<设备信息服务对象>
     */
    List<EquipEntity> selectEquip(String equipName);

    /**
     * 设备信息新建
     *
     * @param equip 实体对象
     * @param user 当前用户
     */
    void addEquip(EquipEntity equip, ShiroUser user);

    /**
     * 设备信息删除
     *
     * @param equipId 唯一标识
     * @param user 当前用户
     */
    void deleteEquip(String equipId, ShiroUser user);

    /**
     * 设备信息编辑
     *
     * @param equip 实体对象
     * @param user 当前用户
     */
    void editEquip(EquipEntity equip, ShiroUser user);

}
