package com.seven.gwc.config.generator.engine.config;

import com.seven.gwc.config.generator.action.model.GenQo;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制器模板生成的配置
 */
public class ControllerConfig {

    private GenQo genQo;                    //全局配置
    private String packageName;             //包名称
    private String controllerPathTemplate;  //Controller名称
    private List<String> imports;           //Controller导入包

    public void init() {
        /**
         *  Controller
         */
        ArrayList<String> imports = new ArrayList<>();
        imports.add("com.baomidou.mybatisplus.extension.plugins.pagination.Page");
        imports.add("com.seven.gwc.core.page.LayuiPageFactory");
        imports.add("com.github.pagehelper.PageHelper");
        imports.add("com.github.pagehelper.PageInfo");
        imports.add("com.seven.gwc.core.base.BaseController");
        imports.add("com.seven.gwc.core.base.BaseResult");
        imports.add("org.springframework.stereotype.Controller");
        imports.add("org.springframework.web.bind.annotation.RequestMapping");
        imports.add("org.springframework.web.bind.annotation.ResponseBody");
        imports.add("org.springframework.web.bind.annotation.PathVariable");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.web.bind.annotation.RequestParam");
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".model." + genQo.getEntityName());
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".service.I" + genQo.getEntityName() + "Service");
        this.imports = imports;
        this.controllerPathTemplate = "\\src\\main\\java\\" + genQo.getProjectPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + genQo.getModuleName() + "\\controller\\{}Controller.java";

        this.packageName = genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".controller";
    }

    public GenQo getGenQo() {
        return genQo;
    }

    public void setGenQo(GenQo genQo) {
        this.genQo = genQo;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getControllerPathTemplate() {
        return controllerPathTemplate;
    }

    public void setControllerPathTemplate(String controllerPathTemplate) {
        this.controllerPathTemplate = controllerPathTemplate;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }
}
