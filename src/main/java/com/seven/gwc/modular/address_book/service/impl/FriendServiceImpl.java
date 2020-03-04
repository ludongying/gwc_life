package com.seven.gwc.modular.address_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.util.ChineseCharacterUtil;
import com.seven.gwc.modular.address_book.vo.FriendListVO;
import com.seven.gwc.modular.address_book.vo.InitialsVO;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.address_book.entity.FriendEntity;
import com.seven.gwc.modular.address_book.dao.FriendMapper;
import com.seven.gwc.modular.address_book.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * description : group服务实现类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, FriendEntity> implements FriendService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private UserMapper userMapper;
    @Value("${server.ip}")
    private String ip;

    @Override
    public List<InitialsVO> getFriendListByPersonalId(String personalId, String search) {
        System.out.println(System.currentTimeMillis());
        List<InitialsVO> initialsVOList = new ArrayList<>();
        List<FriendListVO> friendListVOList = new ArrayList<>();
        LambdaQueryWrapper<FriendEntity> lambdaQuery1 = Wrappers.lambdaQuery();
        lambdaQuery1.eq(FriendEntity::getPerson1Id, personalId);
        List<FriendEntity> friendEntityList1 = friendMapper.selectList(lambdaQuery1);
        for (FriendEntity friendEntity : friendEntityList1) {
            UserEntity userEntity = userMapper.selectById(friendEntity.getPerson2Id());
            if (ToolUtil.isNotEmpty(userEntity)) {
                if (ToolUtil.isNotEmpty(search)) {
                    if (userEntity.getName().contains(search) || userEntity.getPhone().contains(search)) {
                        FriendListVO friendListVO = new FriendListVO();
                        friendListVO.setPersonalId(userEntity.getId());
                        friendListVO.setUserName(userEntity.getName());
                        friendListVO.setPhone(userEntity.getPhone());
                        friendListVO.setAvatar(ip + userEntity.getAvatar());
                        String initials = ChineseCharacterUtil.convertHanzi2Pinyin(userEntity.getName(), true);
                        friendListVO.setInitial(initials.substring(0, 1).toUpperCase());
                        friendListVOList.add(friendListVO);
                    }
                } else {
                    FriendListVO friendListVO = new FriendListVO();
                    friendListVO.setPersonalId(userEntity.getId());
                    friendListVO.setUserName(userEntity.getName());
                    friendListVO.setPhone(userEntity.getPhone());
                    friendListVO.setAvatar(ip + userEntity.getAvatar());
                    String initials = ChineseCharacterUtil.convertHanzi2Pinyin(userEntity.getName(), true);
                    friendListVO.setInitial(initials.substring(0, 1).toUpperCase());
                    friendListVOList.add(friendListVO);
                }
            }
        }

        LambdaQueryWrapper<FriendEntity> lambdaQuery2 = Wrappers.lambdaQuery();
        lambdaQuery2.eq(FriendEntity::getPerson2Id, personalId);
        List<FriendEntity> friendEntityList2 = friendMapper.selectList(lambdaQuery2);
        for (FriendEntity friendEntity : friendEntityList2) {
            UserEntity userEntity = userMapper.selectById(friendEntity.getPerson1Id());
            if (ToolUtil.isNotEmpty(userEntity)) {
                if (ToolUtil.isNotEmpty(search)) {
                    if (userEntity.getName().contains(search) || userEntity.getPhone().contains(search)) {
                        FriendListVO friendListVO = new FriendListVO();
                        friendListVO.setPersonalId(userEntity.getId());
                        friendListVO.setUserName(userEntity.getName());
                        friendListVO.setPhone(userEntity.getPhone());
                        friendListVO.setAvatar(ip + userEntity.getAvatar());
                        String initials = ChineseCharacterUtil.convertHanzi2Pinyin(userEntity.getName(), true);
                        friendListVO.setInitial(initials.substring(0, 1).toUpperCase());
                        friendListVOList.add(friendListVO);
                    }
                } else {
                    FriendListVO friendListVO = new FriendListVO();
                    friendListVO.setPersonalId(userEntity.getId());
                    friendListVO.setUserName(userEntity.getName());
                    friendListVO.setPhone(userEntity.getPhone());
                    friendListVO.setAvatar(ip + userEntity.getAvatar());
                    String initials = ChineseCharacterUtil.convertHanzi2Pinyin(userEntity.getName(), true);
                    friendListVO.setInitial(initials.substring(0, 1).toUpperCase());
                    friendListVOList.add(friendListVO);
                }
            }
        }
        System.out.println(System.currentTimeMillis());
        for(int i = 1; i<=26; i++){
            InitialsVO  initialsVO = new InitialsVO();
            initialsVO.setInitial(String.valueOf(Character.toUpperCase((char)(96+i))));
            List<FriendListVO> friendListVOS = new ArrayList<>();
            for (FriendListVO friendListVO : friendListVOList) {
                if (String.valueOf(Character.toUpperCase((char)(96+i))).equals(friendListVO.getInitial())){
                    FriendListVO friendList = new FriendListVO();
                    friendList.setPersonalId(friendListVO.getPersonalId());
                    friendList.setUserName(friendListVO.getUserName());
                    friendList.setPhone(friendListVO.getPhone());
                    friendList.setAvatar(ip + friendListVO.getAvatar());
                    String initials = ChineseCharacterUtil.convertHanzi2Pinyin(friendListVO.getUserName(), true);
                    friendList.setInitial(initials.substring(0, 1).toUpperCase());
                    friendListVOS.add(friendListVO);
                }
            }
            initialsVO.setFriendListVOList(friendListVOS);
            initialsVOList.add(initialsVO);
            //System.out.println(  Character.toUpperCase( (char)(96+i))  );//大写
        }
        System.out.println(System.currentTimeMillis());
        return initialsVOList;
    }

    @Override
    public BaseResult addFriend(String userId, String personalId) {
        if (userId.equals(personalId)) {
            return new BaseResult(500, "不能添加自己为好友");
        }
        LambdaQueryWrapper<FriendEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(FriendEntity::getPerson1Id, userId)
                .eq(FriendEntity::getPerson2Id, personalId);
        FriendEntity friendEntity = friendMapper.selectOne(lambdaQuery);
        if (ToolUtil.isNotEmpty(friendEntity)) {
            return new BaseResult(500, "已是好友，不可重复添加");
        }

        LambdaQueryWrapper<FriendEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FriendEntity::getPerson1Id, personalId)
                .eq(FriendEntity::getPerson2Id, userId);
        FriendEntity friend = friendMapper.selectOne(lambdaQueryWrapper);
        if (ToolUtil.isNotEmpty(friend)) {
            return new BaseResult(500, "已是好友，不可重复添加");
        }

        FriendEntity entity = new FriendEntity();
        entity.setPerson1Id(userId);
        entity.setPerson2Id(personalId);
        friendMapper.insert(entity);

        return new BaseResult(200, "操作成功");
    }

    @Override
    public BaseResult deleteFriend(String userId, String personalId) {
        LambdaQueryWrapper<FriendEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(FriendEntity::getPerson1Id, userId)
                .eq(FriendEntity::getPerson2Id, personalId);
        FriendEntity friendEntity = friendMapper.selectOne(lambdaQuery);
        if (ToolUtil.isNotEmpty(friendEntity)) {
            friendMapper.deleteById(friendEntity);
        }

        LambdaQueryWrapper<FriendEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FriendEntity::getPerson1Id, personalId)
                .eq(FriendEntity::getPerson2Id, userId);
        FriendEntity friend = friendMapper.selectOne(lambdaQueryWrapper);
        if (ToolUtil.isNotEmpty(friend)) {
            friendMapper.deleteById(friendEntity);
        }
        return new BaseResult(200, "操作成功");
    }
}
