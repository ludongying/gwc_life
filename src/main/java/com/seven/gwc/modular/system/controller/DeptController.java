package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.core.annotation.BussinessLog;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.dictmap.DeptDict;
import com.seven.gwc.core.exception.BusinessException;
import com.seven.gwc.core.log.LogObjectHolder;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.node.ZTreeNode;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.DeptMapper;
import com.seven.gwc.modular.system.dao.UserMapper;
import com.seven.gwc.modular.system.entity.DeptEntity;
import com.seven.gwc.modular.system.entity.UserEntity;
import com.seven.gwc.modular.system.service.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * description : 部门控制器
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Controller
@RequestMapping("dept")
public class DeptController extends BaseController {

    private static String PREFIX = "/modular/system/dept/";

    @Autowired
    private DeptService deptService;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 跳转到部门首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add";
    }

    /**
     * 跳转到修改部门
     */
    @RequestMapping("/dept_edit")
    public String deptEdit(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
        }
        DeptEntity dept = deptService.getById(id);
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit";
    }

    /**
     * 跳转到查看部门
     */
    @RequestMapping("/dept_detail")
    public String deptDetail(Long id) {
        return PREFIX + "dept_detail";
    }

    /**
     * 获取部门列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<DeptEntity> list(String deptName, Long id) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<DeptEntity> depts = deptService.selectDept(deptName, id);
        PageInfo pageInfo = new PageInfo<>(depts);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 获取菜单列表（s树形）
     */
    @RequestMapping(value = "/listTree")
    @ResponseBody
    public Object listTree(String deptName) {
        List<DeptEntity> depts = this.deptService.listTree(deptName);
        return new BaseResultPage().treeData(depts);
    }

    /**
     * 增加部门
     */
    @BussinessLog(value = "增加部门", key = "simpleName", dict = DeptDict.class)
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(DeptEntity dept) {
        ShiroUser user = ShiroKit.getUser();

        DeptEntity param = new DeptEntity();
        param.setFullName(dept.getFullName());
        param.setPid(dept.getPid());
        QueryWrapper<DeptEntity> queryWrapper = new QueryWrapper<>(param);
        DeptEntity deptEntity = deptMapper.selectOne(queryWrapper);

        if (deptEntity != null) {
            return new BaseResult().failure(ErrorEnum.EXISTED_THE_DEPT_NAME);
        } else {
            if (dept.getSort() == null) {
                dept.setSort(0);
            }
            dept.setCreateTime(new Date());
            dept.setCreateUser(user.getId());
            deptService.saveDept(dept);
        }

        return SUCCESS;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "id", dict = DeptDict.class)
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long id) {
        LambdaQueryWrapper<UserEntity> userEntityQueryWrapper = Wrappers.lambdaQuery();
        userEntityQueryWrapper.eq(UserEntity::getDeptId, id);
        List<UserEntity> userEntityList = userMapper.selectList(userEntityQueryWrapper);
        if (userEntityList.size() > 0) {
            return new BaseResult().failure(ErrorEnum.THE_DEPART_EXISTED_USER);
        } else {
            LambdaQueryWrapper<DeptEntity> deptEntityQueryWrapper = Wrappers.lambdaQuery();
            deptEntityQueryWrapper.eq(DeptEntity::getPid, id);
            List<DeptEntity> deptEntityList = deptMapper.selectList(deptEntityQueryWrapper);
            if (deptEntityList.size() > 0) {
                return new BaseResult().failure(ErrorEnum.THE_DEPART_EXISTED_DEPART);
            } else {
                deptService.removeById(id);
            }
        }
        return SUCCESS;
    }

    /**
     * 编辑部门
     */
    @BussinessLog(value = "编辑部门", key = "simpleName", dict = DeptDict.class)
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(DeptEntity dept) {
        ShiroUser user = ShiroKit.getUser();

        QueryWrapper<DeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(DeptEntity::getFullName, dept.getFullName())
                .eq(DeptEntity::getPid, dept.getPid())
                .ne(DeptEntity::getId, dept.getId());
        DeptEntity deptEntity = deptMapper.selectOne(queryWrapper);

        if (deptEntity != null) {
            return new BaseResult().failure(ErrorEnum.EXISTED_THE_DEPT_NAME);
        } else {
            if (dept.getSort() == null) {
                dept.setSort(0);
            }
            if (!deptService.jude(dept.getId(), dept.getPid()) || dept.getId().equals(dept.getPid())) {
                return new BaseResult().failure(ErrorEnum.NOT_SUBORDINATES_AS_SUPERIORS);
            } else {
                dept.setUpdateTime(new Date());
                dept.setUpdateUser(user.getId());
                deptService.updateDept(dept);
            }
        }
        return SUCCESS;
    }

    /**
     * 部门详情
     */
    @RequestMapping("/detail/{id}")
    @ResponseBody
    public DeptEntity detail(@PathVariable Long id) {
        DeptEntity deptEntity = deptService.getById(id);
        deptEntity.setPName(CacheFactory.me().getDeptName(deptEntity.getPid()));
        return deptEntity;
    }

    /**
     * 获取部门的tree列表，ztree格式
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());

        return tree;
    }

    /**
     * 获取部门列表  查询用
     */
    @RequestMapping("/deptList")
    @ResponseBody
    public List<DeptEntity> deptList() {
        return deptService.selectDeptList();
    }

    @RequestMapping("/getDeptById")
    @ResponseBody
    public DeptEntity getDeptById(Long deptId) {
        return deptService.getDeptById(deptId);
    }
}

