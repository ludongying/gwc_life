package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.munition.dao.MunitionOutDetailMapper;
import com.seven.gwc.modular.munition.dao.MunitionOutMapper;
import com.seven.gwc.modular.munition.entity.MunitionOutDetailEntity;
import com.seven.gwc.modular.munition.entity.MunitionOutEntity;
import com.seven.gwc.modular.munition.service.MunitionOutDetailService;
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
 * description : 物资出库详情服务实现类
 *
 * @author : LDY
 * @date : 2020-04-09
 */
@Service
public class MunitionOutDetailServiceImpl extends ServiceImpl<MunitionOutDetailMapper, MunitionOutDetailEntity> implements MunitionOutDetailService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionOutDetailMapper munitionOutDetailMapper;
    @Autowired
    private MunitionOutMapper munitionOutMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<MunitionOutDetailEntity> selectMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, String munitionMainId, Integer total, Integer size) {
        List<MunitionOutDetailEntity> lists = munitionOutDetailMapper.selectMunitionList(munitionOutDetail, munitionMainId, total, size);
        for (MunitionOutDetailEntity detail : lists) {
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
    public Integer getListSize(MunitionOutDetailEntity munitionOutDetail, String munitionMainId) {
        List<MunitionOutDetailEntity> list = munitionOutDetailMapper.getListSize(munitionOutDetail, munitionMainId);
        return list.size();
    }


    @Override
    public void addMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, ShiroUser user) {
        LambdaQueryWrapper<MunitionOutDetailEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionOutDetailEntity::getMunitionMainId, munitionOutDetail.getMunitionMainId()).eq(MunitionOutDetailEntity::getMunitionId, munitionOutDetail.getMunitionId()).eq(MunitionOutDetailEntity::getDepotId, munitionOutDetail.getDepotId()).eq(MunitionOutDetailEntity::getDeleteFlag, 1);
        MunitionOutDetailEntity detail = munitionOutDetailMapper.selectOne(lambdaQuery);
        if (detail != null) {//该物资已存在，累加更新
            int num = detail.getTotalNum() + munitionOutDetail.getTotalNum();
            detail.setTotalNum(num);
            detail.setUpdateDate(new Date());
            detail.setUpdatePerson(user.getId());
            munitionOutDetailMapper.updateById(detail);
        } else {//该物资不存在，直接插出
            munitionOutDetail.setId(null);
            munitionOutDetail.setSynFlag(false);
            munitionOutDetail.setDeleteFlag(true);
            munitionOutDetail.setCreateDate(new Date());
            munitionOutDetail.setCreatePerson(user.getId());
            munitionOutDetailMapper.insert(munitionOutDetail);
            //更新出库主表detailIds
            LambdaQueryWrapper<MunitionOutEntity> lambdaQueryMunitionOut = Wrappers.lambdaQuery();
            lambdaQueryMunitionOut.eq(MunitionOutEntity::getCode, munitionOutDetail.getMunitionMainId()).eq(MunitionOutEntity::getDeleteFlag, 1);
            MunitionOutEntity munitionOutEntity = munitionOutMapper.selectOne(lambdaQueryMunitionOut);
            if (munitionOutEntity != null) {
                String details = munitionOutEntity.getDetailId();
                if (!ToolUtil.isNotEmpty(details)) {
                    details = munitionOutDetail.getId();
                } else {
                    details += FileUtils.file_2_file_sep + munitionOutDetail.getId();
                }
                munitionOutEntity.setDetailId(details);
                munitionOutEntity.setUpdateDate(new Date());
                munitionOutEntity.setUpdatePerson(user.getId());
                munitionOutMapper.updateById(munitionOutEntity);
            }
        }
    }

    @Override
    public void deleteMunitionOutDetail(String munitionOutDetailId, String munitionOutId, ShiroUser user) {
        //更新出库物资表
        LambdaQueryWrapper<MunitionOutEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionOutEntity::getCode, munitionOutId);
        MunitionOutEntity munitionOutEntity = munitionOutMapper.selectOne(lambdaQuery);
        if (munitionOutEntity != null) {
            ArrayList<String> detailIds =
                    Stream.of(munitionOutEntity.getDetailId().split(FileUtils.file_2_file_sep))
                            .collect(Collectors.toCollection(ArrayList<String>::new));
            detailIds.removeAll(Collections.singleton(munitionOutDetailId));
            String result = detailIds.stream().collect(Collectors.joining(","));
            munitionOutEntity.setDetailId(result);
            munitionOutEntity.setUpdateDate(new Date());
            munitionOutEntity.setUpdatePerson(user.getId());
            munitionOutMapper.updateById(munitionOutEntity);
            //删除出库物资
            MunitionOutDetailEntity detail = munitionOutDetailMapper.selectById(munitionOutDetailId);
            if (detail != null) {
                detail.setDeleteFlag(false);
                detail.setSynFlag(false);
                detail.setUpdateDate(new Date());
                detail.setUpdatePerson(user.getId());
            }
            munitionOutDetailMapper.updateById(detail);
        }
    }

    @Override
    public boolean editMunitionOutDetail(MunitionOutDetailEntity munitionOutDetail, ShiroUser user) {
        LambdaQueryWrapper<MunitionOutDetailEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionOutDetailEntity::getMunitionId, munitionOutDetail.getMunitionId()).eq(MunitionOutDetailEntity::getDeleteFlag, 1).ne(MunitionOutDetailEntity::getId, munitionOutDetail.getId());
        MunitionOutDetailEntity detail = munitionOutDetailMapper.selectOne(lambdaQuery);
        if (detail != null) {
            return false;
        }
        munitionOutDetail.setUpdateDate(new Date());
        munitionOutDetail.setUpdatePerson(user.getId());
        munitionOutDetailMapper.updateById(munitionOutDetail);
        return true;
    }

}
