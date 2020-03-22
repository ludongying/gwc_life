package com.seven.gwc.modular.lawrecord.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;

import java.util.List;
import java.util.Map;

/**
 * description : 执法记录Mapper 接口
 *
 * @author : ZZL
 * @date : 2020-02-26
 */
public interface LawRecordMapper extends BaseMapper<LawRecordEntity> {

    /**
     * 获取列表
     * @param lawRecordVO
     * @return
     */
    List<LawRecordDTO> listLawRecord(LawRecordVO lawRecordVO);
    @Deprecated
    List<LawRecordDTO> listLawRecord2(LawRecordVO lawRecordVO);

    /**
     * 获取详情
     * @param id
     * @return
     */
    Map<String,String> detail(String id);

}
