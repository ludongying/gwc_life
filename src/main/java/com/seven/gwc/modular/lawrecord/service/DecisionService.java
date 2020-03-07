package com.seven.gwc.modular.lawrecord.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;

/**
 * description : 决定服务类
 *
 * @author : ZZL
 * @date : 2020-03-06
 */

public interface DecisionService extends IService<DecisionEntity> {

    /**
     * 更新询问笔录
     * @param decisionVO
     * @return
     */
    BaseResult updateDecision(DecisionVO decisionVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    DecisionDTO detail(String id);
}
