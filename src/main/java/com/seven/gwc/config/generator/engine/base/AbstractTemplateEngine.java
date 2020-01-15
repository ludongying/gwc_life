package com.seven.gwc.config.generator.engine.base;


import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.seven.gwc.config.generator.action.model.GenQo;
import com.seven.gwc.config.generator.engine.config.*;
import com.seven.gwc.config.generator.engine.config.*;

/**
 * 模板生成父类
 */
public class AbstractTemplateEngine {
    protected GenQo genQo;                      //全局配置
    protected ControllerConfig controllerConfig;//控制器的配置
    protected PageConfig pageConfig;            //页面的控制器
    protected MapperConfig mapperConfig;        //Dao配置
    protected ServiceConfig serviceConfig;      //Service配置
    protected SqlConfig sqlConfig;              //sql配置
    protected TableInfo tableInfo;              //表的信息
    protected InjectionConfig injectionConfig;  //全局配置

    public void initConfig() {

        if (this.genQo == null) {
            this.genQo = new GenQo();
        }
        if (this.controllerConfig == null) {
            this.controllerConfig = new ControllerConfig();
        }
        if (this.pageConfig == null) {
            this.pageConfig = new PageConfig();
        }
        if (this.mapperConfig == null) {
            this.mapperConfig = new MapperConfig();
        }
        if (this.serviceConfig == null) {
            this.serviceConfig = new ServiceConfig();
        }
        if (this.sqlConfig == null) {
            this.sqlConfig = new SqlConfig();
        }


        this.controllerConfig.setGenQo(this.genQo);
        this.controllerConfig.init();

        this.serviceConfig.setGenQo(this.genQo);
        this.serviceConfig.init();

        this.mapperConfig.setGenQo(this.genQo);
        this.mapperConfig.init();

        this.pageConfig.setGenQo(this.genQo);
        this.pageConfig.init();

        this.sqlConfig.setGenQo(this.genQo);
        this.sqlConfig.init();
    }


    public GenQo getGenQo() {
        return genQo;
    }

    public void setGenQo(GenQo genQo) {
        this.genQo = genQo;
    }

    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(ControllerConfig controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public PageConfig getPageConfig() {
        return pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }

    public MapperConfig getMapperConfig() {
        return mapperConfig;
    }

    public void setMapperConfig(MapperConfig mapperConfig) {
        this.mapperConfig = mapperConfig;
    }

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public SqlConfig getSqlConfig() {
        return sqlConfig;
    }

    public void setSqlConfig(SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public void setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
    }

}

