package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.AgencyMapper;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.service.OperatorService;
import com.seven.gwc.modular.lawrecord.vo.AgencyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.service.AgencyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * description : 办案机关服务实现类
 *
 * @author : ZZL
 * @date : 2020-02-28
 */
@Service
@Slf4j
public class AgencyServiceImpl extends ServiceImpl<AgencyMapper, AgencyEntity> implements AgencyService {


    @Autowired
    private LawRecordService lawRecordService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private AgencyMapper agencyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateAgency(AgencyVO agencyVO) {
         String id=agencyVO.getId();
         //新建
         if(Objects.isNull(id) || id.trim().isEmpty()){
             LawRecordEntity lawRecordEntity =
                     lawRecordService.createLawRecord(agencyVO.getUserId(),agencyVO.getLawType());
             agencyVO.setId(lawRecordEntity.getId());
         }
         //设置办案人员
         operatorService.updateOperator(agencyVO.getUserId(),agencyVO.getOperators(),agencyVO.getId());
         this.saveOrUpdate(agencyVO);
         BaseResult baseResult = new BaseResult(true, "");
         baseResult.setContent(agencyVO.getId());
         return baseResult;
    }

    @Override
    public AgencyDTO detail(String id) {
        AgencyDTO detail = agencyMapper.detail(id);
        if(Objects.nonNull(detail)){
            detail.setTimeStr();
            detail.setAddress();
            detail.setOperators(operatorService.getByRecord(id));
        }
        return detail;
    }

}
