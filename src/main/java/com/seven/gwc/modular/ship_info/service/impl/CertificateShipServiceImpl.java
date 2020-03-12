package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.ship_info.dao.CertificateShipMapper;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;
import com.seven.gwc.modular.ship_info.service.CertificateShipService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description : 证书信息服务实现类
 *
 * @author : LDY
 * @date : 2020-02-28
 */
@Service
public class CertificateShipServiceImpl extends ServiceImpl<CertificateShipMapper, CertificateShipEntity> implements CertificateShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CertificateShipMapper certificateMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private FileManager fileManager;

    @Override
    public List<CertificateShipEntity> selectCertificate(String certificateName, String ids){
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);

        List<CertificateShipEntity> list = certificateMapper.CertificateEntityList(certificateName,ids);
        for(CertificateShipEntity certificateEntity : list){
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
    public boolean addCertificate(CertificateShipEntity certificate, ShiroUser user) {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getId,certificate.getId());
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
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
    public void editCertificate(CertificateShipEntity certificate, ShiroUser user) {
        certificateMapper.updateById(certificate);
    }

    @Override
    public CertificateShipEntity getCertificateById(String id) {
        CertificateShipEntity certificateEntity = certificateMapper.selectById(id);
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
