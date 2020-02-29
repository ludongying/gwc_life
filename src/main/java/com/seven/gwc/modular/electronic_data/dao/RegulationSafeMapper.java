package com.seven.gwc.modular.electronic_data.dao;

import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * description : 法律法规/航线安全Mapper 接口
 *
 * @author : SHQ
 * @date : 2020-02-25
 */
public interface RegulationSafeMapper extends BaseMapper<RegulationSafeEntity> {

    List<RegulationSafeEntity> selectRegulationSafeList(String regulationSafeName,String lawRegularId,String type);

}
