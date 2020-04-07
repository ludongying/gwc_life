package com.seven.gwc.modular.worn_his.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.worn_his.entity.ShipEquipmentWarnEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 历史报警服务类
 *
 * @author : 李晓晖
 * @date : 2020-03-13
 */

public interface ShipEquipmentWarnService extends IService<ShipEquipmentWarnEntity> {

    /**
     * 历史报警查询列表
     *
     * @param shipEquipmentWarnName 名称
     * @return List<历史报警服务对象>
     */
    List<ShipEquipmentWarnEntity> selectShipEquipmentWarn(String shipEquipmentWarnName);

    /**
     * 历史报警新建
     *
     * @param shipEquipmentWarn 实体对象
     * @param user 当前用户
     */
    void addShipEquipmentWarn(ShipEquipmentWarnEntity shipEquipmentWarn, ShiroUser user);

    /**
     * 历史报警删除
     *
     * @param shipEquipmentWarnId 唯一标识
     * @param user 当前用户
     */
    void deleteShipEquipmentWarn(String shipEquipmentWarnId, ShiroUser user);

    /**
     * 历史报警编辑
     *
     * @param shipEquipmentWarn 实体对象
     * @param user 当前用户
     */
    void editShipEquipmentWarn(ShipEquipmentWarnEntity shipEquipmentWarn, ShiroUser user);

}
