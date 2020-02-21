package com.seven.gwc.modular.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.vo.ListBasicsEntityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 用户Mapper 接口
 *
 * @author : GD
 * @date : 2019-08-01
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据用户id获取基础信息列表
     *
     * @param userId 用户id
     * @return
     */
    List<ListBasicsEntityVO> listBasicsEntity(Long userId);

    /**
     * 根据用户信息查询用户列表
     *
     * @param userEntity 用户实体
     * @return
     */
    List<UserEntity> userEntityList(@Param("user") UserEntity userEntity);

}
