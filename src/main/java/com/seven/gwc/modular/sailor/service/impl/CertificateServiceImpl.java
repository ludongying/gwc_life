package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import java.util.List;

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
    public boolean addCertificate(CertificateEntity certificate, ShiroUser user) {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateEntity::getId,certificate.getId());
        CertificateEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        if(certificateEntity!=null){
            return false;
        }
        certificateMapper.insert(certificate);
        return true;
    }

    @Override
    public void deleteCertificate(String certificateId, ShiroUser user) {
        certificateMapper.deleteById(certificateId);
    }

    @Override
    public void editCertificate(CertificateEntity certificate, ShiroUser user) {
        certificateMapper.updateById(certificate);
    }

}
