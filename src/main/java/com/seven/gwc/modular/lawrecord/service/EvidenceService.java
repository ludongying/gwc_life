package com.seven.gwc.modular.lawrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.entity.EvidenceEntity;
import com.seven.gwc.modular.lawrecord.vo.LawEvidenceVO;

/**
 * description : 证据服务类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */

public interface EvidenceService extends IService<EvidenceEntity> {

    /**
     * 更新证据
     * @param lawEvidenceVO
     * @return
     */
  BaseResult updateEvidence(LawEvidenceVO lawEvidenceVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
  BaseResult detail(String id);





}
