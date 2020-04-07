package com.seven.gwc.modular.sailor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
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
    List<PersonEntity> PersonEntityList(@Param("person") PersonEntity personEntity, @Param("total") Integer total, @Param("size") Integer size);

    List<PersonEntity> getListSize(@Param("person")  PersonEntity personEntity);

    List<PersonEntity> PersonNamesEntityList(@Param("id") String id);

    /**
     * 获取所有执法人员信息
     * @return
     */
    List<PersonEntity> PersonLawEntityList();

    List<PersonEntity> PersonsByDeptList(@Param("deptId") String deptId);

    PersonEntity getPerson(@Param("id") String id);
}
