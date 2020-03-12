package com.seven.gwc.modular.ship_info.controller;

import com.alibaba.fastjson.JSONArray;
import com.seven.gwc.core.state.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import com.seven.gwc.modular.ship_info.service.ShipService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.seven.gwc.core.base.BaseController;

import java.util.List;

/**
 * description : 执法船信息管理控制器
 *
 * @author : LDY
 * @date : 2020-02-12
 */
@Controller
@RequestMapping("ship")
public class ShipController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String PREFIX = "/modular/ship_info/ship/";

    @Autowired
    private ShipService shipService;

    /**
     * 跳转到执法船信息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "ship_detail_main";
    }
//    public String index() {
//        return PREFIX + "ship";
//    }

    /**
     * 跳转到添加执法船信息管理
     */
    @RequestMapping("/ship_add")
    public String shipAdd() {
        return PREFIX + "ship_add";
    }

    /**
     * 跳转到修改执法船信息管理
     */
    @RequestMapping("/ship_edit")
    public String shipUpdate(String shipId) {
        return PREFIX + "ship_edit";
    }

    /**
     * 跳转到查看执法船信息管理
     */
    @RequestMapping("/ship_detail")
    public String shipDetail(String shipId) {
        return PREFIX + "ship_detail";
    }

    /**
     * 获取执法船信息管理列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<ShipEntity> list(ShipEntity shipEntity) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<ShipEntity> ships = shipService.selectShip(shipEntity);
        PageInfo pageInfo = new PageInfo<>(ships);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加执法船信息管理
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(ShipEntity ship) {
        ShiroUser user = ShiroKit.getUser();
        //ship.setCertificateId("1");
        if(!shipService.add(ship)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_LAWSHIP_CODE);
        }
        return SUCCESS;
    }

    /**
     * 删除执法船信息管理
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String shipId) {
        //shipService.removeById(shipId);
        shipService.delete(shipId);
        return SUCCESS;
    }

    /**
     * 编辑执法船信息管理
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(ShipEntity ship) {
        ShiroUser user = ShiroKit.getUser();
        if(!shipService.update(ship)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_LAWSHIP_CODE);
        }
        //shipService.updateById(ship);
        return SUCCESS;
    }

    /**
     * 执法船信息管理详情
     */
    @RequestMapping("/detail/{shipId}")
    @ResponseBody
    public ShipEntity detail(@PathVariable String shipId) {
        return shipService.getById(shipId);
    }

    /**
     * 获取执法船列表
     */
    @RequestMapping("/listShips")
    @ResponseBody
    public List<ShipEntity> listShips(String id) {
        return shipService.listShips(id);
    }

    /**
     * 获取执法船信息管理列表
     */
    @RequestMapping("/detailFirst")
    @ResponseBody
    public ShipEntity shipFirst(ShipEntity shipEntity) {
        List<ShipEntity> ships = shipService.selectShip(shipEntity);
        return ships.get(0);
    }

    /**
     * 多船舶图片上传
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(){

    }

}

