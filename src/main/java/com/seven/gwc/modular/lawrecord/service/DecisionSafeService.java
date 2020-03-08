package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.dto.DecisionSafeDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionSafeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.lawrecord.vo.DecisionSafeVO;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;


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
}
