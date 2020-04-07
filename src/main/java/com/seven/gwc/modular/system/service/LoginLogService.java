package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.modular.system.entity.LoginLogEntity;

import java.util.List;

/**
 * description : 登录历史服务类
 *
 * @author : SHQ
 * @date : 2019-10-11
 */

public interface LoginLogService extends IService<LoginLogEntity> {

    /**
     * 登录历史查询列表
     */
    List<LoginLogEntity> selectLoginLog(String loginLogName, String message, String beginTime, String endTime);

    /**
     * 添加登录日志
     *
     * @param logTypeEnum 日志类型
     * @param userId      用户id
     * @param msg         描述
     * @param ip          IP
     */
    void insert(LogTypeEnum logTypeEnum, String userId, String msg, String ip, String macAddress);
}
