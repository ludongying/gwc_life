package com.seven.gwc.modular.system.service;

import com.alibaba.fastjson.JSONArray;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.system.entity.PositionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 岗位服务类
 *
 * @author : GD
 * @date : 2019-09-20
 */

public interface PositionService extends IService<PositionEntity> {

    /**
     * 获取岗位列表
     *
     * @param positionName 查询条件（岗位名）
     * @return
     */
    List<PositionEntity> selectPosition(String positionName);

    /**
     * 编辑岗位冻结状态
     *
     * @param positionId 岗位ID
     * @param state      状态 "ENABLE"非冻结状态
     * @return
     */
    int setStatus(String positionId, String state);

    /**
     * 获取岗位下拉多选框
     *
     * @param ids 编辑时，数据已关联ID，用","分隔
     * @return
     */
    JSONArray listPositions(String ids);

    /**
     * 根据id删除数据
     *
     * @param id 数据id
     * @return
     */
    boolean delete(String id);

    /**
     * 增加数据
     *
     * @param position
     * @return
     */
    BaseResult add(PositionEntity position);

    /**
     * 更新岗位，修改数据权限保存
     *
     * @param position 岗位信息
     * @param menuIds  数据权限(部门)ID
     * @return
     */
    BaseResult update(PositionEntity position, String menuIds);
}
