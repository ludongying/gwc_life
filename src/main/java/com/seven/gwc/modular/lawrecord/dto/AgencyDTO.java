package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.local.AddrData;
import com.seven.gwc.modular.lawrecord.entity.AgencyEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-01
 * @description :
 */
@Data
@Accessors(chain = true)
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

    /**
     * 编号
     */
    private String lawCode;

    /**
     * 执法人员
     */
    private String id1;

    private String personId1;

    private String personName1;

    private String credentialCode1;

    private String id2;

    private String personId2;

    private String personName2;

    private String credentialCode2;

    /** 案发经度 */
    private String lawCaseLon1;
    private String lawCaseLon2;

    /** 案发纬度 */
    private String lawCaseLat1;
    private String lawCaseLat2;

    /****************全详情添加*********************/

    /**
     * 案件来源
     */
    private String lawCaseSourceStr;
    /**
     * 案件编号
     */
    private String lawCaseCodeStr;
    /**
     * 经纬度
     */
    private String lawCaseLonLatStr;

    /**************************************/

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

    public void setLawCodeStr(){
        setLawCode(this.getLawCaseFineCode()+""+this.getLawCaseCode());
    }

    public void setAddress(){
        AddrData addrData = AddrData.generateAddr(this.getAgencyAddr());
        if(Objects.nonNull(addrData)){
            this.agencyAddrStateCode=addrData.getState().getCode();
            this.agencyAddrCityCode=addrData.getCity().getCode();
            this.agencyAddrRegion=addrData.getRegion();
        }
    }

    public void setDetailContent(){
        LawCaseSourceEnum lawCaseSourceEnum = LawCaseSourceEnum.findByCode(this.getLawCaseSource());
        this.lawCaseSourceStr=Objects.isNull(lawCaseSourceEnum)?"":lawCaseSourceEnum.getMessage();
        Integer lawCaseCode = this.getLawCaseCode();
        Integer lawCaseFineCode = this.getLawCaseFineCode();
        this.lawCaseCodeStr="";
        if(Objects.nonNull(lawCaseFineCode)){
            lawCaseCodeStr+=lawCaseFineCode+"罚 ";
        }
        if(Objects.nonNull(lawCaseCode)){
            lawCaseCodeStr+=lawCaseCode+"号";
        }
        this.lawCaseLonLatStr="";
        String lawCaseLon = this.getLawCaseLon();
        String lawCaseLat = this.getLawCaseLat();
        if(Objects.nonNull(lawCaseLon)){
            lawCaseLonLatStr+=lawCaseLon+"经度";
        }
        if(Objects.nonNull(lawCaseLat)){
            lawCaseLonLatStr+=lawCaseLat+"纬度";
        }
    }

    public void setPersons(){
        if(Objects.nonNull(operators) && !operators.isEmpty()){
            OperatorEntity person1= operators.get(0);
            this.id1=person1.getId();
            this.personId1=person1.getLawPersonId();
            this.personName1=person1.getLawPersonName();
            this.credentialCode1=person1.getLawCredentialCode();
            if(operators.size() > 1){
                OperatorEntity person2= operators.get(1);
                this.id2=person2.getId();
                this.personId2=person2.getLawPersonId();
                this.personName2=person2.getLawPersonName();
                this.credentialCode2=person2.getLawCredentialCode();
            }
        }
    }


    public void setLonLat(){
        this.setLawCaseLon1("").setLawCaseLon2("").setLawCaseLat1("").setLawCaseLat2("");
        if(Objects.nonNull(this.getLawCaseLon()) && !this.getLawCaseLon().trim().isEmpty()){
            String[] split = this.getLawCaseLon().split(FileUtils.file_2_file_sep);
            int length = split.length;
            switch (length){
                case 2:
                    this.setLawCaseLat1(split[1]);
                case 1:
                    this.setLawCaseLon1(split[0]);
            }
        }
        if(Objects.nonNull(this.getLawCaseLat()) && !this.getLawCaseLat().trim().isEmpty()){
            String[] split = this.getLawCaseLat().split(FileUtils.file_2_file_sep);
            int length = split.length;
            switch (length){
                case 2:
                    this.setLawCaseLat2(split[1]);
                case 1:
                    this.setLawCaseLon2(split[0]);
            }
        }

    }

}
