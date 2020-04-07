package com.seven.gwc.modular.work.service;

import com.alibaba.fastjson.JSONArray;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.work.entity.ShipWorkLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 工作日志记录服务类
 *
 * @author : 李晓晖
 * @date : 2020-03-06
 */

public interface ShipWorkLogService extends IService<ShipWorkLogEntity> {

    /**
     * 工作日志记录查询列表
     *
     * @param shipWorkLogName 名称
     * @return List<工作日志记录服务对象>
     */
    List<ShipWorkLogEntity> selectShipWorkLog(String shipWorkLogName);

    /**
     * 工作日志记录新建
     *
     * @param shipWorkLog 实体对象
     * @param user 当前用户
     */
    void addShipWorkLog(ShipWorkLogEntity shipWorkLog, ShiroUser user);

    /**
     * 工作日志记录删除
     *
     * @param shipWorkLogId 唯一标识
     * @param user 当前用户
     */
    void deleteShipWorkLog(String shipWorkLogId, ShiroUser user);

    /**
     * 工作日志记录编辑
     *
     * @param shipWorkLog 实体对象
     * @param user 当前用户
     */
    void editShipWorkLog(ShipWorkLogEntity shipWorkLog, ShiroUser user);

    /**
     * 获取日志信息列表
     *
     * @param shipWorkLog 实体对象
     * @param user 当前用户
     */
    JSONArray listLogs(ShipWorkLogEntity shipWorkLog,ShiroUser user);
}
