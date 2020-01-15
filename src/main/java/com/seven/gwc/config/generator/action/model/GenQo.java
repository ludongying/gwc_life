package com.seven.gwc.config.generator.action.model;

import lombok.Data;

/**
 * 代码生成的查询参数
 */
@Data
public class GenQo {

    private String driver;                      //数据库驱动
    private String url;                         //数据库url
    private String userName;                    //数据库账号
    private String password;                    //数据库密码

    private String projectPath;                 //项目地址
    private String projectPackage;              //项目的包
    private String moduleName;                  //模块名
    private String author;                      //作者
    private String date;                        //时间

    private String tableName;                   //表名称
    private String entityName;                  //实体的名称
    private String ignoreTabelPrefix;           //忽略的表前缀
    private String bizChName;                   //业务名称
    private String bizEnName;                   //业务英文名称
    private String bizEnBigName;                //业务英文名称(大写)
    private String parentMenuName;              //父级菜单名称

    private Boolean entitySwitch = false;       //生成实体的开关
    private Boolean controllerSwitch = false;   //是否生成控制器代码开关
    private Boolean serviceSwitch = false;      //service
    private Boolean serviceImplSwitch = false;  //serviceImpl
    private Boolean mapperSwitch = false;       //mapper的开关
    private Boolean xmlSwitch = false;          //xml的开关
    private Boolean sqlSwitch = false;          //生成sql的开关

    private Boolean indexPageSwitch = false;    //主页
    private Boolean indexJsSwitch = false;      //主页的js
    private Boolean addPageSwitch = false;      //添加页面
    private Boolean addJsSwitch = false;        //添加页面js
    private Boolean editPageSwitch = false;     //编辑页面
    private Boolean editJsSwitch = false;       //编辑页面js
    private Boolean detailPageSwitch = false;   //详情页面
    private Boolean detailJsSwitch = false;     //详情页面js

}
