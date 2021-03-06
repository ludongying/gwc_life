package com.seven.gwc.modular.lawrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquisitionDTO;
import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.vo.InquisitionVO;

import java.util.Map;


/**
 * description : 勘验笔录服务类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */

public interface InquisitionService extends IService<InquisitionEntity> {

    /**
     * 更新询问笔录
     * @param inquireVO
     * @return
     */
    BaseResult updateInquisition(InquisitionVO inquireVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    InquisitionDTO detail(String id);

    /**
     * 获取生产勘验所有参数
     * @param id
     * @return
     */
    Map<String,String> getParams(String id);

}
