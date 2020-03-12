package com.seven.gwc.modular.ship_info.service;

import com.alibaba.fastjson.JSONArray;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 执法船信息管理服务类
 *
 * @author : LDY
 * @date : 2020-02-12
 */

public interface ShipService extends IService<ShipEntity> {

    /**
     * 获取船舶列表
     * @param shipEntity 查询条件（船舶实体）
     * @return
     */
    List<ShipEntity> selectShip(ShipEntity shipEntity);

    /**
     * 增加数据
     * @param ship
     * @return
     */
    boolean add(ShipEntity ship);

    /**
     * 更新船舶信息
     * @param ship
     * @return
     */
    boolean update(ShipEntity ship);

    /**
     * 根据id删除数据
     * @param id（数据id)
     * @return
     */
    boolean delete(String id);


    /**
     * 获取执法船下拉框
     *
     * @param id
     * @return
     */
    List<ShipEntity> listShips(String id);

    /**
     * 获取执法船详细信息（图片url转换）
     * @param id 表编码
     * @return
     */
    ShipEntity getShipById(String id);

}
