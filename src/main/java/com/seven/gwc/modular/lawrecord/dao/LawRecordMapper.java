package com.seven.gwc.modular.lawrecord.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.lawrecord.dto.LawRecordDTO;
import com.seven.gwc.modular.lawrecord.dto.LawTypeDTO;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;

import java.util.List;

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

    /**
     * 这种查询会影响分页数据条数会出现问题
     * @param lawRecordVO
     * @return
     */
    @Deprecated
    List<LawRecordDTO> listLawRecord2(LawRecordVO lawRecordVO);

    /**
     * 根据记录id获取案件类型
     * @param id
     * @return
     */
    LawTypeDTO findLawType(String id);

    

}
