package com.seven.gwc.modular.munition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.annotation.DataScope;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.munition.dao.MunitionInDetailMapper;
import com.seven.gwc.modular.munition.dao.MunitionInMapper;
import com.seven.gwc.modular.munition.dao.MunitionStoreMapper;
import com.seven.gwc.modular.munition.entity.MunitionInDetailEntity;
import com.seven.gwc.modular.munition.entity.MunitionInEntity;
import com.seven.gwc.modular.munition.entity.MunitionStoreEntity;
import com.seven.gwc.modular.munition.munitionEnum.MunitionInOutStatesEnum;
import com.seven.gwc.modular.munition.service.MunitionInService;
import com.seven.gwc.modular.munition.service.MunitionStoreService;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description : 物资入库服务实现类
 *
 * @author : LDY
 * @date : 2020-04-07
 */
@Service
public class MunitionInServiceImpl extends ServiceImpl<MunitionInMapper, MunitionInEntity> implements MunitionInService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MunitionInMapper munitionInMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MunitionInDetailMapper munitionInDetailMapper;
    @Autowired
    private MunitionStoreService munitionStoreService;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<MunitionInEntity> selectMunitionIn(MunitionInEntity munitionIn, Integer total, Integer size){
        List<MunitionInEntity> lists = munitionInMapper.selectMunitionInList(munitionIn,total,size);
        for(MunitionInEntity munitionInEntity: lists){
            if(ToolUtil.isNotEmpty(munitionInEntity.getMunitionType())){
                DictEntity dictEntity = dictMapper.selectById(munitionInEntity.getMunitionType());
                if(dictEntity != null){
                    munitionInEntity.setMunitionTypeDesp(dictEntity.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionInEntity.getApplyPerson())){
                UserEntity userEntity = userMapper.selectById(munitionInEntity.getApplyPerson());
                if(userEntity != null){
                    munitionInEntity.setApplyPersonDesp(userEntity.getName());
                }
            }
            if(ToolUtil.isNotEmpty(munitionInEntity.getInOutPerson())){
                UserEntity userEntity = userMapper.selectById(munitionInEntity.getInOutPerson());
                if(userEntity != null){
                    munitionInEntity.setInOutPersonDesp(userEntity.getName());
                }
            }
        }
        return lists;
    }

    @Override
    public Integer getListSize(MunitionInEntity munitionIn) {
        List<MunitionInEntity> list = munitionInMapper.getListSize(munitionIn);
        return list.size();
    }

    @Override
    public boolean addMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInEntity::getCode, munitionIn.getCode());
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQuery);
        if (munitionInEntity != null) {
            return false;
        }
        munitionIn.setActionType(0);//入库为0，出库为1
        //表单状态：0保存，1提交，2出入库通过，3出入库驳回，4审核通过，5审核未通过
        munitionIn.setStatus(MunitionInOutStatesEnum.SAVE.getCode());
        munitionIn.setCreateDate(new Date());
        munitionIn.setCreatePerson(user.getId());
        munitionIn.setSynFlag(false);
        munitionIn.setDeleteFlag(true);
        munitionInMapper.insert(munitionIn);
        return true;
    }

    @Override
    public void deleteMunitionIn(String munitionInId, ShiroUser user) {
        MunitionInEntity munitionInEntity = munitionInMapper.selectById(munitionInId);
        if (munitionInEntity != null) {
            munitionInEntity.setDeleteFlag(false);
            munitionInEntity.setSynFlag(false);
            munitionInEntity.setUpdateDate(new Date());
            munitionInEntity.setUpdatePerson(user.getId());
        }
        munitionInMapper.updateById(munitionInEntity);
    }

    @Override
    public boolean editMunitionIn(MunitionInEntity munitionIn, ShiroUser user) {
        LambdaQueryWrapper<MunitionInEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(MunitionInEntity::getCode,munitionIn.getCode()).eq(MunitionInEntity::getDeleteFlag, 1).ne(MunitionInEntity::getId,munitionIn.getId());
        MunitionInEntity munitionInEntity = munitionInMapper.selectOne(lambdaQuery);
        if(munitionInEntity != null){
            return false;
        }
        munitionIn.setUpdateDate(new Date());
        munitionIn.setUpdatePerson(user.getId());
        munitionInMapper.updateById(munitionIn);
        return true;
    }

    @Override
    public String getAutoCode() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = format.format(new Date());
        Integer code = munitionInMapper.maxCode(uid_pfix);
        String codestr = uid_pfix;
        if(code==0){
            codestr += "1001"; //1001为当天初始表单
        }else{
            code = code + 1001;
            codestr += code.toString();
        }
        return codestr;
    }

    @Override
    public int setStatus(String munitionInId, String state , ShiroUser user) {
        LambdaUpdateWrapper<MunitionInEntity> lambdaUpdate = Wrappers.<MunitionInEntity>lambdaUpdate();
        if(state == MunitionInOutStatesEnum.SUBMIT.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getApplyPerson,user.getId()).set(MunitionInEntity::getApplyTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else if(state == MunitionInOutStatesEnum.MUNITION_IN_OUT_OK.getCode() || state == MunitionInOutStatesEnum.MUNITION_IN_OUT_REFUSED.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getInOutPerson,user.getId()).set(MunitionInEntity::getInOutTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else if(state == MunitionInOutStatesEnum.APPROVE.getCode() || state == MunitionInOutStatesEnum.REFUSED.getCode()){
            lambdaUpdate.set(MunitionInEntity::getStatus, state).set(MunitionInEntity::getApprovePerson,user.getId()).set(MunitionInEntity::getApproveTime,new Date()).eq(MunitionInEntity::getId, munitionInId);
        }
        else{
            lambdaUpdate.set(MunitionInEntity::getStatus, state).eq(MunitionInEntity::getId, munitionInId);
        }
        return this.munitionInMapper.update(null, lambdaUpdate);
    }

    @Override
    public MunitionInEntity getMunitionInDetail(String id) {
        MunitionInEntity munitionInEntity = munitionInMapper.selectById(id);
        if(ToolUtil.isNotEmpty(munitionInEntity.getInOutPerson())){
            UserEntity userEntity = userMapper.selectById(munitionInEntity.getInOutPerson());
            if(userEntity != null){
                munitionInEntity.setInOutPersonDesp(userEntity.getName());
            }
        }
        if(ToolUtil.isNotEmpty(munitionInEntity.getApprovePerson())){
            UserEntity userEntity = userMapper.selectById(munitionInEntity.getApprovePerson());
            if(userEntity != null){
                munitionInEntity.setApprovePersonDesp(userEntity.getName());
            }
        }
        return munitionInEntity;
    }

    @Override
    public void updateStoreList(String id, ShiroUser user) {
        MunitionInEntity munitionInEntity = munitionInMapper.selectById(id);
        if(munitionInEntity != null){
            ArrayList<String> detailIds =
                    Stream.of(munitionInEntity.getDetailId().split(FileUtils.file_2_file_sep))
                            .collect(Collectors.toCollection(ArrayList<String>::new));
            for(int i=0;i<detailIds.size();i++){
                MunitionInDetailEntity detailEntity = munitionInDetailMapper.selectById(detailIds.get(i));
                if(detailEntity != null){
                    //查找对应库存记录
                    MunitionStoreEntity store = munitionStoreService.getMunitionStore(detailEntity.getMunitionId());
                    if(store!=null){ //库存表存在，累加库存数量
                        MunitionStoreEntity storeEntity = new MunitionStoreEntity();
                        int num = detailEntity.getTotalNum() + store.getTotalNum();//数量累加
                        storeEntity.setTotalNum(num);
                        munitionStoreService.editMunitionStore(storeEntity,user);
                    }
                    else//库存表不存在，新增库存记录
                    {
                        MunitionStoreEntity storeEntity = new MunitionStoreEntity();
                        storeEntity.setTotalNum(detailEntity.getTotalNum());
                        storeEntity.setMunitionId(detailEntity.getMunitionId());
                        munitionStoreService.addMunitionStore(storeEntity,user);
                    }
                }
            }
        }
    }

}
