package com.seven.gwc.modular.equipment_info.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintaindetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 设备维保详情服务类
 *
 * @author : LDY
 * @date : 2020-03-25
 */

public interface EquipMaintaindetailService extends IService<EquipMaintaindetailEntity> {

    /**
     * 设备维保详情查询列表
     *
     * @param equipMaintaindetailName 名称
     * @return List<设备维保详情服务对象>
     */
    List<EquipMaintaindetailEntity> selectEquipMaintaindetail(String equipMaintaindetailName);

    /**
     * 设备维保详情新建
     *
     * @param equipMaintaindetail 实体对象
     * @param user 当前用户
     */
    void addEquipMaintaindetail(EquipMaintaindetailEntity equipMaintaindetail, ShiroUser user);

    /**
     * 设备维保详情删除
     *
     * @param equipMaintaindetailId 唯一标识
     * @param user 当前用户
     */
    void deleteEquipMaintaindetail(String equipMaintaindetailId, ShiroUser user);

    /**
     * 设备维保详情编辑
     *
     * @param equipMaintaindetail 实体对象
     * @param user 当前用户
     */
    void editEquipMaintaindetail(EquipMaintaindetailEntity equipMaintaindetail, ShiroUser user);

}
