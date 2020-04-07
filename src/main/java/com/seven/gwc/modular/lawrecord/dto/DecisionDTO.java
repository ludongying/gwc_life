package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.DecisionEntity;
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
public class DecisionDTO extends DecisionEntity {
    /**
     * 省
     */
    @Deprecated
    private String punishAddrStateCode;
    /**
     * 市
     */
    @Deprecated
    private String punishAddrCityCode;
    /**
     * 区
     */
    @Deprecated
    private String punishAddrRegion;


    /************************************/
    /** 当事人类型（枚举） */
    private String punishPersonTypeStr;
    /** 地址 */
    @Deprecated
    private String punishAddrStr;
    /** 情节严重性 */
    private String severityStr;
    /** 证据是否有物价局价格指导 */
    private String caseBureauPriceStr;
    /** 证据是否有禁渔期通告(农业部) */
    private String caseNotificationStr;
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

    /**
     *
     *   地址已修改
     *  this.punishAddrStr="";
     *  AddrData addrData = AddrData.generateAddr(this.getPunishAddr());
     *  if(Objects.nonNull(addrData)){
     *       this.punishAddrStr=addrData.getState().getName()+addrData.getCity().getName()+addrData.getRegion();
     *  }
     *
     *
     */
    public void setDetailContent(){
        PunishmentTypeEnum punishmentTypeEnum = PunishmentTypeEnum.findByCode(this.getPunishPersonType());
        this.punishPersonTypeStr=Objects.isNull(punishmentTypeEnum)?"":punishmentTypeEnum.getMessage();

        PlotSeverityEnum plotSeverityEnum = PlotSeverityEnum.findByCode(this.getSeverity());
        this.severityStr= Objects.isNull(plotSeverityEnum)?"":plotSeverityEnum.getMessage();

        WithOutEnum bureauPriceEnum = WithOutEnum.findByCode(this.getCaseBureauPrice());
        this.caseBureauPriceStr= Objects.isNull(bureauPriceEnum)?"":bureauPriceEnum.getMessage();

        WithOutEnum notificationEnum= WithOutEnum.findByCode(this.getCaseNotification());
        this.caseNotificationStr=Objects.isNull(notificationEnum)?"":notificationEnum.getMessage();

        WhetherEnum whetherEnum = WhetherEnum.findByCode(this.getCaseViolenceLaw());
        this.caseViolenceLawStr= Objects.isNull(whetherEnum)?"":whetherEnum.getMessage();

        if(PunishmentTypeEnum.CITIZEN.getCode().equals(this.getPunishPersonType())){
            SexEnum sexEnum=SexEnum.findByCode(this.getPunishSex());
            this.punishSexStr=Objects.isNull(sexEnum)?"":sexEnum.getMessage();
        }
    }
}
