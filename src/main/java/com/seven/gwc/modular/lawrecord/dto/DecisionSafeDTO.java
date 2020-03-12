package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
import com.seven.gwc.modular.lawrecord.entity.DecisionSafeEntity;
import com.seven.gwc.modular.lawrecord.enums.PlotSeverityEnum;
import com.seven.gwc.modular.lawrecord.enums.PunishmentTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.WhetherEnum;
import com.seven.gwc.modular.lawrecord.enums.WithOutEnum;
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

    /************************************/
    /** 当事人类型（枚举） */
    private String punishPersonTypeStr;
    /** 地址 */
    private String punishAddrStr;
    /** 情节严重性 */
    private String severityStr;
    /** 是否有暴力抗法 */
    private String caseViolenceLawStr;

    private String punishSexStr;
    /************************************/

    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getPunishAddr());
        if(Objects.nonNull(addrData)){
            this.punishAddrStateCode=addrData.getState().getCode();
            this.punishAddrCityCode=addrData.getCity().getCode();
            this.punishAddrRegion=addrData.getRegion();
        }
    }

    public void setDetailContent(){
        PunishmentTypeEnum punishmentTypeEnum = PunishmentTypeEnum.findByCode(this.getPunishPersonType());
        this.punishPersonTypeStr=Objects.isNull(punishmentTypeEnum)?"":punishmentTypeEnum.getMessage();
        this.punishAddrStr="";
        AddrData addrData = AddrData.generateAddr(this.getPunishAddr());
        if(Objects.nonNull(addrData)){
            this.punishAddrStr=addrData.getState().getName()+addrData.getCity().getName()+addrData.getRegion();
        }
        PlotSeverityEnum plotSeverityEnum = PlotSeverityEnum.findByCode(this.getSeverity());
        this.severityStr= Objects.isNull(plotSeverityEnum)?"":plotSeverityEnum.getMessage();

        WhetherEnum whetherEnum = WhetherEnum.findByCode(this.getCaseViolenceLaw());
        this.caseViolenceLawStr= Objects.isNull(whetherEnum)?"":whetherEnum.getMessage();

        if(PunishmentTypeEnum.CITIZEN.getCode().equals(this.getPunishPersonType())){
            SexEnum sexEnum=SexEnum.findByCode(this.getPunishSex());
            this.punishSexStr=Objects.isNull(sexEnum)?"":sexEnum.getMessage();
        }
    }
}
