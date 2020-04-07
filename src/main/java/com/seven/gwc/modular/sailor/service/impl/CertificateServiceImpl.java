package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.dao.CertificateMapper;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.sailor.service.CertificateService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description : 证书信息服务实现类
 *
 * @author : LDY
 * @date : 2020-02-28
 */
@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, CertificateEntity> implements CertificateService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CertificateMapper certificateMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<CertificateEntity> selectCertificateAll(String certificateName) {
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);

        List<CertificateEntity> list = certificateMapper.CertificateEntityListAll(certificateName);
        for (CertificateEntity certificateEntity : list) {
            if (ToolUtil.isNotEmpty(certificateEntity.getCertificateType())) {
                DictEntity certificateTypeDict = dictMapper.selectById(certificateEntity.getCertificateType());
                if (certificateTypeDict != null) {
                    certificateEntity.setCertificateTypeName(certificateTypeDict.getName());
                }
            }
            if (ToolUtil.isNotEmpty(certificateEntity.getOwnerType())) {
                DictEntity ownerTypeDict = dictMapper.selectById(certificateEntity.getOwnerType());
                if (ownerTypeDict != null) {
                    certificateEntity.setOwnerTypeName(ownerTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<CertificateEntity> selectCertificate(CertificateEntity certificate, String personId, Integer total, Integer size) {
        String idsNew = personMapper.selectById(personId).getCertificateId();
        List<CertificateEntity> list = certificateMapper.CertificateEntityList(certificate, idsNew, total, size);
        for (CertificateEntity certificateEntity : list) {
            if (ToolUtil.isNotEmpty(certificateEntity.getCertificateType())) {
                DictEntity certificateTypeDict = dictMapper.selectById(certificateEntity.getCertificateType());
                if (certificateTypeDict != null) {
                    certificateEntity.setCertificateTypeName(certificateTypeDict.getName());
                }
            }
            if (ToolUtil.isNotEmpty(certificateEntity.getOwnerType())) {
                DictEntity ownerTypeDict = dictMapper.selectById(certificateEntity.getOwnerType());
                if (ownerTypeDict != null) {
                    certificateEntity.setOwnerTypeName(ownerTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    public Integer getListSize(CertificateEntity certificate,String personId) {
        String idsNew = personMapper.selectById(personId).getCertificateId();
        List<CertificateEntity> list =  certificateMapper.getListSize(certificate,idsNew);
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCertificate(CertificateEntity certificate, ShiroUser user, String personId) throws ParseException {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateEntity::getCertificateId, certificate.getCertificateId()).eq(CertificateEntity::getDeleteFlag, 1);
        CertificateEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        //确定是否为船员证书
        if (certificateEntity != null) {
            DictEntity dictEntity = dictMapper.selectById(certificateEntity.getOwnerType());
            if (dictEntity.getName().equals("船员证书")) {
                return false;
            }
        }
        certificate.setSynFlag(false);
        certificate.setDeleteFlag(true);
        certificate.setCreateDate(new Date());
        certificate.setCreatePerson(user.getId());
        //计算证书是否过期
        Date date = new Date();
        if (ToolUtil.isNotEmpty(certificate.getOutDate())) {
            int intervalDays = daysBetween(date, certificate.getOutDate());
            if (intervalDays < 0) {//已过期
                certificate.setState(2);
            } else {
                if (intervalDays <= certificate.getWindowPhase()) {//即将过期
                    certificate.setState(1);
                } else {//正常
                    certificate.setState(0);
                }
            }
        }
        certificateMapper.insert(certificate);
        //更新船员表证书
        LambdaQueryWrapper<PersonEntity> lamdaQueryPerson = Wrappers.lambdaQuery();
        lamdaQueryPerson.eq(PersonEntity::getId, personId);
        PersonEntity personEntity = personMapper.selectOne(lamdaQueryPerson);
        String certificateids = personEntity.getCertificateId();
        if (certificateids == null || certificateids.isEmpty()) {
            certificateids = certificate.getId();
        } else {
            certificateids += FileUtils.file_2_file_sep + certificate.getId();
        }
        personEntity.setCertificateId(certificateids);
        personEntity.setUpdateDate(new Date());
        personEntity.setUpdatePerson(user.getId());
        personMapper.updateById(personEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCertificate(String certificateId, ShiroUser user, String personId) {
        //更新船员表
        LambdaQueryWrapper<PersonEntity> lamdaQueryPerson = Wrappers.lambdaQuery();
        lamdaQueryPerson.eq(PersonEntity::getId, personId);
        PersonEntity personEntity = personMapper.selectOne(lamdaQueryPerson);
        ArrayList<String> certificateIds =
                Stream.of(personEntity.getCertificateId().split(FileUtils.file_2_file_sep))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
        certificateIds.removeAll(Collections.singleton(certificateId));
        String result = certificateIds.stream().collect(Collectors.joining(","));
        personEntity.setCertificateId(result);
        personEntity.setUpdateDate(new Date());
        personEntity.setUpdatePerson(user.getId());
        personMapper.updateById(personEntity);
        //逻辑删除证书
        CertificateEntity certificateEntity = certificateMapper.selectById(certificateId);
        if (certificateEntity != null) {
            certificateEntity.setDeleteFlag(false);
            certificateEntity.setSynFlag(false);
            certificateEntity.setUpdateDate(new Date());
            certificateEntity.setUpdatePerson(user.getId());
        }
        certificateMapper.updateById(certificateEntity);
//        certificateMapper.deleteById(certificateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editCertificate(CertificateEntity certificate, ShiroUser user) throws ParseException {
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateEntity::getCertificateId, certificate.getCertificateId()).eq(CertificateEntity::getDeleteFlag, 1).ne(CertificateEntity::getId, certificate.getId());
        CertificateEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        //确定是否为船员证书
        if (certificateEntity != null) {
            DictEntity dictEntity = dictMapper.selectById(certificateEntity.getOwnerType());
            if (dictEntity.getName().equals("船员证书")) {
                return false;
            }
        }
        certificate.setUpdateDate(new Date());
        certificate.setUpdatePerson(user.getId());
        //计算证书是否过期
        Date date = new Date();
        if (ToolUtil.isNotEmpty(certificate.getOutDate())) {
            int intervalDays = daysBetween(date, certificate.getOutDate());
            if (intervalDays < 0) {//已过期
                certificate.setState(2);
            } else {
                if (intervalDays <= certificate.getWindowPhase()) {//即将过期
                    certificate.setState(1);
                } else {//正常
                    certificate.setState(0);
                }
            }
        }
        certificateMapper.updateById(certificate);
        return true;
    }

    @Override
    public void warn() throws ParseException {
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
        List<CertificateEntity> list = certificateMapper.selectList(lambdaQuery);
        Date date = new Date();
        for (CertificateEntity certificateEntity : list) {
            int stateCal;
            if (ToolUtil.isNotEmpty(certificateEntity.getOutDate())) {
                int intervalDays = daysBetween(date, certificateEntity.getOutDate());
                if (intervalDays < 0) {//已过期
                    stateCal = 2;
                } else {
                    if (intervalDays <= certificateEntity.getWindowPhase()) {//即将过期
                        stateCal = 1;
                    } else {//正常
                        stateCal = 0;
                    }
                }
                if (ToolUtil.isEmpty(certificateEntity.getState()) || stateCal != certificateEntity.getState()) {
                    certificateEntity.setState(stateCal);
                    certificateMapper.updateById(certificateEntity);
                }
            }
        }
    }

    /**
     * 日期格式的计算
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return (int) between_days;
    }
}
