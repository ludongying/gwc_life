package com.seven.gwc.modular.work.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.work.dao.ShipWorkLogMapper;
import com.seven.gwc.modular.work.entity.ShipWorkLogEntity;
import com.seven.gwc.modular.work.service.ShipWorkLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description : 工作日志记录服务实现类
 *
 * @author : 李晓晖
 * @date : 2020-03-06
 */
@Service
public class ShipWorkLogServiceImpl extends ServiceImpl<ShipWorkLogMapper, ShipWorkLogEntity> implements ShipWorkLogService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipWorkLogMapper shipWorkLogMapper;

    @Override
    public List<ShipWorkLogEntity> selectShipWorkLog(String shipWorkLogName) {
        LambdaQueryWrapper<ShipWorkLogEntity> lambdaQuery = Wrappers.<ShipWorkLogEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(shipWorkLogName), ShipWorkLogEntity::getId, shipWorkLogName);
        return shipWorkLogMapper.selectList(lambdaQuery);
    }

    @Override
    public void addShipWorkLog(ShipWorkLogEntity shipWorkLog, ShiroUser user) {
        shipWorkLogMapper.insert(shipWorkLog);
    }

    @Override
    public void deleteShipWorkLog(String shipWorkLogId, ShiroUser user) {
        shipWorkLogMapper.deleteById(shipWorkLogId);
    }

    @Override
    public void editShipWorkLog(ShipWorkLogEntity shipWorkLog, ShiroUser user) {
        shipWorkLogMapper.updateById(shipWorkLog);
    }

    @Override
    public JSONArray listLogs(ShipWorkLogEntity shipWorkLog, ShiroUser user) {
        shipWorkLog.setCreatePerson(user.getId());
        List<ShipWorkLogEntity> shipWorkLogEntities = shipWorkLogMapper.WorkLogList(shipWorkLog);


        JSONArray jsonArray = new JSONArray();
        for (ShipWorkLogEntity shipWorkLogEntity : shipWorkLogEntities) {
            JSONObject jsonObject = new JSONObject();
            var ids = shipWorkLogEntity.getId();
            if (!SysConsts.STR_NULL.equals(ids) && ids != null && ids.length() > 0) {
                for (String id : ids.split(SysConsts.STR_COMMA)) {
                    if (shipWorkLogEntity.getId().equals(id)) {
                        jsonObject.put("selected", "selected");
                    }
                }
            }

            jsonObject.put("id", shipWorkLogEntity.getId());

            jsonObject.put("start", DateTimeUtil.parse2String(shipWorkLogEntity.getRecordDate(), "yyyy-MM-dd HH:mm:ss"));
//            jsonObject.put("end", DateTimeUtil.parse2String(shipWorkLogEntity.getRecordEndDate(), "yyyy-MM-dd HH:mm:ss"));
            var str ="至"+DateTimeUtil.parse2String(shipWorkLogEntity.getRecordEndDate(), "yyyy-MM-dd HH:mm:ss")+"\t"+shipWorkLogEntity.getContent();
            jsonObject.put("title", str);
//            jsonObject.put("title", shipWorkLogEntity.getContent());
            jsonObject.put("type", shipWorkLogEntity.getRecordType());
            if (shipWorkLogEntity.getRecordType() == 0) {
                jsonObject.put("color", "#3987AD");
                jsonObject.put("textColor", "white");
            }
            else if(shipWorkLogEntity.getRecordType() == 1){
                jsonObject.put("color", "#D25B47");
                jsonObject.put("textColor", "white");
            }
            else if(shipWorkLogEntity.getRecordType() == 2){
                jsonObject.put("color", "#FE9400");
                jsonObject.put("textColor", "white");
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
