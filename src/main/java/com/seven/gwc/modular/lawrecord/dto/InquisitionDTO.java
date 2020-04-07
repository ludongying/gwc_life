package com.seven.gwc.modular.lawrecord.dto;


import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseCardEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseEnum;
import com.seven.gwc.modular.lawrecord.enums.WhetherEnum;
import lombok.Data;

import java.util.Objects;


/**
 * @author : zzl
 * @Date: 2020-03-02
 * @description : 勘验
 */
@Data
public class InquisitionDTO extends InquisitionEntity {


    /** 船名号书写情况 */
    private String shipCaseNameStr;

    /** 船籍港涂写情况 */
    private String shipCaseRegistryStr;

    /** 船名牌悬挂情况 */
    private String shipCaseCardStr;

    /** 师傅携带证件 */
    private String shipCaseCredentialsStr;

    /** 是否有残留渔获物 */
    private String shipCaseFishStr;


    public void setDetailContent(){
        ShipCaseEnum shipCaseEnum = ShipCaseEnum.findByCode(this.getShipCaseName());
        this.shipCaseNameStr= Objects.isNull(shipCaseEnum)?"":shipCaseEnum.getMessage();
        ShipCaseEnum shipCaseRegistryEnum = ShipCaseEnum.findByCode(this.getShipCaseRegistry());
        this.shipCaseRegistryStr=Objects.isNull(shipCaseRegistryEnum)?"":shipCaseRegistryEnum.getMessage();
        ShipCaseCardEnum shipCaseCardEnum = ShipCaseCardEnum.findByCode(this.getShipCaseCard());
        this.shipCaseCardStr=Objects.isNull(shipCaseCardEnum)?"":shipCaseCardEnum.getMessage();
        WhetherEnum whetherEnum=WhetherEnum.findByCode(this.getShipCaseCredentials());
        this.shipCaseCredentialsStr=Objects.isNull(whetherEnum)?"":whetherEnum.getMessage();
        WhetherEnum whetherFishEnum=WhetherEnum.findByCode(this.getShipCaseFish());
        this.shipCaseFishStr=Objects.isNull(whetherFishEnum)?"":whetherFishEnum.getMessage();
    }


}
