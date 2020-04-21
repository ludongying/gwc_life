package com.seven.gwc.modular.munition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionOutEntity;

import java.util.List;

/**
 * description : 物资出库服务类
 *
 * @author : LDY
 * @date : 2020-04-07
 */

public interface MunitionOutService extends IService<MunitionOutEntity> {

    /**
     * 物资出库查询列表
     *
     * @param munitionOut 名称
     * @return List<物资出库服务对象>
     */
    List<MunitionOutEntity> selectMunitionOut(MunitionOutEntity munitionOut, Integer total, Integer size);

    Integer getListSize(MunitionOutEntity munitionOut);

    /**
     * 物资出库新建
     *
     * @param munitionOut 实体对象
     * @param user 当前用户
     */
    boolean addMunitionOut(MunitionOutEntity munitionOut, ShiroUser user);

    /**
     * 物资出库删除
     *
     * @param munitionOutId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionOut(String munitionOutId, ShiroUser user);

    /**
     * 物资出库编辑
     *
     * @param munitionOut 实体对象
     * @param user 当前用户
     */
    boolean editMunitionOut(MunitionOutEntity munitionOut, ShiroUser user);

    /**
     * 获取出库表单最新标号
     * @return
     */
    String getAutoCode();

    /**
     * 设置保单状态
     * @return
     */
    int setStatus(String munitionOutId, String state, ShiroUser user);

    /**
     * 根据id获取物资出库详情
    * @param id
     * @return
     */
    MunitionOutEntity getMunitionOutDetail(String id);

    /**
     * 物资出库，更新库存表
     * @param id
     * @return
     */
    void updateStoreList(String id, ShiroUser user);


}
