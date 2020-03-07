package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 执法记录服务类
 *
 * @author : ZZL
 * @date : 2020-02-26
 */

public interface LawRecordService extends IService<LawRecordEntity> {

    /**
     * 获取地区
     * @return
     */
    List<StateData> getStates();

    /**
     * 创建执法记录
     * @param userId
     * @param lawType
     * @return
     */
    LawRecordEntity createLawRecord(String userId,Integer lawType);







}
