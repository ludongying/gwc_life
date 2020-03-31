package com.seven.gwc.modular.equipment_info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.equipment_info.dao.EquipMapper;
import com.seven.gwc.modular.equipment_info.entity.EquipEntity;
import com.seven.gwc.modular.equipment_info.service.EquipService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * description : 设备信息服务实现类
 *
 * @author : LDY
 * @date : 2020-03-09
 */
@Service
public class EquipServiceImpl extends ServiceImpl<EquipMapper, EquipEntity> implements EquipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<EquipEntity> selectEquip(String equipName, String equipType){
//        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.<EquipEntity>lambdaQuery();
//        lambdaQuery.like(ToolUtil.isNotEmpty(equipName),EquipEntity::getName,equipName);
        List<EquipEntity> list = equipMapper.selectEquipList(equipName, equipType);
        for(EquipEntity equipEntity : list){
            if(ToolUtil.isNotEmpty(equipEntity.getState())){
                DictEntity certificateTypeDict = dictMapper.selectById(equipEntity.getState());
                if(certificateTypeDict!= null){
                    equipEntity.setStateDesp(certificateTypeDict.getName());
                }
            }
        }
        return list;
    }

    @Override
    public EquipEntity selectEquipById(String id) {
        EquipEntity equipEntity = equipMapper.selectById(id);
        DictEntity dictEntity = dictMapper.selectById(equipEntity.getType());
        equipEntity.setTypeDesp(dictEntity.getName());
        return equipEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEquip(EquipEntity equip, ShiroUser user) {
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipEntity::getSerialNumber,equip.getSerialNumber());
        EquipEntity equipEntity = equipMapper.selectOne(lambdaQuery);
        if(equipEntity != null)
        {
            return false;
        }
        equip.setCreateDate(new Date());
        equip.setCreatePerson(user.getId());
        equip.setSynFlag(false);
        equip.setDeleteFlag(true);
        equipMapper.insert(equip);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquip(String equipId, ShiroUser user) {
        EquipEntity equip = equipMapper.selectById(equipId);
        if (equip!=null){
            equip.setDeleteFlag(false);
            equip.setSynFlag(false);
            equip.setUpdateDate(new Date());
            equip.setUpdatePerson(user.getId());
        }
        equipMapper.updateById(equip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editEquip(EquipEntity equip, ShiroUser user) {
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(EquipEntity::getSerialNumber,equip.getSerialNumber()).ne(EquipEntity::getId,equip.getId());
        EquipEntity equipEntity = equipMapper.selectOne(lambdaQuery);
        if(equipEntity != null){
            return false;
        }
        equip.setUpdateDate(new Date());
        equip.setUpdatePerson(user.getId());
        equipMapper.updateById(equip);
        return true;

    }

    @Override
    public void warn() throws ParseException {
        LambdaQueryWrapper<EquipEntity> lambdaQuery = Wrappers.<EquipEntity>lambdaQuery();
        List<EquipEntity> list = equipMapper.selectList(lambdaQuery);
        Date date = new Date();
        for (EquipEntity equipEntity : list) {
            int stateCal;
            if (ToolUtil.isNotEmpty(equipEntity.getLastMaintenanceDate())) {
                //计算维保到期时间
                Calendar cal = Calendar.getInstance();
                cal.setTime(equipEntity.getLastMaintenanceDate());
                cal.add(Calendar.DATE, equipEntity.getMaintainCycle());
                //计算维保到期时间与当前时间的差值
                int intervalDays = daysBetween(date, cal.getTime());
                if (intervalDays < 0) {//已过期
                    stateCal = 2;
                } else {
                    if (intervalDays <= equipEntity.getWindowPhase()) {//即将过期
                        stateCal = 1;
                    } else {//正常
                        stateCal = 0;
                    }
                }
                if (ToolUtil.isEmpty(equipEntity.getStateWarn()) || stateCal != equipEntity.getStateWarn()) {
                    equipEntity.setStateWarn(stateCal);
                    equipMapper.updateById(equipEntity);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
