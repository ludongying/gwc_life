package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.ElectronicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectronicFileServiceImpl extends ServiceImpl<LawRecordMapper, LawRecordEntity> implements ElectronicFileService {
    @Autowired
    private LawRecordMapper lawRecordMapper;

   /* @Override
    public BaseResultPage<LawRecordDTO> getElectronicFileList(LawRecordVO lawRecordVO) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordDTO> lawRecordDTOS = lawRecordMapper.listLawRecord(lawRecordVO);
        PageInfo pageInfo = new PageInfo<>(lawRecordDTOS);
        if (Objects.nonNull(lawRecordDTOS) && !lawRecordDTOS.isEmpty()) {
            lawRecordDTOS.stream().forEach(dto -> {
                if (Objects.nonNull(dto.getLawCaseSource())) {
                    dto.setLawCaseSourceName(LawCaseSourceEnum.findByCode(dto.getLawCaseSource()).getMessage());
                }
                //数据美化
                dto.setShipName(handleStr(dto.getShipName()));
                dto.setInvestigateName(handleStr(dto.getInvestigateName()));
                dto.setInvestigateTel(handleStr(dto.getInvestigateTel()));
            });
        }
        return new BaseResultPage().createPage(pageInfo);
    }*/
}
