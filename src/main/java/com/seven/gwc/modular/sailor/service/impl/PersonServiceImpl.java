package com.seven.gwc.modular.sailor.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.dao.CertificateMapper;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.sailor.service.PersonService;
import com.seven.gwc.modular.sailor.vo.PersonVO;
import com.seven.gwc.modular.system.dao.PositionMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private CertificateMapper certificateMapper;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<PersonEntity> selectPerson(PersonEntity personEntity, Integer total, Integer size) {
//        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.<PersonEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(personEntity), PersonEntity::getPersonName, personEntity);
        List<PersonEntity> persons = personMapper.PersonEntityList(personEntity, total, size);
        for (PersonEntity person : persons) {
            if(person.getPositionId()!=null) {
                String[] positionIds = person.getPositionId().split(FileUtils.file_2_file_sep);
                String positions = "";
                for (int i = 0; i < positionIds.length; i++) {
                    if (positionIds[i] != null && !positionIds[i].isEmpty()) {
                        if (positions.isEmpty()) {
                            positions = positionMapper.selectById(positionIds[i]).getName();
                        } else {
                            positions += FileUtils.file_2_file_sep + positionMapper.selectById(positionIds[i]).getName();
                        }
                    }
                }
                person.setPositionName(positions);
            }
        }
        return persons;
    }

    @Override
    public Integer getListSize(PersonEntity personEntity) {
        List<PersonEntity> list =  personMapper.getListSize(personEntity);
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPerson(PersonEntity person, ShiroUser user) {
        //先判断person表是否存在
        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PersonEntity::getIdNumber, person.getIdNumber()).eq(PersonEntity::getDeleteFlag, 1);
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
                userEntityInput.setSex(person.getSex());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userEntityInput.setUpdateTime(new Date());
                userEntityInput.setUpdateUser(user.getId());
                userMapper.updateById(userEntityInput);
            } else {
                userEntityInput.setName(person.getPersonName());
                userEntityInput.setBirthday(person.getBirthday());
                userEntityInput.setSex(person.getSex());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userEntityInput.setSynFlag(false);
                userEntityInput.setStatus("ENABLE");
                userEntityInput.setCreateTime(new Date());
                userEntityInput.setCreateUser(user.getId());
                userMapper.insert(userEntityInput);
                //获取新插入的user表id赋值给person表中的person_id
                LambdaQueryWrapper<UserEntity> lambdaQueryUserNew = Wrappers.<UserEntity>lambdaQuery();
                lambdaQueryUserNew.eq(UserEntity::getId, userEntityInput.getId());
                UserEntity userEntityNew = userMapper.selectOne(lambdaQueryUserNew);
                person.setPersonId(userEntityNew.getId());
            }
            person.setSynFlag(false);
            person.setDeleteFlag(true);
            person.setCreateDate(new Date());
            person.setCreatePerson(user.getId());
            personMapper.insert(person);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePerson(String id, ShiroUser user) {
        PersonEntity personEntity = personMapper.selectById(id);
        if (personEntity != null) {
            personEntity.setDeleteFlag(false);
            personEntity.setSynFlag(false);
            personEntity.setUpdateDate(new Date());
            personEntity.setUpdatePerson(user.getId());
        }
        personMapper.updateById(personEntity);
//            personMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editPerson(PersonEntity person, ShiroUser user) {
        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PersonEntity::getIdNumber, person.getIdNumber()).eq(PersonEntity::getDeleteFlag, 1).ne(PersonEntity::getId, person.getId());
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
                userEntityInput.setSex(person.getSex());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userEntityInput.setUpdateTime(new Date());
                userEntityInput.setUpdateUser(user.getId());
                userMapper.updateById(userEntityInput);
            } else {
                userEntityInput.setName(person.getPersonName());
                userEntityInput.setBirthday(person.getBirthday());
                userEntityInput.setSex(person.getSex());
                userEntityInput.setPhone(person.getPhone());
                userEntityInput.setEmail(person.getEmail());
                userEntityInput.setPositionId(person.getPositionId());
                userEntityInput.setSynFlag(false);
                userEntityInput.setStatus("ENABLE");
                userEntityInput.setCreateTime(new Date());
                userEntityInput.setCreateUser(user.getId());
                userMapper.insert(userEntityInput);
                //获取新插入的user表id赋值给person表中的person_id
                LambdaQueryWrapper<UserEntity> lambdaQueryUserNew = Wrappers.<UserEntity>lambdaQuery();
                lambdaQueryUserNew.eq(UserEntity::getId, userEntityInput.getId());
                UserEntity userEntityNew = userMapper.selectOne(lambdaQueryUserNew);
                person.setPersonId(userEntityNew.getId());
            }
            person.setUpdateDate(new Date());
            person.setUpdatePerson(user.getId());
            personMapper.updateById(person);
        }
        return true;
    }

    @Override
    public PersonEntity getOneById(String id) {
//        PersonEntity personEntity = new PersonEntity();
//        personEntity.setId(id);
//        List<PersonEntity> list = personMapper.PersonEntityList(personEntity);
//        if(list.size()<=0){
//            return null;
//        }
//        return list.get(0);
        return  personMapper.getPerson(id);
    }

    @Override
    public JSONArray listPersons(String ids) {
        LambdaQueryWrapper<PersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PersonEntity::getPersonId, ids).orderByAsc(PersonEntity::getPersonId);
        List<PersonEntity> personEntities = personMapper.PersonNamesEntityList(ids);

        JSONArray jsonArray = new JSONArray();
        for (PersonEntity personEntity : personEntities) {
            JSONObject jsonObject = new JSONObject();
            if (!SysConsts.STR_NULL.equals(ids) && ids != null && ids.length() > 0) {
                for (String id : ids.split(SysConsts.STR_COMMA)) {
                    if (personEntity.getId().equals(id)) {
                        jsonObject.put("selected", "selected");
                    }
                }
            }
            jsonObject.put("value", personEntity.getPersonId());
            jsonObject.put("name", personEntity.getPersonName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public List<PersonVO> listLawPersons() {
        List<PersonVO> listVo = new ArrayList<>();
        List<PersonEntity> list = personMapper.PersonLawEntityList();
        for (PersonEntity personEntity : list) {
            if (personEntity != null) {
                PersonVO personVO = new PersonVO();
                personVO.setId(personEntity.getId());
                personVO.setPersonName(personEntity.getPersonName());
                List<CertificateEntity> listCertificate = certificateMapper.CertificateLawEntityList(personEntity.getCertificateId());
                if(listCertificate.size()>0){
                    CertificateEntity certificateEntity = listCertificate.get(0);
                    personVO.setLawCode(certificateEntity.getCertificateId());
                }
                listVo.add(personVO);
            }
        }
        return listVo;
    }

    @Override
    public List<PersonEntity> listPersonsByDept(String deptId) {
        return personMapper.PersonsByDeptList(deptId);
    }

}
