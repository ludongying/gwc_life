package com.seven.gwc.modular.lawrecord.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.DecisionDTO;
import com.seven.gwc.modular.lawrecord.dto.PlotSeverityDTO;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.vo.DecisionVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取生产决定所有参数
     * @param id
     * @return
     */
    Map<String,String> getParams(String id);

    /**
     * 是否逃逸
     * @param id
     * @param lawType
     * @return
     */
    Boolean  shipStatusIsEscape (String id,Integer lawType);


    /**
     * 根据状态更新
     * @param id
     */
    void updateSeverity(String id);

    /**
     * 获取情节严重性
     * @return
     */
    List<PlotSeverityDTO> listSeverity(String id);
}
