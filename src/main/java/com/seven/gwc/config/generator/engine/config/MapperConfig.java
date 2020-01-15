package com.seven.gwc.config.generator.engine.config;

import com.seven.gwc.config.generator.action.model.GenQo;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper模板生成的配置
 */
public class MapperConfig {

    private GenQo genQo;                //全局配置
    private String packageName;         //包名称
    private String daoPathTemplate;     //Mapper名称
    private List<String> daoImplImports;//Mapper导入包
    private String xmlPathTemplate;     //XML名称
    private String entityTemplate;      //实体名称

    public void init() {
        /**
         * Mapper
         */
        ArrayList<String> imports = new ArrayList<>();
        imports.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        imports.add(genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".model." + genQo.getEntityName());
        this.daoImplImports = imports;
        this.daoPathTemplate = "\\src\\main\\java\\" + genQo.getProjectPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + genQo.getModuleName() + "\\dao\\{}Mapper.java";

        /**
         * XML
         */
        this.xmlPathTemplate = "\\src\\main\\java\\" + genQo.getProjectPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + genQo.getModuleName() + "\\dao\\mapper\\{}Mapper.xml";

        this.packageName = genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".dao";
        this.entityTemplate = genQo.getProjectPackage() + ".modular." + genQo.getModuleName() + ".model." + genQo.getEntityName();
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

    public String getDaoPathTemplate() {
        return daoPathTemplate;
    }

    public void setDaoPathTemplate(String daoPathTemplate) {
        this.daoPathTemplate = daoPathTemplate;
    }

    public List<String> getDaoImplImports() {
        return daoImplImports;
    }

    public void setDaoImplImports(List<String> daoImplImports) {
        this.daoImplImports = daoImplImports;
    }

    public String getXmlPathTemplate() {
        return xmlPathTemplate;
    }

    public void setXmlPathTemplate(String xmlPathTemplate) {
        this.xmlPathTemplate = xmlPathTemplate;
    }

    public String getEntityTemplate() {
        return entityTemplate;
    }

    public void setEntityTemplate(String entityTemplate) {
        this.entityTemplate = entityTemplate;
    }
}
