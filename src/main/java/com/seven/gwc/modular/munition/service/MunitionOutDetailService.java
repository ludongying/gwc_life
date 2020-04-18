package com.seven.gwc.modular.munition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.munition.entity.MunitionOutDetailEntity;

import java.util.List;

/**
 * description : 物资出库详情服务类
 *
 * @author : LDY
 * @date : 2020-04-09
 */

public interface MunitionOutDetailService extends IService<MunitionOutDetailEntity> {

    /**
     * 物资出库详情查询列表
     *
     * @param munitionOutDetail 出库物资详情实体
     * @return List<物资出库详情服务对象>
     */
    List<MunitionOutDetailEntity> selectMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, String munitionMainId, Integer total, Integer size);

    Integer getListSize(MunitionOutDetailEntity munitionOutDetail, String munitionMainId);

    /**
     * 物资出库详情新建
     *
     * @param munitionOutDetail 实体对象
     * @param user 当前用户
     */
    void addMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, ShiroUser user);

    /**
     * 物资出库详情删除
     *
     * @param munitionOutDetailId 唯一标识
     * @param user 当前用户
     */
    void deleteMunitionOutDetail(String munitionOutDetailId, String munitionOutId, ShiroUser user);

    /**
     * 物资出库详情编辑
     *
     * @param munitionOutDetail 实体对象
     * @param user 当前用户
     */
    boolean editMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, ShiroUser user);

}
