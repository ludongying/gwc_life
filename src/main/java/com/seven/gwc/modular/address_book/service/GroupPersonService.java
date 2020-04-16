package com.seven.gwc.modular.address_book.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.address_book.entity.GroupPersonEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.address_book.vo.FriendListVO;
import com.seven.gwc.modular.address_book.vo.GroupPersonalVO;
import com.seven.gwc.modular.address_book.vo.GroupVO;

import java.io.IOException;
import java.util.List;

/**
 * description : groupPerson服务类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */

public interface GroupPersonService extends IService<GroupPersonEntity> {

    /**
     * 根据用户ID获取群组列表
     *
     * @param userId 登录人ID
     * @return List<groupPerson服务对象>
     */
    List<GroupVO> getGroupListByUserId(String userId, String groupName);

    /**
     * 根据群组ID获取群组成员
     * @param groupId 群组ID
     * @param search 查询条件
     * @return
     */
    List<GroupPersonalVO> getPersonalListByGroupId(String groupId, String search);

    /**
     * 删除群组成员
     * @param groupId  群组ID
     * @param ids 被删除人IDS
     * @return
     */
    BaseResult deletePersonalByGroupId(String groupId, String ids);

    /**
     * 查询对讲组列表信息
     */
    String getPttList(String keyWord) throws IOException;

}
