package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileData;
import com.seven.gwc.modular.lawrecord.data.file.FileManager;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.sailor.dao.PersonMapper;
import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
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
    public List<CertificateShipEntity> selectCertificate(String certificateName, String ids, String personId){
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);
        String idsNew = shipMapper.selectById(personId).getCertificateId();
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
    public boolean addCertificate(CertificateShipEntity certificate, ShiroUser user, String personId) {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getId,certificate.getId());
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        if(certificateEntity!=null){
            return false;
        }
        certificateMapper.insert(certificate);
        //更新船员表证书
        LambdaQueryWrapper<ShipEntity> lamdaQueryShip = Wrappers.lambdaQuery();
        lamdaQueryShip.eq(ShipEntity::getId,personId);
        ShipEntity shipEntity = shipMapper.selectOne(lamdaQueryShip);
        String certificateids = shipEntity.getCertificateId();
        if(certificateids==null|| certificateids.isEmpty()){
            certificateids += certificate.getId();
        }else{
            certificateids += FileUtils.file_2_file_sep + certificate.getId();
        }
        shipEntity.setCertificateId(certificateids);
        shipMapper.updateById(shipEntity);
        return true;
    }

    @Override
    public void deleteCertificate(String certificateId, ShiroUser user, String personId) {
        //更新船员表
        LambdaQueryWrapper<ShipEntity> lamdaQueryShip = Wrappers.lambdaQuery();
        lamdaQueryShip.eq(ShipEntity::getId,personId);
        ShipEntity shipEntity = shipMapper.selectOne(lamdaQueryShip);
        ArrayList<String> certificateIds =
                Stream.of(shipEntity.getCertificateId().split(FileUtils.file_2_file_sep))
                        .collect(Collectors.toCollection(ArrayList<String>::new));
        certificateIds.removeAll(Collections.singleton(certificateId));
        String result = certificateIds.stream().collect(Collectors.joining(","));
        shipEntity.setCertificateId(result);
        shipMapper.updateById(shipEntity);
        //删除证书
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
