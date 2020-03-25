package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.InquireBase;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 笔录
 */

@Data
@NoArgsConstructor
public class InquireProduceDO extends InquireDO{
    /**
     *渔船船名号
     */
    protected String Boat_Id;


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
     *渔船出海时间
     */
    protected String Sea_Time;
    /**
     *渔船出海港口
     */
    protected String Sea_Port;
    /**
     *船上渔获物及损失-市场价值
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
     * 渔获物
     */
    protected String A005;

    public InquireProduceDO(InquireBase inquireBase) {
        super(inquireBase);
    }




}
