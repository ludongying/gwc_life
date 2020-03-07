package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 执法人员服务类
 *
 * @author : ZZL
 * @date : 2020-02-29
 */

public interface OperatorService extends IService<OperatorEntity> {


    /**
     * 更新执法人员
     * @param userId
     * @param operators
     * @param recordId
     * @return
     */
    boolean updateOperator(String userId,String operators, String recordId);

    /**
     * 根据记录获取执法人员
     * @param recordId
     * @return
     */
    List<OperatorEntity> getByRecord(String recordId);

}
