package com.seven.gwc.config.generator.engine.config;

import com.seven.gwc.config.generator.action.model.GenQo;

import java.util.ArrayList;
import java.util.List;

/**
 * Service模板生成的配置
 */
public class ServiceConfig {

    private GenQo genQo;                            //全局配置
    private String packageName;                     //包名称
    private String servicePathTemplate;             //Service接口名称
    private List<String> serviceInterfaceImports;   //Service接口导入包
    private String serviceImplPathTemplate;         //Service实现类名称
    private List<String> serviceImplImports;        //Service实现类导入包

    public void init() {
        /**
         * 接口
         */
        ArrayList<String> interfaceImports = new ArrayList<>();
        interfaceImports.add("com.baomidou.mybatisplus.extension.service.IService");
        interfaceImports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".model." + genQo.getEntityName());
        this.serviceInterfaceImports = interfaceImports;
        this.servicePathTemplate = "\\src\\main\\java\\" + genQo.getProjectPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + genQo.getModuleName() + "\\service\\I{}Service.java";

        /**
         * 实现类
         */
        ArrayList<String> imports = new ArrayList<>();
        imports.add("org.springframework.stereotype.Service");
        imports.add("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".model." + genQo.getEntityName());
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".dao." + genQo.getEntityName() + "Mapper");
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".service.I" + genQo.getBizEnBigName() + "Service");
        this.serviceImplImports = imports;
        this.serviceImplPathTemplate = "\\src\\main\\java\\" + genQo.getProjectPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + genQo.getModuleName() + "\\service\\impl\\{}ServiceImpl.java";


        this.packageName = genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".service";
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

    public String getServicePathTemplate() {
        return servicePathTemplate;
    }

    public void setServicePathTemplate(String servicePathTemplate) {
        this.servicePathTemplate = servicePathTemplate;
    }

    public List<String> getServiceInterfaceImports() {
        return serviceInterfaceImports;
    }

    public void setServiceInterfaceImports(List<String> serviceInterfaceImports) {
        this.serviceInterfaceImports = serviceInterfaceImports;
    }

    public String getServiceImplPathTemplate() {
        return serviceImplPathTemplate;
    }

    public void setServiceImplPathTemplate(String serviceImplPathTemplate) {
        this.serviceImplPathTemplate = serviceImplPathTemplate;
    }

    public List<String> getServiceImplImports() {
        return serviceImplImports;
    }

    public void setServiceImplImports(List<String> serviceImplImports) {
        this.serviceImplImports = serviceImplImports;
    }
}
