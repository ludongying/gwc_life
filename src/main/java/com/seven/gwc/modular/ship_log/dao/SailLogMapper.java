package com.seven.gwc.modular.ship_log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.ship_log.entity.SailLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 航行日志Mapper 接口
 *
 * @author : 李晓晖
 * @date : 2020-03-18
 */
public interface SailLogMapper extends BaseMapper<SailLogEntity> {

    /**
     * 根据船员信息查询船员列表
     * @param sailLogEntity 船员实体
     * @return
     */
    List<SailLogEntity> ShiplogEntityList(@Param("Shiplog") SailLogEntity sailLogEntity);

}
