package com.seven.gwc.modular.fish_info.dao;

import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 渔船信息Mapper 接口
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
public interface FishShipMapper extends BaseMapper<FishShipEntity> {

    List<FishShipEntity> getFishShipList(@Param("law") FishShipEntity fishShipEntity, @Param("total") Integer total, @Param("size") Integer size);

    List<FishShipEntity> getListSize(@Param("law") FishShipEntity fishShipEntity);

}
