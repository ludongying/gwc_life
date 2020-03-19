package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 执法决定模板数据
 */
@Data
public class DecisionDO implements Serializable {
  /**
   *当事人类型
   */
   private String Litigant;
   /**
   *当事人姓名
   */
   private String Name_1;
   /**
   *当事人性别
   */
   private String Sex_1;
   /**
   *当事人年龄
   */
   private String Age_1;
   /**
   *当事人住址
   */
   private String Address_1;
   /**
   *当事人电话
   */
   private String Phone_1;
   /**
   *当事人身份证号
   */
   private String ID_1;
   /**
   *单位名称（或船名号）
   */
   private String Name_Unit;
   /**
   *单位法定代表人（负责人）
   */
   private String Legal;
   /**
   *单位联系电话
   */
   private String Phone_Unit;
   /**
   *单位地址
   */
   private String Address_Unit;
   /**
   *案由(违规类型)
   */
   private String Type_Violation;
   /**
   *情节严重性
   */
   private String Severity;
   /**
   *金额大写
   */
   private String money;
   /**
   *金额小写
   */
   private String money_low;
   /**
   *处罚依据
   */
   private String Laws_Basis;
   /**
   *违反条款
   */
   private String Laws_Violation;
   /**
   *违反条款处罚决定书
   */
   private String Laws_Violation2;
   /**
    *处罚依据处罚决定书
    */
   private String Laws_Basis2;


}
