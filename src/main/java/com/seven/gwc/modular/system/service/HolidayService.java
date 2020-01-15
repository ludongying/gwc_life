package com.seven.gwc.modular.system.service;

import com.seven.gwc.modular.system.entity.HolidayEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description: 假日服务类
 *
 * @author : GD
 * @date : 2019-10-17
 */

public interface HolidayService extends IService<HolidayEntity> {

    /**
     * 假日查询列表
     */
    List<HolidayEntity> selectHoliday(String holidayName);
}
