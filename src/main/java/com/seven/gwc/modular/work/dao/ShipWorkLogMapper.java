package com.seven.gwc.modular.work.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.work.entity.ShipWorkLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 工作日志记录Mapper 接口
 *
 * @author : 李晓晖
 * @date : 2020-03-06
 */
public interface ShipWorkLogMapper extends BaseMapper<ShipWorkLogEntity> {

    List<ShipWorkLogEntity> WorkLogList(@Param("WorkLog") ShipWorkLogEntity shipWorkLogEntity);


}
