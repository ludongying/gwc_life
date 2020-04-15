package com.seven.gwc.modular.munition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;

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
     * @param munitionIn 名称
     * @return List<物资入库服务对象>
     */
    List<MunitionInEntity> selectMunitionIn(MunitionInEntity munitionIn, Integer total, Integer size);

    Integer getListSize(MunitionInEntity munitionIn);

    /**
     * 物资入库新建
     *
     * @param munitionIn 实体对象
     * @param user 当前用户
     */
    boolean addMunitionIn(MunitionInEntity munitionIn, ShiroUser user);

    /**
     * 物资入库删除
     *
     * @param munitionInId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionIn(String munitionInId, ShiroUser user);

    /**
     * 物资入库编辑
     *
     * @param munitionIn 实体对象
     * @param user 当前用户
     */
    boolean editMunitionIn(MunitionInEntity munitionIn, ShiroUser user);

    /**
     * 获取入库表单最新标号
     * @return
     */
    String getAutoCode();

    /**
     * 设置保单状态
     * @return
     */
    int setStatus(String munitionInId, String state);

}
