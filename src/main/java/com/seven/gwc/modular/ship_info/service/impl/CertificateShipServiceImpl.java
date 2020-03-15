package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.ship_info.dao.CertificateShipMapper;
import com.seven.gwc.modular.ship_info.dao.ShipMapper;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.ship_info.service.CertificateShipService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class CertificateShipServiceImpl extends ServiceImpl<CertificateShipMapper, CertificateShipEntity> implements CertificateShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CertificateShipMapper certificateMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private ShipMapper shipMapper;

    @Override
    public List<CertificateShipEntity> selectCertificate(String certificateName, String shipId){
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);
        String idsNew = shipMapper.selectById(shipId).getCertificateId();
        List<CertificateShipEntity> list = certificateMapper.CertificateEntityList(certificateName,idsNew);
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
    public boolean addCertificate(CertificateShipEntity certificate, ShiroUser user, String shipId) {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getId,certificate.getId());
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        if(certificateEntity!=null){
            return false;
        }
        certificate.setSynFlag(false);
        certificate.setDeleteFlag(true);
        certificate.setCreateDate(new Date());
        certificate.setCreatePerson(user.getId());
        certificateMapper.insert(certificate);
        //更新船员表证书
        LambdaQueryWrapper<ShipEntity> lamdaQueryShip = Wrappers.lambdaQuery();
        lamdaQueryShip.eq(ShipEntity::getId,shipId);
        ShipEntity shipEntity = shipMapper.selectOne(lamdaQueryShip);
        String certificateids = shipEntity.getCertificateId();
        if(certificateids==null|| certificateids.isEmpty()){
            certificateids += certificate.getId();
        }else{
            certificateids += FileUtils.file_2_file_sep + certificate.getId();
        }
        shipEntity.setCertificateId(certificateids);
        shipEntity.setUpdateDate(new Date());
        shipEntity.setUpdatePerson(user.getId());
        shipMapper.updateById(shipEntity);
        return true;
    }

    @Override
    public void deleteCertificate(String certificateId, ShiroUser user, String shipId) {
        //更新船员表
        LambdaQueryWrapper<ShipEntity> lamdaQueryShip = Wrappers.lambdaQuery();
        lamdaQueryShip.eq(ShipEntity::getId,shipId);
        ShipEntity shipEntity = shipMapper.selectOne(lamdaQueryShip);
        ArrayList<String> certificateIds =
                Stream.of(shipEntity.getCertificateId().split(FileUtils.file_2_file_sep))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
        certificateIds.removeAll(Collections.singleton(certificateId));
        String result = certificateIds.stream().collect(Collectors.joining(","));
        shipEntity.setCertificateId(result);
        shipEntity.setUpdateDate(new Date());
        shipEntity.setUpdatePerson(user.getId());
        shipMapper.updateById(shipEntity);
        //逻辑删除证书
        CertificateShipEntity certificateEntity = certificateMapper.selectById(certificateId);
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
    public boolean editCertificate(CertificateShipEntity certificate, ShiroUser user) {
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getCertificateId, certificate.getCertificateId()).ne(CertificateShipEntity::getId, certificate.getId());
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        if (certificateEntity != null) {
            return false;
        }
        certificate.setUpdateDate(new Date());
        certificate.setUpdatePerson(user.getId());
        certificateMapper.updateById(certificate);
        return true;
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
