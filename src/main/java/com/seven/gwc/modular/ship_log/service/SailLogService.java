package com.seven.gwc.modular.ship_log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.ship_log.entity.SailLogEntity;

import java.util.List;

/**
 * description : 航行日志服务类
 *
 * @author : 李晓晖
 * @date : 2020-03-18
 */

public interface SailLogService extends IService<SailLogEntity> {

    /**
     * 航行日志查询列表
     *
     * @param sailLog 实体对象
     * @return List<航行日志服务对象>
     */
//    List<SailLogEntity> selectSailLog(String sailLogName);

    List<SailLogEntity> selectSailLog(SailLogEntity sailLog);

    /**
     * 航行日志新建
     *
     * @param sailLog 实体对象
     * @param user 当前用户
     */
    void addSailLog(SailLogEntity sailLog, ShiroUser user);

    /**
     * 航行日志删除
     *
     * @param sailLogId 唯一标识
     * @param user 当前用户
     */
    void deleteSailLog(String sailLogId, ShiroUser user);

    /**
     * 航行日志编辑
     *
     * @param sailLog 实体对象
     * @param user 当前用户
     */
    void editSailLog(SailLogEntity sailLog, ShiroUser user);

}
