package com.seven.gwc.modular.munition.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 物资入库服务类
 *
 * @author : LDY
 * @date : 2020-04-07
 */

public interface MunitionInService extends IService<MunitionInEntity> {

    /**
     * 物资入库查询列表
     *
     * @param munitionInandoutName 名称
     * @return List<物资入库服务对象>
     */
    List<MunitionInEntity> selectMunitionInandout(String munitionInandoutName);

    /**
     * 物资入库新建
     *
     * @param munitionInandout 实体对象
     * @param user 当前用户
     */
    void addMunitionInandout(MunitionInEntity munitionInandout, ShiroUser user);

    /**
     * 物资入库删除
     *
     * @param munitionInandoutId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionInandout(String munitionInandoutId, ShiroUser user);

    /**
     * 物资入库编辑
     *
     * @param munitionInandout 实体对象
     * @param user 当前用户
     */
    void editMunitionInandout(MunitionInEntity munitionInandout, ShiroUser user);

}
