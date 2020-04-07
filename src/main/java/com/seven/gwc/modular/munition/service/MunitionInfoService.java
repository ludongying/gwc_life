package com.seven.gwc.modular.munition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionInfoEntity;

import java.util.List;

/**
 * description : 物资信息服务类
 *
 * @author : LDY
 * @date : 2020-04-03
 */

public interface MunitionInfoService extends IService<MunitionInfoEntity> {

    /**
     * 物资信息查询列表
     *
     * @param munition 物资实体
     * @return List<物资信息服务对象>
     */
    List<MunitionInfoEntity> selectMunitionInfo(MunitionInfoEntity munition, Integer total, Integer size);

    Integer getListSize(MunitionInfoEntity munition);

    /**
     * 物资信息新建
     *
     * @param munitionInfo 实体对象
     * @param user 当前用户
     */
    boolean addMunitionInfo(MunitionInfoEntity munitionInfo, ShiroUser user);

    /**
     * 物资信息删除
     *
     * @param munitionInfoId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionInfo(String munitionInfoId, ShiroUser user);

    /**
     * 物资信息编辑
     *
     * @param munitionInfo 实体对象
     * @param user 当前用户
     */
    boolean editMunitionInfo(MunitionInfoEntity munitionInfo, ShiroUser user);

}
