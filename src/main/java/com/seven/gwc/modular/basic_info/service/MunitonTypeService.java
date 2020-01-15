package com.seven.gwc.modular.basic_info.service;

import com.seven.gwc.modular.basic_info.entity.MunitonTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 物料类型服务类
 *
 * @author : LDY
 * @date : 2020-01-02
 */

public interface MunitonTypeService extends IService<MunitonTypeEntity> {

    List<MunitonTypeEntity> selectMunitonType(String munitonTypeName);

    /**
     * 物料类型显示状态
     * @param id 物料id
     * @param  state 状态，ENABLE显示，0不显示
     * */
    int setStatus(Long id,String state);

    /**
     * 新增物料类型
     * @param munitionType 物料类型
     * @return
     */
    boolean add(MunitonTypeEntity munitionType);

    /**
     * 修改物料类型
     * @param
     * @return
     */
    boolean edit(MunitonTypeEntity munitonType);
}
