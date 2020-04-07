package com.seven.gwc.modular.equipment_info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 设备信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-03-09
 */
public interface EquipMapper extends BaseMapper<EquipEntity> {

    List<EquipEntity> selectEquipList(@Param("equip") EquipEntity equip, @Param("total") Integer total, @Param("size") Integer size);

    List<EquipEntity> getListSize(@Param("equip") EquipEntity equip);

    List<EquipEntity> getListByTypeName(@Param("equipName") String equipName, @Param("equipType") String equipType);
}
