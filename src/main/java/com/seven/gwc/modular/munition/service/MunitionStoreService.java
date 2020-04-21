package com.seven.gwc.modular.munition.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionStoreEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 库存管理服务类
 *
 * @author : LDY
 * @date : 2020-04-20
 */

public interface MunitionStoreService extends IService<MunitionStoreEntity> {

    /**
     * 库存管理查询列表
     *
     * @param munitionId 物资id
     * @return List<库存管理服务对象>
     */
    MunitionStoreEntity getMunitionStore(String munitionId);

    /**
     * 库存管理新建
     *
     * @param munitionStore 实体对象
     * @param user 当前用户
     */
    boolean addMunitionStore(MunitionStoreEntity munitionStore, ShiroUser user);

    /**
     * 库存管理删除
     *
     * @param munitionStoreId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionStore(String munitionStoreId, ShiroUser user);

    /**
     * 库存管理编辑
     *
     * @param munitionStore 实体对象
     * @param user 当前用户
     */
    boolean editMunitionStore(MunitionStoreEntity munitionStore, ShiroUser user);

}
