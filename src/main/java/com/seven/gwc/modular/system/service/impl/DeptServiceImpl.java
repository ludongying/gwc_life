package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DeptMapper;
import com.seven.gwc.modular.system.entity.DeptEntity;
import com.seven.gwc.modular.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * description : 部门服务实现类
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptEntity> implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptEntity> selectDept(String deptName, Long deptId) {
        LambdaQueryWrapper<DeptEntity> lambdaQuery = Wrappers.<DeptEntity>lambdaQuery();
        //select * from sys_dept (simple_name like '%张三%' or full_name like '%张三%') and (dept_id like '%10%' or pids like '%10%') order by sort asc
        // 包含全称和简称
        lambdaQuery.nested(ToolUtil.isNotEmpty(deptName), i -> i.like(DeptEntity::getFullName, deptName).
                or().
                like(DeptEntity::getSimpleName, deptName)).
                /*and(ToolUtil.isNotEmpty(deptId), i -> i.like(DeptEntity::getId, deptId).or().like(DeptEntity::getPids, "["+deptId+"]")).*/
                        orderByAsc(DeptEntity::getSort);
        List<DeptEntity> list = deptMapper.selectList(lambdaQuery);
        List<DeptEntity> deptEntityList = new ArrayList<>();
        if (list.size() > 0) {
            for (DeptEntity deptEntity : list) {
                if (deptEntity.getPid() != 0) {
                    DeptEntity dept = deptMapper.selectById(deptEntity.getPid());
                    deptEntity.setPName(dept.getFullName());
                }
                deptEntityList.add(deptEntity);
            }
        }
        return deptEntityList;
    }
    //

    /**
     * 查询所有，顶级OPEN状态
     */
    @Override
    public List<ZTreeNode> tree() {
        return this.deptMapper.tree();
    }

    @Override
    public List<DeptEntity> listTree(String deptName) {
        return this.deptMapper.liseTree(deptName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDept(DeptEntity deptEntity) {
        if (ToolUtil.isOneEmpty(deptEntity, deptEntity.getSimpleName(), deptEntity.getFullName(), deptEntity.getPid())) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(deptEntity);
        this.save(deptEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptEntity deptEntity) {
        if (ToolUtil.isOneEmpty(deptEntity, deptEntity.getId(), deptEntity.getSimpleName(), deptEntity.getFullName(), deptEntity.getPid())) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(deptEntity);
        this.updateById(deptEntity);
    }

    @Override
    public List<DeptEntity> selectDeptList() {
        return deptMapper.selectList(null);
    }

    /**
     * 拼接父级Id
     */
    private void deptSetPids(DeptEntity deptEntity) {
        if (ToolUtil.isEmpty(deptEntity.getPid()) || deptEntity.getPid().equals(0L)) {
            deptEntity.setPid(0L);
            deptEntity.setPids("[0],");
        } else {
            Long pid = deptEntity.getPid();
            DeptEntity temp = this.getById(pid);
            String pids = temp.getPids();
            deptEntity.setPid(pid);
            deptEntity.setPids(pids + "[" + pid + "],");
        }
    }

    @Override
    public boolean jude(Long id, Long pid) {
        LambdaQueryWrapper<DeptEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DeptEntity::getId, pid);
        DeptEntity deptEntity = deptMapper.selectOne(lambdaQuery);

        lambdaQuery.eq(DeptEntity::getId, deptEntity.getId())
                .like(DeptEntity::getPids, "[" + id + "]");
        DeptEntity entity = deptMapper.selectOne(lambdaQuery);

        if (entity != null) {
            return false;
        }

        return true;
    }

    @Override
    public DeptEntity getDeptById(Long deptId) {
        DeptEntity deptEntity = new DeptEntity();
        if (deptId != null) {
            deptEntity = deptMapper.selectById(deptId);
        }
        return deptEntity;
    }
}
