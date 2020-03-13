package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.sailor.dao.CertificateMapper;
import com.seven.gwc.modular.sailor.service.CertificateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public List<CertificateEntity> selectCertificateAll(String certificateName){
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);

        List<CertificateEntity> list = certificateMapper.CertificateEntityListAll(certificateName);
        for(CertificateEntity certificateEntity : list){
            if(ToolUtil.isNotEmpty(certificateEntity.getCertificateType())){
                DictEntity certificateTypeDict = dictMapper.selectById(certificateEntity.getCertificateType());
                if(certificateTypeDict!= null){
                    certificateEntity.setCertificateTypeName(certificateTypeDict.getName());
                }
            }
            if(ToolUtil.isNotEmpty(certificateEntity.getOwnerType())){
                DictEntity ownerTypeDict = dictMapper.selectById(certificateEntity.getOwnerType());
                if(ownerTypeDict != null){
                    certificateEntity.setOwnerTypeName(ownerTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<CertificateEntity> selectCertificate(String certificateName, String ids){
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);

        List<CertificateEntity> list = certificateMapper.CertificateEntityList(certificateName,ids);
        for(CertificateEntity certificateEntity : list){
            if(ToolUtil.isNotEmpty(certificateEntity.getCertificateType())){
                DictEntity certificateTypeDict = dictMapper.selectById(certificateEntity.getCertificateType());
                if(certificateTypeDict!= null){
                    certificateEntity.setCertificateTypeName(certificateTypeDict.getName());
                }
            }
            if(ToolUtil.isNotEmpty(certificateEntity.getOwnerType())){
                DictEntity ownerTypeDict = dictMapper.selectById(certificateEntity.getOwnerType());
                if(ownerTypeDict != null){
                    certificateEntity.setOwnerTypeName(ownerTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCertificate(CertificateEntity certificate, ShiroUser user, String personId) {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateEntity::getId,certificate.getId());
        CertificateEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        if(certificateEntity!=null){
            return false;
        }
        certificateMapper.insert(certificate);
        //更新船员表证书
        LambdaQueryWrapper<PersonEntity> lamdaQueryPerson = Wrappers.lambdaQuery();
        lamdaQueryPerson.eq(PersonEntity::getId,personId);
        PersonEntity personEntity = personMapper.selectOne(lamdaQueryPerson);
        String certificateids = personEntity.getCertificateId();
        if(certificateids==null|| certificateids.isEmpty()){
            certificateids += certificate.getId();
        }else{
            certificateids += FileUtils.file_2_file_sep + certificate.getId();
        }
        personEntity.setCertificateId(certificateids);
        personMapper.updateById(personEntity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCertificate(String certificateId, ShiroUser user, String personId) {
        //更新船员表
        LambdaQueryWrapper<PersonEntity> lamdaQueryPerson = Wrappers.lambdaQuery();
        lamdaQueryPerson.eq(PersonEntity::getId,personId);
        PersonEntity personEntity = personMapper.selectOne(lamdaQueryPerson);
        ArrayList<String> certificateIds =
                Stream.of(personEntity.getCertificateId().split(FileUtils.file_2_file_sep))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
        certificateIds.removeAll(Collections.singleton(certificateId));
        String result = certificateIds.stream().collect(Collectors.joining(","));
        personEntity.setCertificateId(result);
        personMapper.updateById(personEntity);
        //删除证书
        certificateMapper.deleteById(certificateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editCertificate(CertificateEntity certificate, ShiroUser user) {
        certificateMapper.updateById(certificate);
    }

    @Override
    public CertificateEntity getCertificateById(String id) {
        CertificateEntity certificateEntity = certificateMapper.selectById(id);
        if (ToolUtil.isNotEmpty(certificateEntity.getAttachFilePath())) {
            List<FileData> files = fileManager.listFile(certificateEntity.getAttachFilePath());
            String urls = "";
            for(FileData fileData: files){
                urls += fileData.getUrl() + FileUtils.file_2_file_sep;
            }
            certificateEntity.setAttachFilePath(urls);
        }
        return certificateEntity;
    }

}
