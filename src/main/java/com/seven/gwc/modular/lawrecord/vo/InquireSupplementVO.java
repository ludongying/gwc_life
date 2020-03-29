package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-29
 * @description :
 */
@Data
public class InquireSupplementVO extends InquireEntity {

    private String inquireStartTime;

    private String inquireEndTime;

    private String id1;

    private String id2;

    private String personId1;

    private String personId2;

    private String personName1;

    private String personName2;

    private String credentialCode1;

    private String credentialCode2;

    List<OperatorEntity> operators;

    public void setContent(String recordId){

        if(Objects.nonNull(inquireStartTime)){
            this.setInquireStartDate(DateTimeUtil.parse2Date(inquireStartTime,"yyyy-MM-dd HH:mm"));
        }

        if(Objects.nonNull(inquireEndTime)){
            this.setInquireEndDate(DateTimeUtil.parse2Date(inquireEndTime,"yyyy-MM-dd HH:mm"));
        }

        operators=new ArrayList<>();
        id1=Objects.nonNull(id1) && id1.trim().isEmpty()?null:id1;
        id2=Objects.nonNull(id2) && id2.trim().isEmpty()?null:id2;
        personId1=Objects.nonNull(personId1) && personId1.trim().isEmpty()?null:personId1;
        personId2=Objects.nonNull(personId2) && personId2.trim().isEmpty()?null:personId2;

        if(Objects.nonNull(id1)||Objects.nonNull(personId1)){
            OperatorEntity operatorEntity = new OperatorEntity();
            operatorEntity.setLawPersonId(personId1).setRecordId(recordId).setLawPersonName(personName1)
                    .setLawCredentialCode(credentialCode1).setId(id1);
            operators.add(operatorEntity);
        }

        if(Objects.nonNull(id2)||Objects.nonNull(personId2)){
            OperatorEntity operatorEntity = new OperatorEntity();
            operatorEntity.setLawPersonId(personId2).setRecordId(recordId).setLawPersonName(personName2)
                    .setLawCredentialCode(credentialCode2).setId(id2);
            operators.add(operatorEntity);
        }




    }
}
