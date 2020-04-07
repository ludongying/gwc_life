package com.seven.gwc.modular.lawrecord.data.instrument.dos;


import lombok.ToString;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据-无存储固定值
 */
@ToString
public class RecordDO extends BaseDO {

    /**
     * 证件类型 身份证
     */
    private String c11;

    /**
     * 执法单位行政区划级别 市直属
     */
    private String c104;

    /**
     * 执法单位所属地区 江苏省
     */
    private String c107;

    /**
     * 执法单位行政级别 副处级
     */
    private String c303;

    /**
     * 查封扣押地点 中国海监江苏省总队连云港维权执法基地
     */
    private String Detention;

    /**
     *全宗号 0339
     */
    private String FondsNum;

    /**
     * 目录号 8
     */
    private Integer CatalogNum;


    public RecordDO() {
        this.c11 = "身份证";
        this.c104 = "市直属";
        this.c107 = "江苏省";
        this.c303 = "副处级";
        this.Detention = "中国海监江苏省总队连云港维权执法基地";
        this.FondsNum = "0339";
        this.CatalogNum = 8;
    }
}
