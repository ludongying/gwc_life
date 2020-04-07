package com.seven.gwc.modular.path.service;

import com.seven.gwc.modular.path.entity.FishAreaEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 海图渔区服务类
 *
 * @author : QQC
 * @date : 2020-03-23
 */

public interface FishAreaService extends IService<FishAreaEntity> {

    List<FishAreaEntity> selectFishArea(String fishAreaName);
    List<FishAreaEntity> fishAreaPointlist();
}
