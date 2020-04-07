package com.seven.gwc.modular.path.service;

import com.seven.gwc.modular.path.entity.FishShipTrackEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * description : 船的轨迹信息服务类
 *
 * @author : QQC
 * @date : 2020-03-26
 */

public interface FishShipTrackService extends IService<FishShipTrackEntity> {

    List<FishShipTrackEntity> selectFishShipTrack(String fishShipTrackName);
    String fishShipTrackPointlist(String fishShipId,Date startTime, Date endTime);
}
