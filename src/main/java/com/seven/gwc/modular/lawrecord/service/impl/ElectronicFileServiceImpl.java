package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
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
public class ElectronicFileServiceImpl extends ServiceImpl<LawRecordMapper, LawRecordEntity> implements ElectronicFileService {
    @Autowired
    private LawRecordMapper lawRecordMapper;

    @Override
    public BaseResultPage<LawRecordDTO> getElectronicFileList(LawRecordVO lawRecordVO) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordDTO> lawRecordDTOS = lawRecordMapper.listLawRecord(lawRecordVO);
        PageInfo pageInfo = new PageInfo<>(lawRecordDTOS);
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
        return new BaseResultPage().createPage(pageInfo);
    }
}
