package com.seven.gwc.modular.address_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.base.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.address_book.entity.GroupEntity;
import com.seven.gwc.modular.address_book.dao.GroupMapper;
import com.seven.gwc.modular.address_book.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * description : group服务实现类
 *
 * @author : SHQ
 * @date : 2020-02-27
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupEntity> implements GroupService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupMapper groupMapper;


    @Override
    public BaseResult editGroupName(String groupId, String groupName) {
        GroupEntity groupEntity = groupMapper.selectById(groupId);
        groupEntity.setName(groupName);
        groupMapper.updateById(groupEntity);
        return new BaseResult(200, "操作成功");
    }
}
