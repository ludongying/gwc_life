package com.seven.gwc.modular.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.core.exception.ServiceException;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 添加字典
     */
    @Override
    public void add(DictEntity dict) {
        //判断是否已经存在同编码或同名称字典
        QueryWrapper<DictEntity> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper
                .and(i -> i.eq("code", dict.getCode()).or().eq("name", dict.getName()))
                .and(i -> i.eq("dict_type_id", dict.getDictTypeId()));
        List<DictEntity> list = this.list(dictQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(ErrorEnum.ERROR_ONLY_STATUS);
        }
        //设置pids
        dictSetPids(dict);
        //设置状态
        dict.setStatus(TypeStatesEnum.OK.getCode());
        this.save(dict);
    }

    /**
     * 修改字典
     */
    @Override
    public void update(DictEntity dict) {
        DictEntity dictEntity = this.getById(dict.getId());

        //判断编码是否重复
        QueryWrapper<DictEntity> wrapper = new QueryWrapper<DictEntity>()
                .and(i -> i.eq("code", dictEntity.getCode()).or().eq("name", dictEntity.getName()))
                .and(i -> i.ne("id", dictEntity.getId()))
                .and(i -> i.eq("dict_type_id", dict.getDictTypeId()));
        int dicts = this.count(wrapper);
        if (dicts > 0) {
            throw new ServiceException(ErrorEnum.ERROR_ONLY_STATUS);
        }
        ShiroUser user = ShiroKit.getUser();
        dictEntity.setUpdateUser(user.getName());
        dictEntity.setUpdateTime(new Date());
        //设置pids
        dictSetPids(dictEntity);

        this.updateById(dictEntity);
    }

    /**
     * 根据ID获取字典详情
     */
    @Override
    public DictEntity dictDetail(Long dictId) {
        //查询字典
        DictEntity detail = this.getById(dictId);
        //查询父级字典
        if (ToolUtil.isNotEmpty(detail.getParentId())) {
            Long parentId = detail.getParentId();
            DictEntity dict = this.getById(parentId);
            if (dict != null) {
                detail.setParentName(dict.getName());
            } else {
                detail.setParentName("顶级");
            }
        }
        return detail;
    }

    @Override
    public List<Map<String, Object>> selectDictTree(String menuName, Long typeId) {
        List<Map<String, Object>> maps = this.dictMapper.selectDictTree(menuName, typeId);
        if (maps == null){
            maps = new ArrayList<>();
        }
        //创建根节点
        DictEntity dict = new DictEntity();
        dict.setName("根节点");
        dict.setId(0L);
        dict.setParentId(-999L);
        maps.add(BeanUtil.beanToMap(dict));
        return maps;
    }

    /**
     * 获取字典的树形列表（ztree结构）
     */
    @Override
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
    }

    private List<Long> getSubIds(Long dictId) {

        ArrayList<Long> longs = new ArrayList<>();

        if (ToolUtil.isEmpty(dictId)) {
            return longs;
        } else {
            List<DictEntity> list = dictMapper.likeParentIds(dictId);
            for (DictEntity dict : list) {
                longs.add(dict.getId());
            }
            return longs;
        }
    }

    /**
     * 设置父级Id
     */
    private void dictSetPids(DictEntity param) {
        if (param.getParentId().equals(0L)) {
            param.setParentIds("[0]");
        } else {
            //获取父级的pids
            Long parentId = param.getParentId();
            DictEntity parent = this.getById(parentId);
            if (parent == null) {
                param.setParentIds("[0]");
            } else {
                param.setParentIds(parent.getParentIds() + "," + "[" + parentId + "]");
            }
        }
    }
}
