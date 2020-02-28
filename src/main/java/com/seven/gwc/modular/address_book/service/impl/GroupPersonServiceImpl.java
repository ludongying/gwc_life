package com.seven.gwc.modular.address_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.address_book.dao.GroupMapper;
import com.seven.gwc.modular.address_book.entity.GroupEntity;
import com.seven.gwc.modular.address_book.vo.FriendListVO;
import com.seven.gwc.modular.address_book.vo.GroupPersonalVO;
import com.seven.gwc.modular.address_book.vo.GroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.address_book.entity.GroupPersonEntity;
import com.seven.gwc.modular.address_book.dao.GroupPersonMapper;
import com.seven.gwc.modular.address_book.service.GroupPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * description : groupPerson服务实现类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Service
public class GroupPersonServiceImpl extends ServiceImpl<GroupPersonMapper, GroupPersonEntity> implements GroupPersonService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupPersonMapper groupPersonMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<GroupVO> getGroupListByUserId(String userId, String groupName) {
        List<GroupVO> groupVOList = new ArrayList<>();
        LambdaQueryWrapper<GroupPersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GroupPersonEntity::getPersonId, userId);
        List<GroupPersonEntity> groupPersonEntityList = groupPersonMapper.selectList(lambdaQuery);
        for (GroupPersonEntity groupPersonEntity : groupPersonEntityList) {
            GroupEntity groupEntity  = groupMapper.selectById(groupPersonEntity.getGroupId());
            if (ToolUtil.isNotEmpty(groupEntity)) {
                if (groupEntity.getName().contains(groupName)) {
                    GroupVO groupVO = new GroupVO();
                    groupVO.setGroupId(groupEntity.getId());
                    groupVO.setGroupName(groupEntity.getName());
                    groupVOList.add(groupVO);
                }
            } else {
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupId(groupEntity.getId());
                groupVO.setGroupName(groupEntity.getName());
                groupVOList.add(groupVO);
            }
        }
        return groupVOList;
    }

    @Override
    public List<GroupPersonalVO> getPersonalListByGroupId(String groupId, String search) {
        List<GroupPersonalVO> list = new ArrayList<>();
        LambdaQueryWrapper<GroupPersonEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(GroupPersonEntity::getGroupId, groupId);
        List<GroupPersonEntity> groupPersonEntityList = groupPersonMapper.selectList(lambdaQuery);
        for (GroupPersonEntity groupPersonEntity : groupPersonEntityList) {
            if (ToolUtil.isNotEmpty(search)) {
                if (groupPersonEntity.getPersonNikeName().contains(search)){
                    GroupPersonalVO groupPersonalVO = new GroupPersonalVO();
                    groupPersonalVO.setPersonalId(groupPersonEntity.getPersonId());
                    groupPersonalVO.setPersonNikeName(groupPersonEntity.getPersonNikeName());
                    list.add(groupPersonalVO);
                }
            } else {
                GroupPersonalVO groupPersonalVO = new GroupPersonalVO();
                groupPersonalVO.setPersonalId(groupPersonEntity.getPersonId());
                groupPersonalVO.setPersonNikeName(groupPersonEntity.getPersonNikeName());
                list.add(groupPersonalVO);
            }
        }
        return list;
    }

    @Override
    public BaseResult deletePersonalByGroupId(String groupId, String personalIds) {
        String[] ids = personalIds.split(",");
        for (String personalId: ids) {
            LambdaQueryWrapper<GroupPersonEntity> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.eq(GroupPersonEntity::getGroupId, groupId)
                    .eq(GroupPersonEntity::getPersonId, personalId);
            GroupPersonEntity groupPersonEntity = groupPersonMapper.selectOne(lambdaQuery);
            groupPersonMapper.deleteById(groupPersonEntity);
        }
        return new BaseResult(200, "操作成功");
    }
}
