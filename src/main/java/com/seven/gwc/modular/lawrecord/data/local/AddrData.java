package com.seven.gwc.modular.lawrecord.data.local;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :
 */
@Data
@NoArgsConstructor
public class AddrData{

    /**
     * 省
     */
    private BaseData state;

    /**
     * 市
     */
    private BaseData city;

    /**
     * 地址
     */
    private String region;

    public String getAddress(){
        return JSON.toJSONString(this);
    }

    public static AddrData generateAddr(String str){
        if(Objects.nonNull(str) && !str.trim().isEmpty()){
            return JSON.parseObject(str,AddrData.class);
        }
        return null;
    }

    public static String getAddrFromJson(String str){
        AddrData addrData = generateAddr(str);
        if(Objects.nonNull(addrData)){
            String region=Objects.nonNull(addrData.getRegion())?addrData.getRegion():"";
            return  addrData.getState().getName()+addrData.getCity().getName()+region;
        }
        return null;
    }


}
