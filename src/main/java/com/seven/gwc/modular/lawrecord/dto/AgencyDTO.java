package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-01
 * @description :
 */
@Data
public class AgencyDTO extends AgencyEntity {

    /**
     * 案件类型
     */
    private Integer lawType;

    /**
     * 执法人员
     */
    private List<OperatorEntity> operators;

    /**
     * 勘验时间
     */
    private String prospectDateStr;

    /**
     * 询问时间
     */
    private String inquireDateStr;

    /**
     * 省
     */
    private String agencyAddrStateCode;

    /**
     * 市
     */
    private String agencyAddrCityCode;
    /**
     * 区
     */
    private String agencyAddrRegion;


    private String concatTime(Date start, Date end){
       return DateTimeUtil.parse2String(start,"yyyy-MM-dd HH:mm")
               +" ~ "
               +DateTimeUtil.parse2String(end,"yyyy-MM-dd HH:mm");
    }

    public void setTimeStr(){
        if(Objects.nonNull(this.getProspectStartDate())){
            this.prospectDateStr=concatTime(this.getProspectStartDate(),this.getProspectEndDate());
        }
        if(Objects.nonNull(this.getInquireStartDate())){
            this.inquireDateStr=concatTime(this.getInquireStartDate(),this.getInquireEndDate());
        }
    }

    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getAgencyAddr());
        if(Objects.nonNull(addrData)){
            this.agencyAddrStateCode=addrData.getState().getCode();
            this.agencyAddrCityCode=addrData.getCity().getCode();
            this.agencyAddrRegion=addrData.getRegion();
        }
    }






}
