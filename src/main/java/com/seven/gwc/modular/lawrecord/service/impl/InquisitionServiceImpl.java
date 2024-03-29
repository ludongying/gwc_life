package com.seven.gwc.modular.lawrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.dao.InquisitionMapper;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.InquisitionDO;
import com.seven.gwc.modular.lawrecord.dto.InquisitionDTO;
import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.service.InquisitionService;
import com.seven.gwc.modular.lawrecord.service.InstrumentService;
import com.seven.gwc.modular.lawrecord.service.LawRecordService;
import com.seven.gwc.modular.lawrecord.vo.InquisitionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;


/**
 * description : 勘验笔录服务实现类
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Service
@Slf4j
public class InquisitionServiceImpl extends ServiceImpl<InquisitionMapper, InquisitionEntity> implements InquisitionService {
    @Autowired
    private LawRecordService lawRecordService;
    @Autowired
    private InstrumentService instrumentService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateInquisition(InquisitionVO vo) {
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
        instrumentService.generateInstrument(vo.getId(), InquisitionEntity.class);
        return baseResult;
    }

    @Override
    public InquisitionDTO detail(String id) {
        InquisitionEntity inquisitionEntity = this.getById(id);
        if(Objects.nonNull(inquisitionEntity)){
            InquisitionDTO inquisitionDTO=new InquisitionDTO();
            if(Objects.nonNull(inquisitionEntity)){
                BeanUtils.copyProperties(inquisitionEntity,inquisitionDTO);
            }
            return inquisitionDTO;
        }
        return null;
    }

    @Override
    public Map<String, String> getParams(String id) {
        InquisitionEntity entity = this.getById(id);
        if(Objects.nonNull(entity)){
            return new InquisitionDO(entity).toMap();
        }
        return null;
    }

}
