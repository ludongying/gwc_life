package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description :办案机关模板数据
 */
@Data
public class AgencyDO implements Serializable {

    /**
     * 执法单位简称
     */
    private String Short;
    /**
     * 执法单位名称
     */
    private String Enforcement_Agency;
    /**
     * 执法船船号
     */
    private String Official_Boat;
    /**
     * 执法人员一
     */
    private String Officer1;
    /**
     * 执法人员一执法证号
     */
    private String OfficerNumber1;
    /**
     * 执法人员二
     */
    private String Officer2;
    /**
     * 执法人员二执法证号
     */
    private String OfficerNumber2;
    /**
     * 处罚机关地址
     */
    private String Official_Address;
    /**
     *处罚机关联系人
     */
    private String Official_Contact;
    /**
     *联系人电话
     */
    private String Official_Contact_Phone;
    /**
     *指定银行
     */
    private String Bank;
    /**
     *复议机关
     */
    private String Reconsideration;
    /**
     *诉讼机关
     */
    private String Litigation;
    /**
     *案件年份
     */
    private Integer YEAR;
    /**
     *案件编号
     */
    private String Number;
    /**
     *案件来源
     */
    private String From;
    /**
     *案发地点
     */
    private String Location;
    /**
     *案发时间年
     */
    private String Fa_year;
    /**
     *案发时间月
     */
    private String Fa_Month;
    /**
     *案发时间日
     */
    private String Fa_Day;
    /**
     *案发时间时
     */
    private String Fa_Hour;
    /**
     *案发时间分
     */
    private String Fa_Minute;
    /**
     *勘验时间年
     */
    private String Check_year;
    /**
     *勘验时间月
     */
    private String Check_Month;
    /**
     *勘验时间日
     */
    private String Check_Day;
    /**
     *勘验时间时
     */
    private String Check_Hour1;
    /**
     *勘验时间分
     */
    private String Check_Minute1;
    /**
     *至勘验时间时
     */
    private String Check_Hour2;
    /**
     *至勘验时间分
     */
    private String Check_Minute2;
    /**
     *询问时间年
     */
    private String Ask_year;
    /**
     *询问时间月
     */
    private String Ask_Month;
    /**
     *询问时间日
     */
    private String Ask_Day;
    /**
     *询问时间时
     */
    private String Ask_Hour1;
    /**
     *询问时间分
     */
    private String Ask_Minute1;
    /**
     *至询问时间时
     */
    private String Ask_Hour2;
    /**
     *至询问时间分
     */
    private String Ask_Minute2;
    /**
     *立案审批电话时间年
     */
    private String Phone_year;
    /**
     *立案审批电话时间月
     */
    private String Phone_Month;
    /**
     *立案审批电话时间日
     */
    private String Phone_Day;
    /**
     *立案审批电话时间时
     */
    private String Phone_Hour;
    /**
     *立案审批电话时间分
     */
    private String Phone_Minute;
    /**
     *受案时间年
     */
    private String Shou_year;
    /**
     *受案时间月
     */
    private String Shou_Month;
    /**
     *受案时间日
     */
    private String Shou_Day;
    /**
     *处理意见书电话时间年
     */
    private String Idea_year;
    /**
     *处理意见书电话时间月
     */
    private String Idea_Month;
    /**
     *处理意见书电话时间日
     */
    private String Idea_Day;
    /**
     *处罚决定书时间年
     */
    private String Punish_year;
    /**
     *处罚决定书时间月
     */
    private String Punish_Month;
    /**
     *处罚决定书时间日
     */
    private String Punish_Day;
    /**
     *结案时间年
     */
    private String Closed_year;
    /**
     *结案时间月
     */
    private String Closed_Month;
    /**
     *结案时间日
     */
    private String Closed_Day;
    /**
     *保管年限年
     */
    private String Keep_year;
    /**
     *保管年限月
     */
    private String Keep_Month;
    /**
     *保管年限日
     */
    private String Keep_Day;
}
