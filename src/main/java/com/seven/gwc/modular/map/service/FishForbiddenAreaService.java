package com.seven.gwc.modular.map.service;

import com.seven.gwc.modular.map.entity.FishForbiddenAreaEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 禁渔区服务类
 *
 * @author : QQC
 * @date : 2020-03-09
 */

public interface FishForbiddenAreaService extends IService<FishForbiddenAreaEntity> {

    List<FishForbiddenAreaEntity> selectFishForbiddenArea(String fishForbiddenAreaName);
    List<FishForbiddenAreaEntity> fishForbiddenAreaPointlist(String AreaId);
}
