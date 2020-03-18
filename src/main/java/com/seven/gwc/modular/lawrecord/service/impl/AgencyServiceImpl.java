package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.GwcConsts;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.AgencyMapper;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.AgencyService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.service.OperatorService;
import com.seven.gwc.modular.lawrecord.vo.AgencyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /**
     * {@code detail.setAddress();}
     */
    @Override
    public AgencyDTO detail(String id) {
        AgencyDTO detail = agencyMapper.detail(id);
        if(Objects.nonNull(detail)){
            detail.setTimeStr();
            //此处设置地址
            detail.setOperators(operatorService.getByRecord(id));
        }
        return detail;
    }

    @Override
    public String getLawCode(String fineCode) {
        Integer code = agencyMapper.maxCode(fineCode);
        code = Objects.isNull(code) ? 1 : ++code;
        return GwcConsts.getCodeStr(code);
    }

    @Override
    public BaseResult existLawCode(String fineCode, String code) {
        BaseResult result=new BaseResult();
        result.setSuccess(true);
        LambdaQueryWrapper<AgencyEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AgencyEntity::getLawCaseFineCode, fineCode).eq(AgencyEntity::getLawCaseCode, GwcConsts.getCode(code));
        int count = this.count(wrapper);
        if(count>0){
            result.setSuccess(false);
            result.setMessage("编号已被占用");
            result.setContent(getLawCode(fineCode));
        }
        return result;
    }

}
