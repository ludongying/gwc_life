package com.seven.gwc.modular.lawrecord.service.impl;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.dto.InquireSafeDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.InquireSafeVO;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.gwc.modular.lawrecord.entity.InquireSafeEntity;
import com.seven.gwc.modular.lawrecord.dao.InquireSafeMapper;
import com.seven.gwc.modular.lawrecord.service.InquireSafeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * description : 笔录服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-07
 */
@Service
public class InquireSafeServiceImpl extends ServiceImpl<InquireSafeMapper, InquireSafeEntity> implements InquireSafeService {

    @Autowired
    private LawRecordService lawRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateInquireSafe(InquireSafeVO vo) {
        String id=vo.getId();
        //新建
        if(Objects.isNull(id) || id.trim().isEmpty()){
            LawRecordEntity lawRecordEntity =
                    lawRecordService.createLawRecord(vo.getUserId(),vo.getLawType());
            vo.setId(lawRecordEntity.getId());
        }
        this.saveOrUpdate(vo);
        BaseResult baseResult = new BaseResult(true, "");
        baseResult.setContent(vo.getId());
        return baseResult;
    }

    @Override
    public InquireSafeDTO detail(String id) {
        InquireSafeEntity inquireSafeEntity = this.getById(id);
        if(Objects.nonNull(inquireSafeEntity)){
            InquireSafeDTO inquireSafeDTO=new InquireSafeDTO();
            BeanUtils.copyProperties(inquireSafeEntity,inquireSafeDTO);
            inquireSafeDTO.setAddress();
            return inquireSafeDTO;
        }
        return null;

    }


}
