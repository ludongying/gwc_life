package com.seven.gwc.modular.equipment_info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;

import java.text.ParseException;
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
     * @param equip 设备实体
     * @return List<设备信息服务对象>
     */
    List<EquipEntity> selectEquip(EquipEntity equip, Integer total, Integer size);

    Integer getListSize(EquipEntity equip);

    /**
     * 设备信息查询列表
     *
     * @param equipType 设备类别
     * @return List<设备信息服务对象>
     */
    List<EquipEntity> getListByType(String equipType);

    /**
     * 设备信息查询列表
     *
     * @param equipName 设备名称
     * @param equipType 设备类别
     * @return List<设备信息服务对象>
     */
    List<EquipEntity> getListByTypeName(String equipName,String equipType);

    /**
     *通过id获取设备信息（连接dict)
     * @param id 编码
     * @return 设备信息对象
     */
    EquipEntity selectEquipById(String id);

    /**
     * 设备信息新建
     *
     * @param equip 实体对象
     * @param user 当前用户
     */
    boolean addEquip(EquipEntity equip, ShiroUser user);

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
    boolean editEquip(EquipEntity equip, ShiroUser user);

    /**
     * 设备维保到期判断，并更新设备维保状态
     * @return
     */
    void warn() throws ParseException;

    /**
     * 编辑设备冻结状态
     *
     * @param equipId 设备id
     * @param state      状态 "ENABLE"非冻结状态
     * @return
     */
    int setStatus(String equipId, String state);

}
