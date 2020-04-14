package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.munition.dao.MunitionInDetailMapper;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.service.MunitionInDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<MunitionInDetailEntity> selectMunitionInDetail(String munitionInDetailName){
        LambdaQueryWrapper<MunitionInDetailEntity> lambdaQuery = Wrappers.<MunitionInDetailEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(munitionInDetailName),MunitionInDetailEntity::getId,munitionInDetailName);
        return munitionInDetailMapper.selectList(lambdaQuery);
    }

    @Override
    public void addMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user) {
        LambdaQueryWrapper<MunitionInDetailEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInDetailEntity::getMunitionMainId,munitionInDetail.getMunitionMainId()).eq(MunitionInDetailEntity::getMunitionId,munitionInDetail.getMunitionId()).eq(MunitionInDetailEntity::getDeleteFlag,1);
        MunitionInDetailEntity detail = munitionInDetailMapper.selectOne(lambdaQuery);
        if (detail != null) {//该物资已存在，累加更新
            int num = detail.getTotalNum() + munitionInDetail.getTotalNum();
            munitionInDetail.setTotalNum(num);
            munitionInDetail.setUpdateDate(new Date());
            munitionInDetail.setUpdatePerson(user.getId());
            munitionInDetailMapper.updateById(munitionInDetail);
        }
        else{////该物资不存在，直接插入
            munitionInDetail.setSynFlag(false);
            munitionInDetail.setDeleteFlag(true);
            munitionInDetail.setCreateDate(new Date());
            munitionInDetail.setCreatePerson(user.getId());
            munitionInDetailMapper.insert(munitionInDetail);
        }
        //更新入库主表detailIds
        LambdaQueryWrapper<MunitionInEntity> lambdaQueryMunitionIn = Wrappers.lambdaQuery();
        lambdaQueryMunitionIn.eq(MunitionInEntity::getId,munitionInDetail.getMunitionMainId()).eq(MunitionInEntity::getDeleteFlag,1);
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQueryMunitionIn);
        if(munitionInEntity!=null){
            String details = munitionInEntity.getDetailId();
            if (ToolUtil.isNotEmpty(details)) {
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

    @Override
    public void deleteMunitionInDetail(String munitionInDetailId, ShiroUser user) {
        munitionInDetailMapper.deleteById(munitionInDetailId);
    }

    @Override
    public void editMunitionInDetail(MunitionInDetailEntity munitionInDetail, ShiroUser user) {
        munitionInDetailMapper.updateById(munitionInDetail);
    }

}
