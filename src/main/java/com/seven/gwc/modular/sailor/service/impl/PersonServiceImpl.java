package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.service.PersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 船员信息服务实现类
 *
 * @author : LDY
 * @date : 2020-02-21
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, PersonEntity> implements PersonService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<PersonEntity> selectPerson(String personName){
        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.<PersonEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(personName),PersonEntity::getPersonName,personName);
        return personMapper.selectList(lambdaQuery);
    }

    @Override
    public void addPerson(PersonEntity person, ShiroUser user) {
        personMapper.insert(person);
    }

    @Override
    public void deletePerson(Long personId, ShiroUser user) {
        personMapper.deleteById(personId);
    }

    @Override
    public void editPerson(PersonEntity person, ShiroUser user) {
        personMapper.updateById(person);
    }

}
