package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据 笔录
 */
@Data
public class InquireDO implements Serializable {
    /**
     *渔船船名号
     */
    protected String Boat_Id;
    /**
     *被询问人姓名
     */
    protected String Ask_Name;
    /**
     *被询问人性别
     */
    protected String Ask_Sex;
    /**
     *被询问人年龄
     */
    protected String Ask_Age;
    /**
     *被询问人住址
     */
    protected String Ask_Address;
    /**
     *被询问人电话
     */
    protected String Ask_Phone;
    /**
     *被询问人身份证号
     */
    protected String Ask_ID;
    /**
     *被询问人职务
     */
    protected String Ask_Position;
    /**
     *渔船船主姓名
     */
    protected String Holder1;
    /**
     *渔船船长姓名
     */
    protected String Captain;
    /**
     *船上总人数
     */
    protected String Sum;
    /**
     *被询问人作业类型
     */
    protected String Type;
    /**
     *携带有哪些生产网具，各有多少
     */
    protected String Net;
    /**
     *渔船主机功率
     */
    protected String Boat_Power;
    /**
     *捕捞许可证核定作业类型
     */
    protected String Type_paperwork;
    /**
     *是否携带证件
     */
    protected String Carry;
    /**
     *渔船出海时间
     */
    protected String Sea_Time;
    /**
     *渔船出海港口
     */
    protected String Sea_Port;
    /**
     *船上渔获物及损失
     */
    protected String Product;
    /**
     *本航次生产了
     */
    protected String Net_Number;
    /**
     *到达作业渔区的时间
     */
    protected String Net_Time;
    /**
     *查获时渔船状态
     */
    protected String Status;
    /**
     *渔船上网具是否湿润
     */
    protected String flag1;
    /**
     *网具中是否有残留的渔获物
     */
    protected String flag2;
    /**
     *甲板是否刚冲洗过
     */
    protected String flag3;
    /**
     *船名号是否清晰
     */
    protected String Clear;

}
