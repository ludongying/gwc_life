package com.seven.gwc.modular.lawrecord.service.impl;


import com.seven.gwc.modular.lawrecord.data.local.LocData;
import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

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


}
