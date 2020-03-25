package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentUtil;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : zzl
 * @Date: 2020-03-23
 * @description : 基类
 */
@ToString
public class BaseDO implements Serializable {


    public Map<String,String> toMap(){
         return InstrumentUtil.toMap(this);
    }
}
