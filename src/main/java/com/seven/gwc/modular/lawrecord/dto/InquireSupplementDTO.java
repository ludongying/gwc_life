package com.seven.gwc.modular.lawrecord.dto;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.entity.OperatorEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-29
 * @description :
 */
@Data
@Accessors(chain = true)
public class InquireSupplementDTO extends InquireEntity {

    private String inquireStartTime;
    private String inquireEndTime;
    private String inquireDateStr;
    private List<OperatorEntity> operators;

    private String id1;

    private String id2;

    private String personId1;

    private String personId2;

    private String personName1;

    private String personName2;

    private String credentialCode1;

    private String credentialCode2;

    public void setTime(){
        this.inquireStartTime=DateTimeUtil.parse2String(this.getInquireStartDate(),"yyyy-MM-dd HH:mm");
        this.inquireEndTime=DateTimeUtil.parse2String(this.getInquireEndDate(),"yyyy-MM-dd HH:mm");
        this.inquireDateStr=  this.inquireStartTime+" ~ "+ this.inquireEndTime;
    }

    public void setOperators(List<OperatorEntity> operators) {
        this.operators = operators;
        if(Objects.nonNull(operators) && operators.size()>0){
            OperatorEntity entity1 = operators.get(0);
            this.setId1(entity1.getId()).setPersonId1(entity1.getLawPersonId())
                    .setPersonName1(entity1.getLawPersonName()).setCredentialCode1(entity1.getLawCredentialCode());
            if(operators.size()>1){
                OperatorEntity entity2 = operators.get(1);
                this.setId2(entity2.getId()).setPersonId2(entity2.getLawPersonId())
                        .setPersonName2(entity2.getLawPersonName()).setCredentialCode2(entity2.getLawCredentialCode());
            }
        }
    }
}
