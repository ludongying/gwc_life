package com.seven.gwc.modular.munition.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;

import java.util.List;

/**
 * description : 物资入库详情服务类
 *
 * @author : LDY
 * @date : 2020-04-09
 */

public interface MunitionInDetailService extends IService<MunitionInDetailEntity> {

    /**
     * 物资入库详情查询列表
     *
     * @param munitionInDetail 入库物资详情实体
     * @return List<物资入库详情服务对象>
     */
    List<MunitionInDetailEntity> selectMunitionInDetail(MunitionInDetailEntity munitionInDetail, String munitionMainId, Integer total, Integer size);

    Integer getListSize(MunitionInDetailEntity munitionInDetail, String munitionMainId);

    /**
     * 物资入库详情新建
     *
     * @param munitionInDetail 实体对象
     * @param user 当前用户
     */
    void addMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user);

    /**
     * 物资入库详情删除
     *
     * @param munitionInDetailId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionInDetail(String munitionInDetailId, String munitionInId,  ShiroUser user);

    /**
     * 物资入库详情编辑
     *
     * @param munitionInDetail 实体对象
     * @param user 当前用户
     */
    boolean editMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user);

}
