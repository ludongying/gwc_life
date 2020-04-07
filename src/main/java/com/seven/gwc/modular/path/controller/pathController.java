//package com.seven.gwc.modular.path.controller;
//
//import com.seven.gwc.core.base.BaseController;
//import com.seven.gwc.core.state.ErrorEnum;
//import com.seven.gwc.modular.path.entity.FishAreaEntity;
//import com.seven.gwc.modular.path.entity.FishForbiddenAreaEntity;
//import com.seven.gwc.modular.path.service.FishAreaService;
//import com.seven.gwc.modular.path.service.FishForbiddenAreaService;
//import com.seven.gwc.modular.path.service.FishShipTrackService;
//import net.sf.ehcache.search.impl.BaseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * description : 轨迹控制器
// *
// * @author : QQC
// * @date : 2020-01-06
// */
//@Controller
//@RequestMapping("path")
//public class pathController extends BaseController{
//
//    @Autowired
//    private FishForbiddenAreaService fishForbiddenAreaService;
//    @Autowired
//    private FishAreaService fishAreaService;
//    @Autowired
//    private FishShipTrackService fishShipTrackService;
//
//    @RequestMapping("/fishForbiddenArea")
//    @ResponseBody
//    public BaseResult fishForbiddenAreapoint(String AreaId){
//
//        List<FishForbiddenAreaEntity> fishForbiddenAreapointlists = fishForbiddenAreaService.fishForbiddenAreaPointlist(AreaId);
//        /*将list集合装换成json对象*/
////        String data = JSONArray.toJSONString(fishForbiddenAreapointlists);
//        BaseResult baseResult=new BaseResult();
//        if (fishForbiddenAreapointlists.size() > 0) {
//            baseResult.setSuccess(true);
//            baseResult.setContent(fishForbiddenAreapointlists);
//
//        } else {
//            return new BaseResult().failure(ErrorEnum.NO_FISH_FORBIDDEN_AREA_DATA);
//        }
//
//        return baseResult;
//    }
//    @RequestMapping("/fishArea")
//    @ResponseBody
//    public BaseResult fishAreapoint(){
//        List<FishAreaEntity> fishAreapointlists = fishAreaService.fishAreaPointlist();
//        /*将list集合装换成json对象*/
////        String data = JSONArray.toJSONString(fishAreapointlists);
//        BaseResult baseResult=new BaseResult();
//        if (fishAreapointlists.size() > 0) {
//            baseResult.setSuccess(true);
//            baseResult.setContent(fishAreapointlists);
//
//        } else {
//            return new BaseResult().failure(ErrorEnum.NO_FISH_AREA_DATA);
//        }
//
//        return baseResult;
//    }
//
//    @RequestMapping("/fishShipTrack")
//    @ResponseBody
//    public BaseResult fishShipTrackpoint(){
//
//        Date startTime1=new Date(2019-1900,8,23,13,49,0),endTime1=new Date(2019-1900,8,26,13,51,0);
//        String fishShipId = "1";
//        String fishShipTrackpointlists = fishShipTrackService.fishShipTrackPointlist(fishShipId,startTime1,endTime1);
////        System.out.println(fishShipTrackpointlists.size());
//        BaseResult baseResult=new BaseResult();
//        if (fishShipTrackpointlists!=null) {
//            baseResult.setSuccess(true);
//            baseResult.setContent(fishShipTrackpointlists);
//        } else {
//            return new BaseResult().failure(ErrorEnum.NO_FISH_AREA_DATA);
//        }
//        return baseResult;
//    }
////    @RequestMapping("/setfishShipTrackdata")
////    @ResponseBody
////    public BaseResult setfishShipTrackpoint(String startTime){
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        Date startTime1;
////        try {
////            startTime1 = simpleDateFormat.parse(startTime);
////        } catch (ParseException e) {
////            return new BaseResult().failure(ErrorEnum.ERROR_DATE_TYPE);
////        }
////        String mydate = startTime+" 23:59:59";
////
////        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date mydate1;
////        try {
////            mydate1 = simpleDateFormat1.parse(mydate);
////        } catch (ParseException e) {
////            return new BaseResult().failure(ErrorEnum.ERROR_DATE_TYPE);
////        }
////        //        //向后推一天
//////        Calendar c = Calendar.getInstance();
//////        c.setTime(startTime1);
//////        c.add(Calendar.DAY_OF_MONTH, 1);
//////        Date sDate = c.getTime();
////        FishShipTrackEntity FishShipTrackEntity = new FishShipTrackEntity();
////        FishShipTrackEntity.setId("3");
////        FishShipTrackEntity.setFishShipId("1");
////        FishShipTrackEntity.setRecordDate(startTime1);
////
////        List<PointlistEntity> pointlists = pointlistService.PathPointlist(mydate1);
////
////        JSONArray trackJson = new JSONArray();
////
////        for(int i=0;i<pointlists.size();i++)
////        {
////            JSONObject object = new JSONObject();
////            //lon
////            object.put("lon",pointlists.get(i).getLon());
////            //lat
////            object.put("lat",pointlists.get(i).getLat());
////            //speed
////            object.put("speed",pointlists.get(i).getSpeed());
////            //heading
////            object.put("heading",pointlists.get(i).getCourse());
////            //timeStamp
////            object.put("timeStamp",pointlists.get(i).getCreateTime());
////            trackJson.add(object);
//////            System.out.println(object.getDate("timeStamp"));
////        }
//////        System.out.println(trackJson);
////        FishShipTrackEntity.setTrack(JSON.toJSONString(trackJson));
////        fishShipTrackService.save(FishShipTrackEntity);
////        return SUCCESS;
////    }
//}
//
//
