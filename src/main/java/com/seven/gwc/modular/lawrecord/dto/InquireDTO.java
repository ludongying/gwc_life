package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.enums.InvestigatePositionEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipRatedTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipRealTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipStatusEnum;
import lombok.Data;

import java.util.List;
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

    /**
     * 补录信息
     */
    private List<InquireSupplementDTO> inquireContent;

    /**************全详情*********************/

    private String investigateSexStr;

    private String investigatePositionStr;

    private String investigateAddrStr;

    private String shipRealTypeStr;

    private String shipRatedTypeStr;

    private String shipStatusStr;


    /***************************************/

    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getInvestigateAddr());
        if(Objects.nonNull(addrData)){
            this.investigateAddrStateCode=addrData.getState().getCode();
            this.investigateAddrCityCode=addrData.getCity().getCode();
            this.investigateAddrRegion=addrData.getRegion();
        }
    }

    public void setDetailContent(){
        SexEnum sexEnum = SexEnum.findByCode(this.getInvestigateSex());
        this.investigateSexStr=Objects.isNull(sexEnum)?"":sexEnum.getMessage();
        InvestigatePositionEnum investigatePositionEnum = InvestigatePositionEnum.findByCode(this.getInvestigatePosition());
        this.investigatePositionStr= Objects.isNull(investigatePositionEnum)?"":investigatePositionEnum.getMessage();
        this.investigateAddrStr="";
        AddrData addrData = AddrData.generateAddr(this.getInvestigateAddr());
        if(Objects.nonNull(addrData)){
            this.investigateAddrStr=addrData.getState().getName()+addrData.getCity().getName()+addrData.getRegion();
        }
        ShipRealTypeEnum shipRealTypeEnum = ShipRealTypeEnum.findByCode(this.getShipRealType());
        this.shipRealTypeStr= Objects.isNull(shipRealTypeEnum)?"":shipRealTypeEnum.getMessage();
        ShipRatedTypeEnum shipRatedTypeEnum = ShipRatedTypeEnum.findByCode(this.getShipRatedType());
        this.shipRatedTypeStr=Objects.isNull(shipRatedTypeEnum)?"":shipRatedTypeEnum.getMessage();
        ShipStatusEnum shipStatusEnum = ShipStatusEnum.findByCode(this.getShipStatus());
        this.shipStatusStr= Objects.isNull(shipStatusEnum)?"":shipStatusEnum.getMessage();
    }

}
