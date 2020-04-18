package com.seven.gwc.modular.munition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.munition.entity.MunitionOutEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 物资出库Mapper 接口
 *
 * @author : LDY
 * @date : 2020-04-07
 */
public interface MunitionOutMapper extends BaseMapper<MunitionOutEntity> {

    List<MunitionOutEntity> selectMunitionOutList(@Param("munitionOut") MunitionOutEntity munitionOut, @Param("total") Integer total, @Param("size") Integer size);

    List<MunitionOutEntity> getListSize(@Param("munitionOut") MunitionOutEntity munitionOut);

    /**
     * 查询最大编号
     * @param dateCode 编号前缀（日期部分yyyyMMdd）
     * @return
     */
    Integer maxCode(@Param("dateCode") String dateCode);

}
