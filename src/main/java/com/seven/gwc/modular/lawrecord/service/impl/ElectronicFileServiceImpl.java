package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.service.ElectronicFileService;
import com.seven.gwc.modular.lawrecord.vo.ElectronicFileListVO;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ElectronicFileServiceImpl implements ElectronicFileService {
    @Autowired
    private LawRecordMapper lawRecordMapper;

    @Override
    public List<LawRecordDTO> getElectronicFileList(LawRecordVO lawRecordVO) {
        List<LawRecordDTO> lawRecordDTOS = lawRecordMapper.listLawRecord(lawRecordVO);
        if (ToolUtil.isNotEmpty(lawRecordDTOS) && !lawRecordDTOS.isEmpty()) {
            lawRecordDTOS.stream().forEach(dto -> {
                if (ToolUtil.isNotEmpty(dto.getLawCaseSource())) {
                    dto.setLawCaseSourceName(LawCaseSourceEnum.findByCode(dto.getLawCaseSource()).getMessage());
                }
                if (ToolUtil.isNotEmpty(dto.getLawType())) {
                    dto.setLawTypeName(LawTypeEnum.findByCode(dto.getLawType()).getMessage());
                }
            });
        }
        return lawRecordDTOS;
    }
}
