package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

import java.io.Serializable;
/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 勘验
 */
@Data
public class InquisitionDO implements Serializable {

    /**
     *是否携带证件
     */
    protected String Carry;

    /**
     * 船名号涂写情况
     */
    protected  String A002;
    /**
     * 船籍港涂写情况
     */
    protected  String A003;
    /**
     * 船名牌悬挂情况
     */
    protected  String A004;
    /**
     * 船名号正确涂写内容
     */
    protected  String A0021;
    /**
     * 船籍港正确涂写内容（预留）
     */
    protected  String A0031;
    /**
     * 船名牌正确悬挂内容（预留）
     */
    protected  String A0041;
}
