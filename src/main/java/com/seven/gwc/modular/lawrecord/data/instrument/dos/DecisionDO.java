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
   protected String Age_1;
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
   *处罚依据
   */
   protected String Laws_Basis;
   /**
   *违反条款
   */
   protected String Laws_Violation;
   /**
   *违反条款处罚决定书
   */
   protected String Laws_Violation2;
   /**
    *处罚依据处罚决定书
    */
   protected String Laws_Basis2;


}
