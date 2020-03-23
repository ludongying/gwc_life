package com.seven.gwc.modular.lawrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;


public interface ElectronicFileService extends IService<LawRecordEntity> {
    BaseResultPage<LawRecordDTO> getElectronicFileList(LawRecordVO lawRecordVO);
}
