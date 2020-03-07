package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.InquireVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.seven.gwc.modular.lawrecord.dao.InquireMapper;
import com.seven.gwc.modular.lawrecord.service.InquireService;

import java.util.Objects;


/**
 * description : 询问笔录服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Service
@Slf4j
public class InquireServiceImpl extends ServiceImpl<InquireMapper, InquireEntity> implements InquireService {
    @Autowired
    private LawRecordService lawRecordService;

    @Override
    public BaseResult updateInquire(InquireVO vo) {
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
    public InquireDTO detail(String id) {
        InquireEntity inquireEntity = this.getById(id);
        if(Objects.nonNull(inquireEntity)){
            InquireDTO inquireDTO=new InquireDTO();
            BeanUtils.copyProperties(inquireEntity,inquireDTO);
            inquireDTO.setAddress();
            return inquireDTO;
        }
        return null;
    }


}
