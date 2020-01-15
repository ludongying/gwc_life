package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.HolidayMapper;
import com.seven.gwc.modular.system.entity.HolidayEntity;
import com.seven.gwc.modular.system.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : 假日服务实现类
 *
 * @author : GD
 * @date : 2019-10-17
 */
@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, HolidayEntity> implements HolidayService {
    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public List<HolidayEntity> selectHoliday(String holidayName) {
        LambdaQueryWrapper<HolidayEntity> lambdaQuery = Wrappers.<HolidayEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(holidayName), HolidayEntity::getName, holidayName)
                .orderByAsc(HolidayEntity::getSort);
        return holidayMapper.selectList(lambdaQuery);
    }

}
