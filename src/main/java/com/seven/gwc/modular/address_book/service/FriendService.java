package com.seven.gwc.modular.address_book.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.address_book.entity.FriendEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.address_book.vo.FriendListVO;
import com.seven.gwc.modular.address_book.vo.InitialsVO;

import java.util.List;

/**
 * description : friend服务类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */

public interface FriendService extends IService<FriendEntity> {

    /**
     * group新建
     *
     * @param userId 当前用户Id
     * @param personalId 被添加人ID
     */
    BaseResult addFriend(String userId, String personalId);

    /**
     * group删除
     *
     * @param userId 当前用户Id
     * @param personalId 被删除人ID
     */
    BaseResult deleteFriend(String userId, String personalId);

    /**
     * 获取好友列表
     * @param personalId 用户ID
     * @return
     */
    List<InitialsVO> getFriendListByPersonalId(String personalId);

    /**
     * 好友列表查询
     * @param personalId 用户ID
     * @param search 查询条件
     * @return
     */
    List<FriendListVO> searchFriendList(String personalId, String search);

    List<FriendListVO> addSearchFriendList(String personalId, String search);
}
