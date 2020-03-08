package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.data.local.LocData;
import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;

/**
 * description : 执法记录服务实现类
 *
 * @author : ZZL
 * @date : 2020-02-26
 */
@Service
@Slf4j
public class LawRecordServiceImpl extends ServiceImpl<LawRecordMapper, LawRecordEntity> implements LawRecordService {

    @Autowired
    private LocData locData;

    @Autowired
    private LawRecordMapper lawRecordMapper;

    @Override
    public List<StateData> getStates(){
       return locData.getStateDataList();
    }

    @Override
    public LawRecordEntity createLawRecord(String userId,Integer lawType) {
        LawRecordEntity lawRecordEntity=new LawRecordEntity();
        lawRecordEntity.init(userId);
        lawRecordEntity.setLawType(LawTypeEnum.findByCode(lawType));
        this.save(lawRecordEntity);
        return lawRecordEntity;
    }

    @Override
    public BaseResultPage<LawRecordDTO> listLawRecord(LawRecordVO lawRecordVO) {

        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordDTO> lawRecordDTOS = lawRecordMapper.listLawRecord(lawRecordVO);
        PageInfo pageInfo = new PageInfo<>(lawRecordDTOS);
        if(Objects.nonNull(lawRecordDTOS) && !lawRecordDTOS.isEmpty()){
            lawRecordDTOS.stream().forEach(dto -> dto.setLawCaseSourceName(LawCaseSourceEnum.findByCode(dto.getLawCaseSource()).getMessage()));
        }
        return new BaseResultPage().createPage(pageInfo);

    }


}
