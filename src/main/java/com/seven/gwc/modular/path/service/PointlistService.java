package com.seven.gwc.modular.path.service;

import com.seven.gwc.modular.path.entity.PointlistEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : path服务类
 *
 * @author : QQC
 * @date : 2020-01-02
 */

public interface PointlistService extends IService<PointlistEntity> {

    List<PointlistEntity> selectPointlist(String pointlistName);
}
