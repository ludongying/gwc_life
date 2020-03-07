package com.seven.gwc.modular.lawrecord.dao;

import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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
}
