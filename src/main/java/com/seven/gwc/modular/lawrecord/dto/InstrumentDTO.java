package com.seven.gwc.modular.lawrecord.dto;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-23
 * @description : 文书输出对象
 */
public class InstrumentDTO implements Serializable {

    /**
     * 编号
     */
    Integer code;
    /**
     * 模板名称
     */
    String message;

    /**
     * 模板路径
     */
    String modelPath;

    /**
     * 模板生成数据后的路径
     */
    String modelDataPath;

    /**
     * 文书路径
     */
    String path;





}
