package com.seven.gwc.modular.munition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.munition.entity.MunitionOutDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 物资入库详情Mapper 接口
 *
 * @author : LDY
 * @date : 2020-04-09
 */
public interface MunitionOutDetailMapper extends BaseMapper<MunitionOutDetailEntity> {
    List<MunitionOutDetailEntity> selectMunitionList(@Param("munitionOutDetail") MunitionOutDetailEntity munitionOutDetail, @Param("munitionMainId") String munitionMainId, @Param("total") Integer total, @Param("size") Integer size);

    List<MunitionOutDetailEntity> getListSize(@Param("munitionOutDetail") MunitionOutDetailEntity munitionOutDetail, @Param("munitionMainId") String munitionMainId);
}
