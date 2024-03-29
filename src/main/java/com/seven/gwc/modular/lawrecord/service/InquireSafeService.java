package com.seven.gwc.modular.lawrecord.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquireSafeDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireSafeEntity;
import com.seven.gwc.modular.lawrecord.vo.InquireSafeVO;

import java.util.Map;


/**
 * description : 笔录服务类
 *
 * @author : ZZL
 * @date : 2020-03-07
 */

public interface InquireSafeService extends IService<InquireSafeEntity> {

    /**
     * 更新询问笔录
     * @param inquireSafeVO
     * @return
     */
    BaseResult updateInquireSafe(InquireSafeVO inquireSafeVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    InquireSafeDTO detail(String id);

    /**
     * 获取安全笔录所有参数
     * @param id
     * @return
     */
    Map<String,String> getParams(String id);
}
