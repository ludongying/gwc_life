package com.seven.gwc.modular.resource.controller;

import com.seven.gwc.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("music")
public class MusicController extends BaseController {

    private static String PREFIX = "/modular/resource/";

    /**
     * 跳转到电影信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "music";
    }

//    /**
//     * 获取音乐信息列表
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
}
