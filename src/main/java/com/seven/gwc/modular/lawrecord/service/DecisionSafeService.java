package com.seven.gwc.modular.lawrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionSafeDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionSafeEntity;
import com.seven.gwc.modular.lawrecord.vo.DecisionSafeVO;

import java.util.Map;


/**
 * description : 决定服务类
 *
 * @author : ZZL
 * @date : 2020-03-07
 */

public interface DecisionSafeService extends IService<DecisionSafeEntity> {

    /**
     * 更新询问笔录
     * @param decisionSafeVO
     * @return
     */
    BaseResult updateDecisionSafe(DecisionSafeVO decisionSafeVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    DecisionSafeDTO detail(String id);


    /**
     * 获取安全决定所有参数
     * @param id
     * @return
     */
    Map<String,String> getParams(String id);

    /**
     * 根据状态修改 严重性
     * @param id
     */
    void updateSeverity(String id);
}
