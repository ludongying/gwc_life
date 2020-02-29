package com.seven.gwc.modular.sailor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public List<CertificateEntity> selectCertificate(String certificateName){
        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);
        return certificateMapper.selectList(lambdaQuery);
    }

    @Override
    public void addCertificate(CertificateEntity certificate, ShiroUser user) {
        certificateMapper.insert(certificate);
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
