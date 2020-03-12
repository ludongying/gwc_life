package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.InquireSafeEntity;
import com.seven.gwc.modular.lawrecord.enums.*;
import lombok.Data;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-02
 * @description :
 */
@Data
public class InquireSafeDTO extends InquireSafeEntity {

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

    /***********************************************/
    private String investigateSexStr;

    private String investigatePositionStr;

    private String investigateAddrStr;

    private String shipStatusStr;

    /** 船名号书写情况 */
    private String shipCaseNameStr;

    /** 船籍港涂写情况 */
    private String shipCaseRegistryStr;

    /** 船名牌悬挂情况 */
    private String shipCaseCardStr;
    /***********************************************/

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
        ShipStatusEnum shipStatusEnum = ShipStatusEnum.findByCode(this.getShipStatus());
        this.shipStatusStr= Objects.isNull(shipStatusEnum)?"":shipStatusEnum.getMessage();
        ShipCaseEnum shipCaseEnum = ShipCaseEnum.findByCode(this.getShipCaseName());
        this.shipCaseNameStr= Objects.isNull(shipCaseEnum)?"":shipCaseEnum.getMessage();
        ShipCaseEnum shipCaseRegistryEnum = ShipCaseEnum.findByCode(this.getShipCaseRegistry());
        this.shipCaseRegistryStr=Objects.isNull(shipCaseRegistryEnum)?"":shipCaseRegistryEnum.getMessage();
        ShipCaseCardEnum shipCaseCardEnum = ShipCaseCardEnum.findByCode(this.getShipCaseCard());
        this.shipCaseCardStr=Objects.isNull(shipCaseCardEnum)?"":shipCaseCardEnum.getMessage();
        this.shipCaseCardStr=Objects.isNull(shipCaseCardEnum)?"":shipCaseCardEnum.getMessage();
    }

}
