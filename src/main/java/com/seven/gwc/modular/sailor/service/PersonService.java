package com.seven.gwc.modular.sailor.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 船员信息服务类
 *
 * @author : LDY
 * @date : 2020-02-21
 */

public interface PersonService extends IService<PersonEntity> {

    /**
     * 船员信息查询列表
     *
     * @param personName 名称
     * @return List<船员信息服务对象>
     */
    List<PersonEntity> selectPerson(String personName);

    /**
     * 船员信息新建
     *
     * @param person 实体对象
     * @param user 当前用户
     */
    void addPerson(PersonEntity person, ShiroUser user);

    /**
     * 船员信息删除
     *
     * @param personId 唯一标识
     * @param user 当前用户
     */
    void deletePerson(Long personId, ShiroUser user);

    /**
     * 船员信息编辑
     *
     * @param person 实体对象
     * @param user 当前用户
     */
    void editPerson(PersonEntity person, ShiroUser user);

}
