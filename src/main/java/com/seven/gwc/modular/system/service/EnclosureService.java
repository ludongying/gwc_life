package com.seven.gwc.modular.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.system.entity.EnclosureEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * description : 附件服务类
 *
 * @author : GD
 * @date : 2019-09-29
 */

public interface EnclosureService extends IService<EnclosureEntity> {

    /**
     * 附件查询列表
     */
    List<EnclosureEntity> selectEnclosure(String enclosureName);

    /**
     * 添加附件
     *
     * @param user       操作人
     * @param fileList   附件列表
     * @param forSurface 外键表
     * @param forId      外键表Id
     * @return
     */
    int addEnclosureEntity(ShiroUser user, String fileList, String forSurface, Long forId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
