package com.seven.gwc.modular.munition.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param munitionInfoName 名称
     * @return List<物资信息服务对象>
     */
    List<MunitionInfoEntity> selectMunitionInfo(String munitionInfoName);

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
