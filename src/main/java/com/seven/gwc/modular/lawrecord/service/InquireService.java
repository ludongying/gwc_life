package com.seven.gwc.modular.lawrecord.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.dto.InquireSupplementDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;

import java.util.List;
import java.util.Map;

/**
 * description : 询问笔录服务类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */

public interface InquireService extends IService<InquireEntity> {

    /**
     * 更新询问笔录
     * @param inquireVO
     * @return
     */
    BaseResult updateInquire(InquireVO  inquireVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    InquireDTO detail(String id);

    BaseResult detailList(String id);

    List<InquireDTO> inquireDTODetail(String id);

    /**
     * 获取询问笔录补录
     * @param id
     * @return
     */
    List<InquireSupplementDTO> getInquireSupplement(String id);

    /**
     * 获取生产询问笔录所有参数
     * @param id
     * @return
     */
    Map<String,String> getParams(String id);

    /**
     * 获取生产询问笔录补录参数
     * @param inquireEntity
     * @return
     */
    Map<String, String> getSupplementParams(InquireEntity inquireEntity);


    /**
     * 获取询问笔录补录
     * @param id
     * @return
     */
    List<InquireEntity> getSupplement(String id);
    /**
     * 获取询问笔录补录数量
     * @param id
     * @return
     */
    Integer getSupplementCount(String id);


}
