package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.ui.Model;

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

    /**
     * 创建执法记录
     * @param userId
     * @param lawType
     * @return
     */
    LawRecordEntity createAgencyRecord(String userId,Integer lawType);


    /**
     * 加载列表
     * @param lawRecordVO
     * @return
     */
    BaseResultPage<LawRecordDTO> listLawRecord(LawRecordVO lawRecordVO);

    /**
     * 执法记录作废
     * @param id
     * @return
     */
    BaseResult invalidRecord(String id);

    /**
     * 结案
     * @param id
     * @return
     */
    BaseResult finishRecord(String id);

    /**
     * 详情
     * @param id
     * @param model
     */
    void detail(String id,Model model);












}
