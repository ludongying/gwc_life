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
    private String Boat_Id;
    /**
     *被询问人姓名
     */
    private String Ask_Name;
    /**
     *被询问人性别
     */
    private String Ask_Sex;
    /**
     *被询问人年龄
     */
    private String Ask_Age;
    /**
     *被询问人住址
     */
    private String Ask_Address;
    /**
     *被询问人电话
     */
    private String Ask_Phone;
    /**
     *被询问人身份证号
     */
    private String Ask_ID;
    /**
     *被询问人职务
     */
    private String Ask_Position;
    /**
     *渔船船主姓名
     */
    private String Holder1;
    /**
     *渔船船长姓名
     */
    private String Captain;
    /**
     *船上总人数
     */
    private String Sum;
    /**
     *被询问人作业类型
     */
    private String Type;
    /**
     *携带有哪些生产网具，各有多少
     */
    private String Net;
    /**
     *渔船主机功率
     */
    private String Boat_Power;
    /**
     *捕捞许可证核定作业类型
     */
    private String Type_paperwork;
    /**
     *是否携带证件
     */
    private String Carry;
    /**
     *渔船出海时间
     */
    private String Sea_Time;
    /**
     *渔船出海港口
     */
    private String Sea_Port;
    /**
     *船上渔获物及损失
     */
    private String Product;
    /**
     *本航次生产了
     */
    private String Net_Number;
    /**
     *到达作业渔区的时间
     */
    private String Net_Time;
    /**
     *查获时渔船状态
     */
    private String Status;
    /**
     *渔船上网具是否湿润
     */
    private String flag1;
    /**
     *网具中是否有残留的渔获物
     */
    private String flag2;
    /**
     *甲板是否刚冲洗过
     */
    private String flag3;
    /**
     *船名号是否清晰
     */
    private String Clear;

}
