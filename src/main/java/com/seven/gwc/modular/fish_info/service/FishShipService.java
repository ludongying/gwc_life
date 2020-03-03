package com.seven.gwc.modular.fish_info.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 渔船信息服务类
 *
 * @author : SHQ
 * @date : 2020-03-02
 */

public interface FishShipService extends IService<FishShipEntity> {

    /**
     * 渔船信息查询列表
     *
     * @param code 船编码
     * @param phone 船主手机
     * @param shipType 船类型
     * @return List<渔船信息服务对象>
     */
    List<FishShipEntity> selectFishShip(String code, String phone, String shipType);

    /**
     * 渔船信息新建
     *
     * @param fishShip 实体对象
     * @param user 当前用户
     */
    void addFishShip(FishShipEntity fishShip, ShiroUser user);

    /**
     * 渔船信息删除
     *
     * @param fishShipId 唯一标识
     * @param user 当前用户
     */
    void deleteFishShip(String fishShipId, ShiroUser user);

    /**
     * 渔船信息编辑
     *
     * @param fishShip 实体对象
     * @param user 当前用户
     */
    void editFishShip(FishShipEntity fishShip, ShiroUser user);

    /**
     * 获取渔船信息
     *
     * @param fishShipId 实体ID
     */
    FishShipEntity detailFishShip(String fishShipId);

}
