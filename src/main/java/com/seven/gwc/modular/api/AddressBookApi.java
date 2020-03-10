package com.seven.gwc.modular.api;

import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.address_book.service.FriendService;
import com.seven.gwc.modular.address_book.service.GroupPersonService;
import com.seven.gwc.modular.address_book.vo.FriendListVO;
import com.seven.gwc.modular.address_book.vo.GroupPersonalVO;
import com.seven.gwc.modular.address_book.vo.GroupVO;
import com.seven.gwc.modular.address_book.vo.InitialsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通讯录控制器
 *
 * @author : SHQ
 * @date : 2020-02-27
 */

@RestController
@Api(tags = "通讯录接口")
@RequestMapping("gwcApi/addressBook")
public class AddressBookApi extends BaseController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private GroupPersonService groupPersonService;

    @GetMapping(value = "/getFriendListByPersonalId")
    @ApiOperation(value = "获取好友列表")
    public BaseResult<List<InitialsVO>> getFriendListByPersonalId(HttpServletRequest request){
        String userId = request.getAttribute("userId").toString();
        List<InitialsVO> listVO = friendService.getFriendListByPersonalId(userId);
        return new BaseResult().content(listVO);
    }

    @GetMapping(value = "/searchFriendList")
    @ApiOperation(value = "好友列表查询")
    public BaseResult<List<FriendListVO>> searchFriendList(HttpServletRequest request, String search) {
        String userId = request.getAttribute("userId").toString();
        List<FriendListVO> listVO = friendService.searchFriendList(userId, search);
        return new BaseResult().content(listVO);
    }

    @GetMapping(value = "/addSearchFriendList")
    @ApiOperation(value = "添加好友查询")
    public BaseResult<List<FriendListVO>> addSearchFriendList(HttpServletRequest request, String search) {
        String userId = request.getAttribute("userId").toString();
        List<FriendListVO> listVO = friendService.addSearchFriendList(userId, search);
        return new BaseResult().content(listVO);
    }

    @GetMapping(value = "/getUserInfoByPersonalId")
    @ApiOperation(value = "根据用户ID获取用户信息")
    public BaseResult<FriendListVO> getUserInfoByPersonalId(String personalId) {
        FriendListVO friendListVO= friendService.getUserInfoByPersonalId(personalId);
        return new BaseResult().content(friendListVO);
    }

    @PostMapping(value = "/addFriend")
    @ApiOperation(value = "添加好友")
    public BaseResult addFriend(HttpServletRequest request,
                                @ApiParam(required = true, name = "personalId", value = "被添加人ID")String personalId){
        String userId = request.getAttribute("userId").toString();
        return friendService.addFriend(userId, personalId);
    }

    @PostMapping(value = "/deleteFriend")
    @ApiOperation(value = "删除好友")
    public BaseResult deleteFriend(HttpServletRequest request,
                                   @ApiParam(required = true, name = "personalId", value = "被删除人ID")String personalId){
        String userId = request.getAttribute("userId").toString();
        return friendService.deleteFriend(userId, personalId);
    }

    @GetMapping(value = "/getGroupListByUserId")
    @ApiOperation(value = "获取群组列表")
    public BaseResult<List<GroupVO>> getGroupListByUserId(HttpServletRequest request,
                                              @ApiParam(name = "groupName", value = "查询条件")String groupName) {
        String userId = request.getAttribute("userId").toString();
        List<GroupVO> listVO =  groupPersonService.getGroupListByUserId(userId, groupName);
        return new BaseResult().content(listVO);
    }

    @GetMapping(value = "/getPersonalListByGroupId")
    @ApiOperation(value = "根据群组ID获取群组成员信息")
    public BaseResult<List<GroupPersonalVO>> getPersonalListByGroupId(@ApiParam(required = true, name = "groupId", value = "群组ID")String groupId,
                                                          @ApiParam(name = "search", value = "查询条件")String search) {
        List<GroupPersonalVO> list = groupPersonService.getPersonalListByGroupId(groupId, search);
        return new BaseResult().content(list);
    }

    @PostMapping(value = "/deletePersonalByGroupId")
    @ApiOperation(value = "根据群组ID删除群组成员信息")
    public BaseResult deletePersonalByGroupId(String groupId, String personalIds) {
        return groupPersonService.deletePersonalByGroupId(groupId, personalIds);
    }

}
