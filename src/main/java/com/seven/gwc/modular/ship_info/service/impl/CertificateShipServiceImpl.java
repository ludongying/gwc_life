package com.seven.gwc.modular.ship_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
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
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<CertificateShipEntity> selectCertificate(CertificateShipEntity certificate, String shipId, Integer total, Integer size) {
//        LambdaQueryWrapper<CertificateEntity> lambdaQuery = Wrappers.<CertificateEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(certificateName),CertificateEntity::getName,certificateName);
        String idsNew = shipMapper.selectById(shipId).getCertificateId();
        List<CertificateShipEntity> list = certificateMapper.CertificateEntityList(certificate, idsNew, total, size);
        for (CertificateShipEntity certificateEntity : list) {
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
    public Integer getListSize(CertificateShipEntity certificate, String shipId) {
        String idsNew = shipMapper.selectById(shipId).getCertificateId();
        List<CertificateShipEntity> list =  certificateMapper.getListSize(certificate,idsNew);
        return list.size();
    }

    @Override
    public boolean addCertificate(CertificateShipEntity certificate, ShiroUser user, String shipId) throws ParseException {
        //判断证书编码是否存在
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getCertificateId, certificate.getCertificateId()).eq(CertificateShipEntity::getDeleteFlag, 1);
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        //确定是否为船舶证书
        if(certificateEntity != null){
            DictEntity dictEntity = dictMapper.selectById(certificateEntity.getOwnerType());
            if (dictEntity.getName().equals("船舶证书")){
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
            int intervalDays = daysBetween(date,certificate.getOutDate());
            if(intervalDays <0){//已过期
                certificate.setState(2);
            }else{
                if(intervalDays <= certificate.getWindowPhase()){//即将过期
                    certificate.setState(1);
                }else{//正常
                    certificate.setState(0);
                }
            }
        }
        certificateMapper.insert(certificate);
        //更新船员表证书
        LambdaQueryWrapper<ShipEntity> lamdaQueryShip = Wrappers.lambdaQuery();
        lamdaQueryShip.eq(ShipEntity::getId, shipId);
        ShipEntity shipEntity = shipMapper.selectOne(lamdaQueryShip);
        String certificateids = shipEntity.getCertificateId();
        if (certificateids == null || certificateids.isEmpty()) {
            certificateids += certificate.getId();
        } else {
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
        lamdaQueryShip.eq(ShipEntity::getId, shipId);
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
    public boolean editCertificate(CertificateShipEntity certificate, ShiroUser user) throws ParseException {
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(CertificateShipEntity::getCertificateId, certificate.getCertificateId()).eq(CertificateShipEntity::getDeleteFlag, 1).ne(CertificateShipEntity::getId, certificate.getId());
        CertificateShipEntity certificateEntity = certificateMapper.selectOne(lambdaQuery);
        //确定是否为船舶证书
        if(certificateEntity != null){
            DictEntity dictEntity = dictMapper.selectById(certificateEntity.getOwnerType());
            if (dictEntity.getName().equals("船舶证书")){
                return false;
            }
        }
        certificate.setUpdateDate(new Date());
        certificate.setUpdatePerson(user.getId());
        //计算证书是否过期
        Date date = new Date();
        if (ToolUtil.isNotEmpty(certificate.getOutDate())) {
            int intervalDays = daysBetween(date,certificate.getOutDate());
            if(intervalDays <0){//已过期
                certificate.setState(2);
            }else{
                if(intervalDays <= certificate.getWindowPhase()){//即将过期
                    certificate.setState(1);
                }else{//正常
                    certificate.setState(0);
                }
            }
        }
        certificateMapper.updateById(certificate);
        return true;
    }

    @Override
    public void warn() throws ParseException {
        LambdaQueryWrapper<CertificateShipEntity> lambdaQuery = Wrappers.<CertificateShipEntity>lambdaQuery();
        List<CertificateShipEntity> list = certificateMapper.selectList(lambdaQuery);
        Date date = new Date();
        for (CertificateShipEntity certificateShipEntity : list) {
            int stateCal;
            if (ToolUtil.isNotEmpty(certificateShipEntity.getOutDate())) {
                int intervalDays = daysBetween(date,certificateShipEntity.getOutDate());
                if(intervalDays <0){//已过期
                    stateCal = 2;
                }else{
                    if(intervalDays <= certificateShipEntity.getWindowPhase()){//即将过期
                        stateCal = 1;
                    }else{//正常
                        stateCal = 0;
                    }
                }
                if(ToolUtil.isEmpty(certificateShipEntity.getState()) || stateCal != certificateShipEntity.getState())
                {
                    certificateShipEntity.setState(stateCal);
                    certificateMapper.updateById(certificateShipEntity);
                }
            }
        }
    }

    /**
     * 日期格式的计算
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return (int)between_days;
    }

}
