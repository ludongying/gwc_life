package com.seven.gwc.modular.manage.controller;

import com.seven.gwc.core.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description : 船员信息控制器
 *
 * @author : LDY
 * @date : 2020-02-21
 */
@Controller
@RequestMapping("musicManage")
public class MusicManageController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/manage_music/";

    /**
     * 跳转到船员信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "music_manage";
    }

    /**
     * 跳转到添加信息
     */
    @RequestMapping("/musicManage_add")
    public String personAdd() {
        return PREFIX + "music_manage_add";
    }

    /**
     * 跳转到剧集管理信息
     * */
   @RequestMapping("/musicManage_series")
    public String musicSeries(String id) {
        return PREFIX + "music_series";
  }
//
//    /**
//     * 跳转到修改船员信息
//     */
//    @RequestMapping("/person_edit")
//    public String personUpdate(String id) {
//
//        return PREFIX + "person_edit";
//    }
//
//    /**
//     * 跳转到查看船员信息
//     */
//    @RequestMapping("/person_detail")
//    public String personDetail(String id) {
//        return PREFIX + "person_detail";
//    }
//
//    /**
//     * 获取船员信息列表
//     */
//    @RequestMapping("/list")
//    @ResponseBody
//    public BaseResultPage<PersonEntity> list(PersonEntity personEntity) {
//        Page page = BaseResultPage.defaultPage();
//        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
//        List<PersonEntity> persons = personService.selectPerson(personEntity,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
//        PageInfo pageInfo = new PageInfo<>(persons);
//        Integer size = personService.getListSize(personEntity);
//        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
//        pageInfo.setTotal(size);
//        return new BaseResultPage().createPage(pageInfo);
//    }
//
}

