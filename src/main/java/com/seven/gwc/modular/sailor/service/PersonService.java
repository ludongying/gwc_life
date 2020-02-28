package com.seven.gwc.modular.sailor.service;

import com.alibaba.fastjson.JSONArray;
import com.seven.gwc.core.node.ZTreeNode;
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
     * @param personEntity 人员实体
     * @return List<船员信息服务对象>
     */
    List<PersonEntity> selectPerson(PersonEntity personEntity);

    /**
     * 船员信息新建
     *
     * @param person 实体对象
     * @param user 当前用户
     */
    boolean addPerson(PersonEntity person, ShiroUser user);

    /**
     * 船员信息删除
     *
     * @param id 唯一标识
     * @param user 当前用户
     */
    void deletePerson(String id, ShiroUser user);

    /**
     * 船员信息编辑
     *
     * @param person 实体对象
     * @param user 当前用户
     */
    void editPerson(PersonEntity person, ShiroUser user);

    /**
     * 通过id获取船员信息
     * @param id
     * @return
     */
    PersonEntity getOneById(String id);

}
