package com.seven.gwc.modular.sailor.dao;

import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 船员信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-02-21
 */
public interface PersonMapper extends BaseMapper<PersonEntity> {

    /**
     * 根据船员信息查询船员列表
     * @param personEntity 船员实体
     * @return
     */
    List<PersonEntity> PersonEntityList(@Param("person") PersonEntity personEntity);

    PersonEntity PersonEntity(@Param("id") String id);

}
