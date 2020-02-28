package com.seven.gwc.modular.electronic_data.service;

import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : 法律法规/航线安全服务类
 *
 * @author : SHQ
 * @date : 2020-02-25
 */

public interface RegulationSafeService extends IService<RegulationSafeEntity> {

    /**
     * 法律法规/航线安全查询列表
     *
     * @param regulationSafeName 名称
     * @return List<法律法规/航线安全服务对象>
     */
    List<RegulationSafeEntity> selectRegulationSafe(String regulationSafeName,String lawRegularId,String type);

    /**
     * 法律法规/航线安全新建
     *
     * @param regulationSafe 实体对象
     * @param user 当前用户
     */
    void addRegulationSafe(RegulationSafeEntity regulationSafe, ShiroUser user);

    /**
     * 法律法规/航线安全删除
     *
     * @param regulationSafeId 唯一标识
     * @param user 当前用户
     */
    void deleteRegulationSafe(String regulationSafeId, ShiroUser user);

    /**
     * 法律法规/航线安全编辑
     *
     * @param regulationSafe 实体对象
     * @param user 当前用户
     */
    void editRegulationSafe(RegulationSafeEntity regulationSafe, ShiroUser user);

    /**
     * 根据文档名称验重
     * @param name
     * @param type
     */
    boolean selectOnlyByName(String name,String type);

}
