package com.seven.gwc.config.generator.engine.config;

import com.seven.gwc.config.generator.action.model.GenQo;

/**
 * 页面 模板生成的配置
 */
public class PageConfig {

    private GenQo genQo;                    //全局配置
    private String pagePathTemplate;        //首页
    private String pageJsPathTemplate;      //首页js
    private String pageAddPathTemplate;     //添加页
    private String pageAddJsPathTemplate;   //添加页js
    private String pageEditPathTemplate;    //编辑页
    private String pageEditJsPathTemplate;  //编辑页js
    private String pageDetailPathTemplate;  //详情页
    private String pageDetailJsPathTemplate;//详情页js


    public void init() {
        /**
         * 首页
         */
        pagePathTemplate = "\\src\\main\\webapp\\templates\\modular\\" + genQo.getModuleName() + "\\{}\\{}.html";
        pageJsPathTemplate = "\\src\\main\\webapp\\static\\modular\\" + genQo.getModuleName() + "\\{}\\{}.js";

        /**
         * 添加
         */
        pageAddPathTemplate = "\\src\\main\\webapp\\templates\\modular\\" + genQo.getModuleName() + "\\{}\\{}_add.html";
        pageAddJsPathTemplate = "\\src\\main\\webapp\\static\\modular\\" + genQo.getModuleName() + "\\{}\\{}_add.js";

        /**
         * 编辑
         */
        pageEditPathTemplate = "\\src\\main\\webapp\\templates\\modular\\" + genQo.getModuleName() + "\\{}\\{}_edit.html";
        pageEditJsPathTemplate = "\\src\\main\\webapp\\static\\modular\\" + genQo.getModuleName() + "\\{}\\{}_edit.js";

        /**
         * 详情
         */
        pageDetailPathTemplate = "\\src\\main\\webapp\\templates\\modular\\" + genQo.getModuleName() + "\\{}\\{}_detail.html";
        pageDetailJsPathTemplate = "\\src\\main\\webapp\\static\\modular\\" + genQo.getModuleName() + "\\{}\\{}_detail.js";
    }


    public GenQo getGenQo() {
        return genQo;
    }

    public void setGenQo(GenQo genQo) {
        this.genQo = genQo;
    }

    public String getPagePathTemplate() {
        return pagePathTemplate;
    }

    public void setPagePathTemplate(String pagePathTemplate) {
        this.pagePathTemplate = pagePathTemplate;
    }

    public String getPageJsPathTemplate() {
        return pageJsPathTemplate;
    }

    public void setPageJsPathTemplate(String pageJsPathTemplate) {
        this.pageJsPathTemplate = pageJsPathTemplate;
    }

    public String getPageAddPathTemplate() {
        return pageAddPathTemplate;
    }

    public void setPageAddPathTemplate(String pageAddPathTemplate) {
        this.pageAddPathTemplate = pageAddPathTemplate;
    }

    public String getPageAddJsPathTemplate() {
        return pageAddJsPathTemplate;
    }

    public void setPageAddJsPathTemplate(String pageAddJsPathTemplate) {
        this.pageAddJsPathTemplate = pageAddJsPathTemplate;
    }

    public String getPageEditPathTemplate() {
        return pageEditPathTemplate;
    }

    public void setPageEditPathTemplate(String pageEditPathTemplate) {
        this.pageEditPathTemplate = pageEditPathTemplate;
    }

    public String getPageEditJsPathTemplate() {
        return pageEditJsPathTemplate;
    }

    public void setPageEditJsPathTemplate(String pageEditJsPathTemplate) {
        this.pageEditJsPathTemplate = pageEditJsPathTemplate;
    }

    public String getPageDetailPathTemplate() {
        return pageDetailPathTemplate;
    }

    public void setPageDetailPathTemplate(String pageDetailPathTemplate) {
        this.pageDetailPathTemplate = pageDetailPathTemplate;
    }

    public String getPageDetailJsPathTemplate() {
        return pageDetailJsPathTemplate;
    }

    public void setPageDetailJsPathTemplate(String pageDetailJsPathTemplate) {
        this.pageDetailJsPathTemplate = pageDetailJsPathTemplate;
    }
}
