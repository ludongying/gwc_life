package com.seven.gwc.modular.ship_info.dao;

import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.system.vo.ListBasicsEntityVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * description : 执法船信息管理Mapper 接口
 *
 * @author : LDY
 * @date : 2020-02-12
 */
public interface ShipMapper extends BaseMapper<ShipEntity> {

    /**
     * 根据船舶信息查询船舶列表
     * @param shipEntity 船舶实体
     * @return
     */
    List<ShipEntity> ShipEntityList(@Param("ship") ShipEntity shipEntity);
}
