package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.data.instrument.InstrumentUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : zzl
 * @Date: 2020-03-23
 * @description :
 */
public class BaseDO implements Serializable {


    public Map<String,Object> toMap(){
         return InstrumentUtil.toMap(this);
    }
}
