package com.seven.gwc.modular.equipment_info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.equipment_info.entity.EquipMaintainEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 设备维护Mapper 接口
 *
 * @author : LDY
 * @date : 2020-03-12
 */
public interface EquipMaintainMapper extends BaseMapper<EquipMaintainEntity> {

    /**
     * 获取维保列表
     * @param maintain
     * @return
     */
    List<EquipMaintainEntity> getMaintainList(@Param("maintain") EquipMaintainEntity maintain);

}
