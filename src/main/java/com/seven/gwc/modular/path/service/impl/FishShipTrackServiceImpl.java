package com.seven.gwc.modular.path.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.modular.system.entity.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.path.entity.FishShipTrackEntity;
import com.seven.gwc.modular.path.dao.FishShipTrackMapper;
import com.seven.gwc.modular.path.service.FishShipTrackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * description : 船的轨迹信息服务实现类
 *
 * @author : QQC
 * @date : 2020-03-26
 */
@Service
public class FishShipTrackServiceImpl extends ServiceImpl<FishShipTrackMapper, FishShipTrackEntity> implements FishShipTrackService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishShipTrackMapper fishShipTrackMapper;

    /**
     * 船的轨迹信息查询列表
     */
    @Override
    public List<FishShipTrackEntity> selectFishShipTrack(String fishShipTrackName){
        LambdaQueryWrapper<FishShipTrackEntity> lambdaQuery = Wrappers.<FishShipTrackEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(fishShipTrackName),FishShipTrackEntity::getFishShipId,fishShipTrackName);
        return fishShipTrackMapper.selectList(lambdaQuery);
    }
    /**
     * 船的轨迹信息查询列表
     */
    @Override
    public String fishShipTrackPointlist(String fishShipId,Date startTime, Date endTime){
        int startYear = startTime.getYear();
        int startMonth = startTime.getMonth();
        int startDay = startTime.getDate();
        Date startDate = new Date(startYear,startMonth,startDay);
        int endYear = endTime.getYear();
        int endMonth = endTime.getMonth();
        int endDay = endTime.getDate();
        Date endDate = new Date(endYear,endMonth,endDay);
        LambdaQueryWrapper<FishShipTrackEntity> lambdaQuery = Wrappers.<FishShipTrackEntity>lambdaQuery();
        lambdaQuery.eq(ToolUtil.isNotEmpty(fishShipId), FishShipTrackEntity::getFishShipId,fishShipId)//=鱼船id
                .le(ToolUtil.isNotEmpty(endTime), FishShipTrackEntity::getRecordDate,endDate)//<=结束时间
                .ge(ToolUtil.isNotEmpty(startTime), FishShipTrackEntity::getRecordDate,startDate)//>=开始时间
                .orderByAsc(FishShipTrackEntity::getRecordDate);//升序
        List<FishShipTrackEntity> fishShipTrackpointlists = fishShipTrackMapper.selectList(lambdaQuery);
        if(fishShipTrackpointlists.size()<=0)
            return null;

        JSONArray trackJson = new JSONArray();
        for(int i=0;i<fishShipTrackpointlists.size();i++)
        {
            JSONArray trackJson1 = new JSONArray();
            trackJson1 = JSON.parseArray(fishShipTrackpointlists.get(i).getTrack());
            //按时间排序
            trackJson1.sort(Comparator.comparing(obj -> ((JSONObject) obj).getDate("timeStamp")));
//            System.out.println(trackJson1.size());
            if(i==0)
            {
                for (int j = 0;i<trackJson1.size();){
                    Date t = trackJson1.getJSONObject(j).getDate("timeStamp");
                    if(startTime.compareTo(t)>0)//startTime>t
                    {
                        trackJson1.remove(j);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            //按时间排序
            trackJson1.sort(Comparator.comparing(obj -> ((JSONObject) obj).getDate("timeStamp")).reversed());
//            System.out.println(trackJson1.size());
            if(i==(fishShipTrackpointlists.size()-1))
            {
                for (int j = 0;j<trackJson1.size();){
                    Date t = trackJson1.getJSONObject(j).getDate("timeStamp");
                    if(endTime.compareTo(t)<0)//endTime<t
                    {
                        trackJson1.remove(j);
                    }
                    else
                    {
                        break;
                    }
                }
            }
//            System.out.println(trackJson1.size());
            trackJson.addAll(trackJson1);
        }
//            System.out.println(trackJson.size());
        //按时间排序
        trackJson.sort(Comparator.comparing(obj -> ((JSONObject) obj).getDate("timeStamp")));
        return JSON.toJSONString(trackJson);
    }
}
