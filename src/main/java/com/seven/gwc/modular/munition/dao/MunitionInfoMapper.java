package com.seven.gwc.modular.munition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.munition.entity.MunitionInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 物资信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-04-03
 */
public interface MunitionInfoMapper extends BaseMapper<MunitionInfoEntity> {

    List<MunitionInfoEntity> selectMunitionList(@Param("munition") MunitionInfoEntity munition, @Param("total") Integer total, @Param("size") Integer size);

    List<MunitionInfoEntity> getListSize(@Param("munition") MunitionInfoEntity munition);
}
