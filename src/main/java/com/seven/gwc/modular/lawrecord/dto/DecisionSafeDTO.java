package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.entity.DecisionSafeEntity;
import lombok.Data;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-06
 * @description :
 */
@Data
public class DecisionSafeDTO extends DecisionSafeEntity {
    /**
     * 省
     */
    private String punishAddrStateCode;
    /**
     * 市
     */
    private String punishAddrCityCode;
    /**
     * 区
     */
    private String punishAddrRegion;


    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getPunishAddr());
        if(Objects.nonNull(addrData)){
            this.punishAddrStateCode=addrData.getState().getCode();
            this.punishAddrCityCode=addrData.getCity().getCode();
            this.punishAddrRegion=addrData.getRegion();
        }
    }
}
