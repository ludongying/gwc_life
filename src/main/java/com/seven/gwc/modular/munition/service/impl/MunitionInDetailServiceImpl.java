package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.munition.dao.MunitionInDetailMapper;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.service.MunitionInDetailService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description : 物资入库详情服务实现类
 *
 * @author : LDY
 * @date : 2020-04-09
 */
@Service
public class MunitionInDetailServiceImpl extends ServiceImpl<MunitionInDetailMapper, MunitionInDetailEntity> implements MunitionInDetailService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInDetailMapper munitionInDetailMapper;
    @Autowired
    private MunitionInMapper munitionInMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<MunitionInDetailEntity> selectMunitionInDetail(MunitionInDetailEntity munitionInDetail, String munitionMainId, Integer total, Integer size) {
        List<MunitionInDetailEntity> lists = munitionInDetailMapper.selectMunitionList(munitionInDetail, munitionMainId, total, size);
        for (MunitionInDetailEntity detail : lists) {
            if (ToolUtil.isNotEmpty(detail.getDepotId())) {
                DictEntity dictEntity = dictMapper.selectById(detail.getDepotId());
                if (dictEntity != null) {
                    detail.setDepotName(dictEntity.getName());
                }
            }
        }
        return lists;
    }

    @Override
    public Integer getListSize(MunitionInDetailEntity munitionInDetail, String munitionMainId) {
        List<MunitionInDetailEntity> list = munitionInDetailMapper.getListSize(munitionInDetail, munitionMainId);
        return list.size();
    }

    @Override
    public void addMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user) {
        LambdaQueryWrapper<MunitionInDetailEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInDetailEntity::getMunitionMainId, munitionInDetail.getMunitionMainId()).eq(MunitionInDetailEntity::getMunitionId, munitionInDetail.getMunitionId()).eq(MunitionInDetailEntity::getDepotId, munitionInDetail.getDepotId()).eq(MunitionInDetailEntity::getDeleteFlag, 1);
        MunitionInDetailEntity detail = munitionInDetailMapper.selectOne(lambdaQuery);
        if (detail != null) {//该物资已存在，累加更新
            int num = detail.getTotalNum() + munitionInDetail.getTotalNum();
            detail.setTotalNum(num);
            detail.setUpdateDate(new Date());
            detail.setUpdatePerson(user.getId());
            munitionInDetailMapper.updateById(detail);
        } else {//该物资不存在，直接插入
            munitionInDetail.setId(null);
            munitionInDetail.setSynFlag(false);
            munitionInDetail.setDeleteFlag(true);
            munitionInDetail.setCreateDate(new Date());
            munitionInDetail.setCreatePerson(user.getId());
            munitionInDetailMapper.insert(munitionInDetail);
            //更新入库主表detailIds
            LambdaQueryWrapper<MunitionInEntity> lambdaQueryMunitionIn = Wrappers.lambdaQuery();
            lambdaQueryMunitionIn.eq(MunitionInEntity::getCode, munitionInDetail.getMunitionMainId()).eq(MunitionInEntity::getDeleteFlag, 1);
            MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQueryMunitionIn);
            if (munitionInEntity != null) {
                String details = munitionInEntity.getDetailId();
                if (!ToolUtil.isNotEmpty(details)) {
                    details = munitionInDetail.getId();
                } else {
                    details += FileUtils.file_2_file_sep + munitionInDetail.getId();
                }
                munitionInEntity.setDetailId(details);
                munitionInEntity.setUpdateDate(new Date());
                munitionInEntity.setUpdatePerson(user.getId());
                munitionInMapper.updateById(munitionInEntity);
            }
        }
    }

    @Override
    public void deleteMunitionInDetail(String munitionInDetailId, String munitionInId, ShiroUser user) {
        //更新入库物资表
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInEntity::getCode, munitionInId);
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQuery);
        if (munitionInEntity != null) {
            ArrayList<String> detailIds =
                    Stream.of(munitionInEntity.getDetailId().split(FileUtils.file_2_file_sep))
                            .collect(Collectors.toCollection(ArrayList<String>::new));
            detailIds.removeAll(Collections.singleton(munitionInDetailId));
            String result = detailIds.stream().collect(Collectors.joining(","));
            munitionInEntity.setDetailId(result);
            munitionInEntity.setUpdateDate(new Date());
            munitionInEntity.setUpdatePerson(user.getId());
            munitionInMapper.updateById(munitionInEntity);
            //删除入库物资
            MunitionInDetailEntity detail = munitionInDetailMapper.selectById(munitionInDetailId);
            if (detail != null) {
                detail.setDeleteFlag(false);
                detail.setSynFlag(false);
                detail.setUpdateDate(new Date());
                detail.setUpdatePerson(user.getId());
            }
            munitionInDetailMapper.updateById(detail);
        }
    }

    @Override
    public boolean editMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user) {
        LambdaQueryWrapper<MunitionInDetailEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInDetailEntity::getMunitionId, munitionInDetail.getMunitionId()).eq(MunitionInDetailEntity::getDeleteFlag, 1).ne(MunitionInDetailEntity::getId, munitionInDetail.getId());
        MunitionInDetailEntity detail = munitionInDetailMapper.selectOne(lambdaQuery);
        if (detail != null) {
            return false;
        }
        munitionInDetail.setUpdateDate(new Date());
        munitionInDetail.setUpdatePerson(user.getId());
        munitionInDetailMapper.updateById(munitionInDetail);
        return true;
    }

}
