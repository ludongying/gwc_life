package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import lombok.Data;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-02
 * @description :
 */
@Data
public class InquireDTO extends InquireEntity {

    /**
     * 省
     */
    private String investigateAddrStateCode;

    /**
     * 市
     */
    private String investigateAddrCityCode;
    /**
     * 区
     */
    private String investigateAddrRegion;


    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getInvestigateAddr());
        if(Objects.nonNull(addrData)){
            this.investigateAddrStateCode=addrData.getState().getCode();
            this.investigateAddrCityCode=addrData.getCity().getCode();
            this.investigateAddrRegion=addrData.getRegion();
        }
    }

}
