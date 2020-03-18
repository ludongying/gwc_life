package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.vo.ElectronicFileListVO;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;

import java.util.List;

public interface ElectronicFileService {
    List<LawRecordDTO> getElectronicFileList(LawRecordVO lawRecordVO);
}
