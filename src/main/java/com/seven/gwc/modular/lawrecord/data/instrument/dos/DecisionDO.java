package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.entity.DecisionBase;
import com.seven.gwc.modular.lawrecord.enums.PunishmentTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 执法决定模板数据
 */
@Data
@Accessors(chain = true)
public class DecisionDO implements Serializable {
  /**
   *当事人类型
   */
   protected String Litigant;
   /**
   *当事人姓名
   */
   protected String Name_1;
   /**
   *当事人性别
   */
   protected String Sex_1;
   /**
   *当事人年龄
   */
   protected Integer Age_1;
   /**
   *当事人住址
   */
   protected String Address_1;
   /**
   *当事人电话
   */
   protected String Phone_1;
   /**
   *当事人身份证号
   */
   protected String ID_1;
   /**
   *单位名称（或船名号）
   */
   protected String Name_Unit;
   /**
   *单位法定代表人（负责人）
   */
   protected String Legal;
   /**
   *单位联系电话
   */
   protected String Phone_Unit;
   /**
   *单位地址
   */
   protected String Address_Unit;

   /**
   *情节严重性
   */
   protected String Severity;
   /**
   *金额大写
   */
   protected String money;
   /**
   *金额小写
   */
   protected String money_low;
   /**
   *处罚依据(预留)
   */
   protected String Laws_Basis;
   /**
   *违反条款(预留)
   */
   protected String Laws_Violation;
   /**
   *违反条款处罚决定书(预留)
   */
   protected String Laws_Violation2;
   /**
    *处罚依据处罚决定书(预留)
    */
   protected String Laws_Basis2;


   public DecisionDO (DecisionBase decisionBase){
     PunishmentTypeEnum punishmentTypeEnum = PunishmentTypeEnum.findByCode(decisionBase.getPunishPersonType());
     SexEnum sexEnum = SexEnum.findByCode(decisionBase.getPunishSex());

    this.setLitigant(Objects.nonNull(punishmentTypeEnum)?punishmentTypeEnum.getMessage():"")
        .setName_1(decisionBase.getPunishPersonName()).setSex_1(Objects.nonNull(sexEnum)?sexEnum.getMessage():"")
        .setAge_1(decisionBase.getPunishAge()).setAddress_1(decisionBase.getPunishAddr())
            .setPhone_1(decisionBase.getPunishTel()).setID_1(decisionBase.getPunishIdentityCard())
        .setName_Unit(decisionBase.getPunishCompanyName()).setLegal(decisionBase.getPunishPersonName())
        .setPhone_Unit(decisionBase.getPunishTel()).setAddress_Unit(decisionBase.getPunishAddr());

   }


}
