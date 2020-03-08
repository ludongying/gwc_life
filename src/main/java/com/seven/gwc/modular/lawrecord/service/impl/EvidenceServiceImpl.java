package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.EvidenceDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.EvidenceVO;
import com.seven.gwc.modular.lawrecord.vo.LawEvidenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.EvidenceEntity;
import com.seven.gwc.modular.lawrecord.dao.EvidenceMapper;
import com.seven.gwc.modular.lawrecord.service.EvidenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * description : 证据服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Service
public class EvidenceServiceImpl extends ServiceImpl<EvidenceMapper, EvidenceEntity> implements EvidenceService {

    @Autowired
    private LawRecordService lawRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateEvidence(LawEvidenceVO vo) {
        String id=vo.getId();
        //新建
        if(Objects.isNull(id) || id.trim().isEmpty()){
            LawRecordEntity lawRecordEntity =
                    lawRecordService.createLawRecord(vo.getUserId(),vo.getLawType());
            vo.setId(lawRecordEntity.getId());
        }
        List<EvidenceVO> evidences = vo.getEvidences();
        ArrayList<EvidenceEntity> entities = new ArrayList<>(evidences);
        this.saveOrUpdateBatch(entities);
        BaseResult baseResult = new BaseResult(true, "");
        baseResult.setContent(vo.getId());
        return baseResult;
    }

    @Override
    public BaseResult detail(String id) {
        BaseResult result=new BaseResult();
        List<EvidenceDTO> datas=new ArrayList<>();
        result.setSuccess(true);
        LambdaQueryWrapper<EvidenceEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EvidenceEntity::getDeleteFlag,Boolean.TRUE).eq(EvidenceEntity::getRecordId,id);
        List<EvidenceEntity> list = this.list(wrapper);
        if(Objects.nonNull(list) && !list.isEmpty()){
            for (EvidenceEntity evidenceEntity : list) {
                datas.add(new EvidenceDTO(evidenceEntity));
            }
            result.setContent(datas);
        }
        return result;
    }
}
