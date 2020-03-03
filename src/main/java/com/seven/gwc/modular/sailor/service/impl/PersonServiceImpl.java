package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.RelationEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.UserService;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<PersonEntity> selectPerson(PersonEntity personEntity) {
//        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.<PersonEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(personName), PersonEntity::getPersonName, personName);
        return personMapper.PersonEntityList(personEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPerson(PersonEntity person, ShiroUser user) {
        //先判断person表是否存在
        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PersonEntity::getIdNumber, person.getIdNumber());
        PersonEntity personEntity = personMapper.selectOne(lambdaQuery);
        if (personEntity != null) {
            return false;
        } else {
            //判断user表是否存在，存在则更新，不存在则插入
            LambdaQueryWrapper<UserEntity> lambdaQueryUser = Wrappers.<UserEntity>lambdaQuery();
            lambdaQueryUser.eq(UserEntity::getId, person.getPersonId());
            UserEntity userEntity = userMapper.selectOne(lambdaQueryUser);
            UserEntity userEntityInput = new UserEntity();
            if (userEntity != null) {
                userEntityInput.setId(person.getPersonId());
                userEntityInput.setName(person.getPersonName());
                userEntityInput.setBirthday(person.getBirthday());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userMapper.updateById(userEntityInput);
            } else {
                userEntityInput.setName(person.getPersonName());
                userEntityInput.setBirthday(person.getBirthday());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userMapper.insert(userEntityInput);
                //获取新插入的user表id赋值给person表中的person_id
                LambdaQueryWrapper<UserEntity> lambdaQueryUserNew = Wrappers.<UserEntity>lambdaQuery();
                lambdaQueryUserNew.eq(UserEntity::getName, person.getPersonName());
                UserEntity userEntityNew = userMapper.selectOne(lambdaQueryUserNew);
                person.setPersonId(userEntityNew.getId());
            }
            personMapper.insert(person);
        }
            return true;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public void deletePerson (String id, ShiroUser user){
            personMapper.deleteById(id);
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public boolean editPerson (PersonEntity person, ShiroUser user){
            LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.eq(PersonEntity::getIdNumber, person.getIdNumber()).ne(PersonEntity::getId, person.getId());
            PersonEntity personEntity = personMapper.selectOne(lambdaQuery);
            if (personEntity != null) {
                return false;
            }
           else {
                //判断user表是否存在，存在则更新，不存在则插入
                LambdaQueryWrapper<UserEntity> lambdaQueryUser = Wrappers.<UserEntity>lambdaQuery();
                lambdaQueryUser.eq(UserEntity::getId, person.getPersonId());
                UserEntity userEntity = userMapper.selectOne(lambdaQueryUser);
                UserEntity userEntityInput = new UserEntity();
                if (userEntity != null) {
                    userEntityInput.setId(person.getPersonId());
                    userEntityInput.setName(person.getPersonName());
                    userEntityInput.setBirthday(person.getBirthday());
                    userEntityInput.setPhone(person.getPhone());
                    userEntityInput.setEmail(person.getEmail());
                    userEntityInput.setPositionId(person.getPositionId());
                    userMapper.updateById(userEntityInput);
                } else {
                    userEntityInput.setName(person.getPersonName());
                    userEntityInput.setBirthday(person.getBirthday());
                    userEntityInput.setPhone(person.getPhone());
                    userEntityInput.setEmail(person.getEmail());
                    userEntityInput.setPositionId(person.getPositionId());
                    userMapper.insert(userEntityInput);
                    //获取新插入的user表id赋值给person表中的person_id
                    LambdaQueryWrapper<UserEntity> lambdaQueryUserNew = Wrappers.<UserEntity>lambdaQuery();
                    lambdaQueryUserNew.eq(UserEntity::getName, person.getPersonName());
                    UserEntity userEntityNew = userMapper.selectOne(lambdaQueryUserNew);
                    person.setPersonId(userEntityNew.getId());
                }
                personMapper.updateById(person);
            }
            return true;
        }

        @Override
        public PersonEntity getOneById (String id){
            return personMapper.PersonEntity(id);
        }
    }
