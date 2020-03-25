package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.AgencyMapper;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.AgencyDO;
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

import java.util.Map;
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
        BaseResult baseResult = new BaseResult(true, "");
         String id=agencyVO.getId();
         //新建
         if(Objects.isNull(id) || id.trim().isEmpty()){
             LawRecordEntity lawRecordEntity =
                     lawRecordService.createAgencyRecord(agencyVO.getUserId(),agencyVO.getLawType());
             agencyVO.setId(lawRecordEntity.getId());
         }
         //设置办案人员
         operatorService.updateOperator(agencyVO.getUserId(),agencyVO.getOperators(),agencyVO.getId());
         //查看编号是否被占用
        AgencyEntity agency = this.getById(agencyVO.getId());
        if(Objects.isNull(agency) || Objects.isNull(agency.getLawCaseCode())){
            boolean res = this.existLawCode(agencyVO.getLawCaseFineCode(), agencyVO.getLawCaseCode());
            if(!res){
                agencyVO.setLawCaseCode(this.getLawCode(agencyVO.getLawCaseFineCode()));
                baseResult.setMessage("案件编号已被占用 更改为:"+agencyVO.getLawCaseFineCode()+"罚"+agencyVO.getLawCaseCode()+"号");
            }
        }
         this.saveOrUpdate(agencyVO);
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
            detail.setPersons();
            detail.setLawCodeStr();
        }
        return detail;
    }

    @Override
    public Integer getLawCode(Integer fineCode) {
        Integer code = agencyMapper.maxCode(fineCode);
        return Objects.isNull(code) ? 1001 : ++code;
    }

    @Override
    public Map<String, Object> getParams(String id) {
        AgencyDTO detail = detail(id);
        if(Objects.nonNull(detail)){
            return  new AgencyDO(detail).toMap();
        }
        return null;
    }

    private boolean existLawCode(Integer fineCode, Integer code) {
        LambdaQueryWrapper<AgencyEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AgencyEntity::getLawCaseFineCode, fineCode).eq(AgencyEntity::getLawCaseCode, code);
        int count = this.count(wrapper);
        return count==0;
    }


}
