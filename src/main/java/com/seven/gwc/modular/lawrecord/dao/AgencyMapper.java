package com.seven.gwc.modular.lawrecord.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;

/**
 * description : 办案机关Mapper 接口
 *
 * @author : ZZL
 * @date : 2020-02-28
 */
public interface  AgencyMapper extends BaseMapper<AgencyEntity> {

    /**
     * 详情
     * @param id
     * @return
     */
    AgencyDTO detail(String id);

    /**
     * 查询最大编号
     * @param fineCode
     * @return
     */
    Integer maxCode(Integer fineCode);
}
