package com.seven.gwc.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.exception.ServiceException;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.dao.DictTypeMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.entity.DictTypeEntity;
import com.seven.gwc.modular.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description : 字典服务实现类
 *
 * @author : LM
 * @date : 2019-10-10
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Override
    public List<DictEntity> selectDict(String dictTypeId) {
        LambdaQueryWrapper<DictEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DictEntity::getDictTypeId, dictTypeId)
                .orderByAsc(DictEntity::getSort)
                .orderByDesc(DictEntity::getCreateTime);
        return dictMapper.selectList(lambdaQuery);
    }

    /**
     * 添加字典
     */
    @Override
    public void add(DictEntity dict) {
        //判断是否已经存在同编码或同名称字典
        QueryWrapper<DictEntity> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper
                .and(i -> i.eq("name", dict.getName()))
                .and(i -> i.eq("dict_type_id", dict.getDictTypeId()));
        List<DictEntity> list = this.list(dictQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(ErrorEnum.ERROR_ONLY_STATUS);
        }
        dict.setCreateTime(new Date());
        this.save(dict);
    }

    /**
     * 编辑字典
     */
    @Override
    public void update(DictEntity dict) {
        ShiroUser user = ShiroKit.getUser();
        DictEntity dictEntity = this.getById(dict.getId());
        LambdaQueryWrapper<DictEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DictEntity::getName, dict.getName())
                .eq(DictEntity::getDictTypeId, dict.getDictTypeId())
                .ne(DictEntity::getId, dict.getId());

        DictEntity list = dictMapper.selectOne(lambdaQuery);
        if (list != null) {
            throw new ServiceException(ErrorEnum.ERROR_ONLY_CODE);
        }
        if (dict.getSort() == null) {
            dict.setSort(0);
        }
        dict.setUpdateUser(user.getName());
        dict.setUpdateTime(new Date());

        this.updateById(dict);
    }

    /**
     * 根据ID获取字典详情
     */
    @Override
    public DictEntity dictDetail(String dictId) {
        DictEntity entity = dictMapper.selectById(dictId);
        return entity;
    }

    @Override
    public List<ZTreeNode> getDictTreeByDictTypeCode(String dictTypeCode) {
        LambdaQueryWrapper<DictTypeEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(ToolUtil.isNotEmpty(dictTypeCode), DictTypeEntity::getCode, dictTypeCode);
        DictTypeEntity dictTypeEntity = dictTypeMapper.selectOne(lambdaQuery);
        return dictMapper.getDictTree(dictTypeEntity.getId());
    }

    @Override
    public List<DictEntity> getDictListByDictTypeCode(String dictTypeCode) {
        LambdaQueryWrapper<DictTypeEntity> dictTypeLambda = Wrappers.lambdaQuery();
        dictTypeLambda.eq(DictTypeEntity::getCode, dictTypeCode);
        DictTypeEntity dictTypeEntity = dictTypeMapper.selectOne(dictTypeLambda);

        LambdaQueryWrapper<DictEntity> dictLambda = Wrappers.lambdaQuery();
        dictLambda.eq(DictEntity::getDictTypeId, dictTypeEntity.getId());

        return dictMapper.selectList(dictLambda);
    }

    @Override
    public DictEntity findByNameAndTypeId(String name, String typeCode) {
        LambdaQueryWrapper<DictTypeEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(DictTypeEntity::getCode, typeCode);
        DictTypeEntity dictTypeEntity = dictTypeMapper.selectOne(lambdaQuery);

        LambdaQueryWrapper<DictEntity> typeLambdaQuery = Wrappers.lambdaQuery();
        typeLambdaQuery.eq(ToolUtil.isNotEmpty(name), DictEntity::getName, name)
                .eq(DictEntity::getDictTypeId, dictTypeEntity.getId());
        return dictMapper.selectOne(typeLambdaQuery);
    }

    /*@Override
    public List<Map<String, Object>> selectDictTree(String menuName, Long typeId) {
        List<Map<String, Object>> maps = this.dictMapper.selectDictTree(menuName, typeId);
        if (maps == null){
            maps = new ArrayList<>();
        }
        //创建根节点
        DictEntity dict = new DictEntity();
        dict.setName("根节点");
        dict.setId(0L);
        maps.add(BeanUtil.beanToMap(dict));
        return maps;
    }*/

    /**
     * 获取字典的树形列表（ztree结构）
     */
    /*@Override
    public List<ZTreeNode> dictTreeList(Long dictTypeId, Long dictId) {
        List<ZTreeNode> tree = dictMapper.dictTree(dictTypeId);
        //获取dict的所有子节点
        List<Long> subIds = getSubIds(dictId);
        //如果传了dictId，则在返回结果里去掉
        List<ZTreeNode> resultTree = new ArrayList<>();
        for (ZTreeNode zTreeNode : tree) {
            //如果dictId等于树节点的某个id则去除
            if (ToolUtil.isNotEmpty(dictId) && dictId.equals(zTreeNode.getId())) {
                continue;
            }
            if (subIds.contains(zTreeNode.getId())) {
                continue;
            }
            resultTree.add(zTreeNode);
        }
        resultTree.add(ZTreeNode.createParent());
        return resultTree;
    }*/

    private List<String> getSubIds(Long dictId) {

        ArrayList<String> longs = new ArrayList<>();

        if (ToolUtil.isEmpty(dictId)) {
            return longs;
        } else {
            List<DictEntity> list = dictMapper.likeParentIds(dictId.toString());
            for (DictEntity dict : list) {
                longs.add(dict.getId());
            }
            return longs;
        }
    }

}
