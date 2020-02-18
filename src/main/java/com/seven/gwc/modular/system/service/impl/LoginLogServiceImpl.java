package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.state.LogSucceedEnum;
import com.seven.gwc.core.state.LogTypeEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.LoginLogMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.LoginLogEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * description : 登录历史服务实现类
 *
 * @author : SHQ
 * @date : 2019-10-11
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogEntity> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<LoginLogEntity> selectLoginLog(String loginLogName, String message, String beginTime, String endTime) {
        LambdaQueryWrapper<LoginLogEntity> lambdaQuery = Wrappers.<LoginLogEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(loginLogName), LoginLogEntity::getLogName, loginLogName)
                .like(ToolUtil.isNotEmpty(message),LoginLogEntity::getMessage, message)
                .apply(ToolUtil.isNotEmpty(beginTime), "date_format(create_time,'%Y-%m-%d %H:i:s') >= {0}", beginTime)
                .apply(ToolUtil.isNotEmpty(endTime), "date_format(create_time,'%Y-%m-%d %H:i:s') <= {0}", endTime)
                .orderByDesc(LoginLogEntity::getCreateTime);
        return loginLogMapper.selectList(lambdaQuery);
    }

    @Override
    public void insert(LogTypeEnum logTypeEnum, Long userId, String msg, String ip, String macAddress) {
        LoginLogEntity loginLog = new LoginLogEntity();
        loginLog.setLogName(logTypeEnum.getMessage());
        if (userId != null) {
            UserEntity userEntity = userMapper.selectById(userId);
            loginLog.setUserName(userEntity.getName());
        }
        loginLog.setCreateTime(new Date());
        loginLog.setSucceed(LogSucceedEnum.SUCCESS.getMessage());
        loginLog.setIpAddress(ip);
        loginLog.setMessage(msg);
        loginLog.setMacAddress(macAddress);
        loginLogMapper.insert(loginLog);
    }
}
