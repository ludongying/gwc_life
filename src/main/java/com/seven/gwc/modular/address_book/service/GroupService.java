package com.seven.gwc.modular.address_book.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.address_book.entity.GroupEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * description : group服务类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */

public interface GroupService extends IService<GroupEntity> {
    /**
     * group编辑
     *
     * @param groupId 群组ID
     * @param groupName 群组名
     */
    BaseResult editGroupName(String groupId, String groupName);

}
