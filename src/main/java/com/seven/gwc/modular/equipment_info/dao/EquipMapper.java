package com.seven.gwc.modular.equipment_info.dao;

import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * description : 设备信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-03-09
 */
public interface EquipMapper extends BaseMapper<EquipEntity> {
    List<EquipEntity> selectEquipList(String equipName, String equipType);
}
