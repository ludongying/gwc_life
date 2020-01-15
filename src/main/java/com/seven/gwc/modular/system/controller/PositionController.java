package com.seven.gwc.modular.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.state.TypeStatesEnum;
import com.seven.gwc.modular.system.entity.PositionEntity;
import com.seven.gwc.modular.system.service.PositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 岗位控制器
 *
 * @author : GD
 * @date : 2019-09-20
 */
@Controller
@RequestMapping("position")
public class PositionController extends BaseController {

    private static String PREFIX = "/modular/system/position/";

    @Autowired
    private PositionService positionService;

    /**
     * 跳转到岗位首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "position";
    }

    /**
     * 跳转到添加岗位
     */
    @RequestMapping("/position_add")
    public String positionAdd() {
        return PREFIX + "position_add";
    }

    /**
     * 跳转到修改岗位
     */
    @RequestMapping("/position_edit")
    public String positionUpdate(Long positionId) {
        return PREFIX + "position_edit";
    }

    /**
     * 跳转到数据权限
     */
    @RequestMapping("/position_dataAuthority")
    public String positionDataAuthority(Long positionId) {
        return PREFIX + "position_dataAuthority";
    }

    /**
     * 获取岗位列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<PositionEntity> list(String positionName) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<PositionEntity> positions = positionService.selectPosition(positionName);
        PageInfo pageInfo = new PageInfo<>(positions);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 新增岗位
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(PositionEntity position) {
        if (position.getSort() == null) {
            position.setSort(0);
        }
        if (!positionService.add(position)) {
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_NAME);
        }
        return SUCCESS;
    }

    /**
     * 删除岗位
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam Long positionId) {
        if (!positionService.delete(positionId)) {
            return new BaseResult().failure(ErrorEnum.THE_DATA_USED);
        }
        return SUCCESS;
    }

    /**
     * 修改岗位
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(PositionEntity position, String menuIds) {
        if (position.getSort() == null) {
            position.setSort(0);
        }
        if (!positionService.update(position, menuIds)) {
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_NAME);
        }
        return SUCCESS;
    }

    /**
     * 岗位详情
     */
    @RequestMapping("/detail/{positionId}")
    @ResponseBody
    public PositionEntity detail(@PathVariable Long positionId) {
        return positionService.getById(positionId);
    }

    /**
     * 启动
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public BaseResult unfreeze(@RequestParam Long id) {
        this.positionService.setStatus(id, TypeStatesEnum.OK.getCode());
        return SUCCESS;
    }

    /**
     * 禁用
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public BaseResult freeze(@RequestParam Long id) {
        this.positionService.setStatus(id, TypeStatesEnum.PROHIBIT.getCode());
        return SUCCESS;
    }

    /**
     * 获取岗位列表
     */
    @RequestMapping("/listPositions")
    @ResponseBody
    public Object listPositions(String ids) {
        JSONArray jsonArray = positionService.listPositions(ids);
        return new BaseResult().content(jsonArray);
    }


}

