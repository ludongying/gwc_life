package com.seven.gwc.modular.munition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 物资入库Mapper 接口
 *
 * @author : LDY
 * @date : 2020-04-07
 */
public interface MunitionInMapper extends BaseMapper<MunitionInEntity> {

    List<MunitionInEntity> selectMunitionInList(@Param("munitionIn") MunitionInEntity munitionIn, @Param("total") Integer total, @Param("size") Integer size);

    List<MunitionInEntity> getListSize(@Param("munitionIn") MunitionInEntity munitionIn);

    /**
     * 查询最大编号
     * @param dateCode 编号前缀（日期部分yyyyMMdd）
     * @return
     */
    Integer maxCode( @Param("dateCode") String dateCode);

}
