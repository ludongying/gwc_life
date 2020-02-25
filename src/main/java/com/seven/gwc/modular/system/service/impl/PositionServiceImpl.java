package com.seven.gwc.modular.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.PositionDeptMapper;
import com.seven.gwc.modular.system.dao.PositionMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.PositionDeptEntity;
import com.seven.gwc.modular.system.entity.PositionEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description : 岗位服务实现类
 *
 * @author : GD
 * @date : 2019-09-20
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionEntity> implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private PositionDeptMapper positionDeptMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<PositionEntity> selectPosition(String positionName) {
        LambdaQueryWrapper<PositionEntity> lambdaQuery = Wrappers.<PositionEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(positionName), PositionEntity::getName, positionName)
                .orderByAsc(PositionEntity::getSort);
        return this.positionMapper.selectList(lambdaQuery);
    }

    @Override
    public int setStatus(String positionId, String state) {
        LambdaUpdateWrapper<PositionEntity> lambdaUpdate = Wrappers.<PositionEntity>lambdaUpdate();
        lambdaUpdate.set(PositionEntity::getState, state).eq(PositionEntity::getId, positionId);
        return this.positionMapper.update(null, lambdaUpdate);
    }

    @Override
    public JSONArray listPositions(String ids) {
        LambdaQueryWrapper<PositionEntity> lambdaQueryDept = Wrappers.<PositionEntity>lambdaQuery();
        lambdaQueryDept.eq(PositionEntity::getState, TypeStatesEnum.OK.getCode()).orderByAsc(PositionEntity::getSort);
        List<PositionEntity> positionEntities = positionMapper.selectList(lambdaQueryDept);

        JSONArray jsonArray = new JSONArray();
        for (PositionEntity positionEntity : positionEntities) {
            JSONObject jsonObject = new JSONObject();
            if (!SysConsts.STR_NULL.equals(ids) && ids != null && ids.length() > 0) {
                for (String id : ids.split(SysConsts.STR_COMMA)) {
                    if (positionEntity.getId().equals(id)){
                        jsonObject.put("selected", "selected");
                    }
                }
            }

            jsonObject.put("value", positionEntity.getId());
            jsonObject.put("name", positionEntity.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public boolean add(PositionEntity position) {
        LambdaQueryWrapper<PositionEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PositionEntity::getName, position.getName());
        PositionEntity positionEntity = positionMapper.selectOne(lambdaQuery);
        if (positionEntity != null) {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        position.setCreateId(user.getId());
        position.setCreateUser(user.getName());
        position.setCreateTime(new Date());
        positionMapper.insert(position);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(PositionEntity position, String menuIds) {
        LambdaQueryWrapper<PositionEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(PositionEntity::getName, position.getName())
                .ne(PositionEntity::getId, position.getId());
        PositionEntity positionEntity = positionMapper.selectOne(lambdaQuery);
        if (positionEntity != null) {
            return false;
        }

        //更改岗位
        positionMapper.updateById(position);
        if (position.getDataScope() != null && SysConsts.STR_TWO.equals(position.getDataScope())) {
            LambdaQueryWrapper<PositionDeptEntity> lambdaQuery1 = Wrappers.lambdaQuery();
            lambdaQuery1.eq(PositionDeptEntity::getPositionId, position.getId());
            //删除岗位范围
            positionDeptMapper.delete(lambdaQuery1);

            for (String menuId : menuIds.split(SysConsts.STR_COMMA)) {
                PositionDeptEntity positionDeptEntity = new PositionDeptEntity();
                positionDeptEntity.setPositionId(position.getId());
                positionDeptEntity.setDeptId(menuId);
                //添加岗位范围
                positionDeptMapper.insert(positionDeptEntity);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        LambdaQueryWrapper<UserEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.isNotNull(UserEntity::getPositionId);
        List<UserEntity> userEntityList = userMapper.selectList(lambdaQuery);
        List<String> list = new ArrayList<>();
        if (userEntityList.size() > 0) {
            for (UserEntity userEntity : userEntityList) {
                String[] postIds = userEntity.getPositionId().split(",");
                if (postIds != null) {
                    for (String postId : postIds) {
                        list.add(postId);
                    }
                }
            }
        }
        if (list.contains(id.toString())) {
            return false;
        }
        positionMapper.deleteById(id);
        return true;
    }

}
