package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.ReasonDTO;
import com.seven.gwc.modular.lawrecord.vo.ReasonVO;

/**
 * @author : zzl
 * @Date: 2020-03-05
 * @description :
 */
public interface ReasonService {

    /**
     * 更新案由
     * @param reasonVO
     * @return
     */
    BaseResult updateReason(ReasonVO reasonVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    ReasonDTO detail(String id);
}
