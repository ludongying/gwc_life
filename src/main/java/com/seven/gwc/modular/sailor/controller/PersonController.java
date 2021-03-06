package com.seven.gwc.modular.sailor.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.shiro.ShiroKit;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.state.ErrorEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.sailor.entity.PersonEntity;
import com.seven.gwc.modular.sailor.service.PersonService;
import com.seven.gwc.modular.sailor.vo.PersonVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description : 船员信息控制器
 *
 * @author : LDY
 * @date : 2020-02-21
 */
@Controller
@RequestMapping("person")
public class PersonController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String PREFIX = "/modular/sailor/person/";

    @Autowired
    private PersonService personService;

    /**
     * 跳转到船员信息首页
     */
    @RequestMapping("")
    public String index(Model model) {
        ShiroUser user = ShiroKit.getUser();
        model.addAttribute("userId", user.getId());
        model.addAttribute("roleNames", user.getRoleNames());
        return PREFIX + "person";
    }

    /**
     * 跳转到添加船员信息
     */
    @RequestMapping("/person_add")
    public String personAdd() {
        return PREFIX + "person_add";
    }

    /**
     * 跳转到修改船员信息
     */
    @RequestMapping("/person_edit")
    public String personUpdate(String id) {

        return PREFIX + "person_edit";
    }

    /**
     * 跳转到查看船员信息
     */
    @RequestMapping("/person_detail")
    public String personDetail(String id) {
        return PREFIX + "person_detail";
    }

    /**
     * 获取船员信息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public BaseResultPage<PersonEntity> list(PersonEntity personEntity) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<PersonEntity> persons = personService.selectPerson(personEntity,(int)(page.getCurrent() - 1) * (int)page.getSize(), (int)page.getSize());
        PageInfo pageInfo = new PageInfo<>(persons);
        Integer size = personService.getListSize(personEntity);
        pageInfo.setPages((int)Math.ceil((float)size / (float) page.getSize()));
        pageInfo.setTotal(size);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 增加船员信息
     */
    @RequestMapping("/add")
    @ResponseBody
    public BaseResult add(PersonEntity person) {
        ShiroUser user = ShiroKit.getUser();
        if(!personService.addPerson(person, user)){
            return new BaseResult().failure((ErrorEnum.ERROR_ONLY_PERSON_ID));
        }
        return SUCCESS;
    }

    /**
     * 删除船员信息
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(@RequestParam String id) {
        ShiroUser user = ShiroKit.getUser();
        personService.deletePerson(id, user);
        return SUCCESS;
    }

    /**
     * 编辑船员信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResult update(PersonEntity person) {
        ShiroUser user = ShiroKit.getUser();
        if(!personService.editPerson(person, user)){
            return new BaseResult().failure(ErrorEnum.ERROR_ONLY_PERSON_ID);
        }
        return SUCCESS;
    }

    /**
     * 船员信息详情
     */
    @RequestMapping("/detail/{id}")
    @ResponseBody
    public PersonEntity detail(@PathVariable String id) {
        PersonEntity personEntity = personService.getOneById(id);
        if (ToolUtil.isNotEmpty(personEntity.getDeptId())) {
            personEntity.setDeptName(CacheFactory.me().getDeptName(personEntity.getDeptId()));
        } else {
            personEntity.setDeptName("顶级");
        }
        return personEntity;
    }

    /**
     * 获取船员姓名列表
     */
    @RequestMapping("/listPersons")
    @ResponseBody
    public Object listPersons(String ids) {
        JSONArray jsonArray = personService.listPersons(ids);
        return new BaseResult().content(jsonArray);
    }

    /**
     * 获取执法船员实体列表
     */
    @RequestMapping("/listLawPersons")
    @ResponseBody
    public List<PersonVO> listLawPersons(){
        return personService.listLawPersons();
    }

    /**
     * 按部门获取所属船员
     */
    @RequestMapping("/listPersonsByDept")
    @ResponseBody
    public List<PersonEntity> listPersonsByDept(String deptId){
        return personService.listPersonsByDept(deptId);
    }
}

