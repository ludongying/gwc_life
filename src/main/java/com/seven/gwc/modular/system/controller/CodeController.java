package com.seven.gwc.modular.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.config.generator.action.config.WebGeneratorConfig;
import com.seven.gwc.config.generator.action.model.GenQo;
import com.seven.gwc.core.base.BaseController;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.system.entity.TablesEntity;
import com.seven.gwc.modular.system.service.TablesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * description : 代码生成控制器
 *
 * @author : GD
 * @date : 2019-08-02
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private static String PREFIX = "/modular/system/code/";

    @Autowired
    private TablesService tablesService;


    /**
     * 数据库驱动
     */
    @Value("${genQo.Driver}")
    private String driver;
    /**
     * 数据库url
     */
    @Value("${genQo.Url}")
    private String url;
    /**
     * 数据库账号
     */
    @Value("${genQo.UserName}")
    private String userName;
    /**
     * 数据库密码
     */
    @Value("${genQo.Password}")
    private String password;

    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code";
    }


    /**
     * 获取表结构列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public BaseResultPage<TablesEntity> list() {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<TablesEntity> tables = tablesService.getList(null);
        PageInfo pageInfo = new PageInfo<>(tables);
        return new BaseResultPage().createPage(pageInfo);
    }

    /**
     * 代码生成
     */
    @RequestMapping(value = "/generate")
    @ResponseBody
    public Object add(@RequestParam String author,
                      @RequestParam String pcodeName,
                      @RequestParam String proPackage,
                      @RequestParam String modularName,
                      @RequestParam String removePrefix,
                      @RequestParam String tableComment,
                      @RequestParam String tableNames,
                      @RequestParam String businessModules) {
        String[] split = tableNames.split(",");
        for (int i = 0; i < split.length; i++) {
            GenQo genQo = new GenQo();
            //作者(大写)
            genQo.setAuthor(author.toUpperCase());
            //菜单
            genQo.setParentMenuName(pcodeName == "" ? null : pcodeName);
            //包名称(小写)
            genQo.setProjectPackage(proPackage.toLowerCase());
            //模块名称(小写)
            genQo.setModuleName(modularName.toLowerCase());
            //前缀移除
            genQo.setIgnoreTabelPrefix(removePrefix == "" ? null : removePrefix);
            //表描述
            genQo.setBizChName(tableComment);
            //表
            genQo.setTableName(split[i]);

            for (String businessModule : businessModules.split(SysConsts.STR_COMMA)) {
                switch (businessModule) {
                    case "entity":
                        genQo.setEntitySwitch(true);
                        break;
                    case "controller":
                        genQo.setControllerSwitch(true);
                        break;
                    case "service":
                        genQo.setServiceSwitch(true);
                        break;
                    case "serviceImpl":
                        genQo.setServiceImplSwitch(true);
                        break;
                    case "mapper":
                        genQo.setMapperSwitch(true);
                        break;
                    case "xml":
                        genQo.setXmlSwitch(true);
                        break;
                    case "sql":
                        genQo.setSqlSwitch(true);
                        break;
                    case "index":
                        genQo.setIndexPageSwitch(true);
                        genQo.setIndexJsSwitch(true);
                        break;
                    case "add":
                        genQo.setAddPageSwitch(true);
                        genQo.setAddJsSwitch(true);
                        break;
                    case "edit":
                        genQo.setEditPageSwitch(true);
                        genQo.setEditJsSwitch(true);
                        break;
                    case "detail":
                        genQo.setDetailPageSwitch(true);
                        genQo.setDetailJsSwitch(true);
                        break;
                    default:
                        break;
                }
            }

            genQo.setDriver(driver);
            genQo.setUrl(url);
            genQo.setUserName(userName);
            genQo.setPassword(password);
            genQo.setDate((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            genQo.setProjectPath(System.getProperty("user.dir"));

            WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
            webGeneratorConfig.doMpGeneration();    //默认生成
            webGeneratorConfig.doTonFunGeneration();//自定义生成
        }
        return SUCCESS;
    }
}
