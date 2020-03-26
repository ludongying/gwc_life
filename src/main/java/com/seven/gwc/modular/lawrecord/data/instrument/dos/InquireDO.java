package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.modular.lawrecord.entity.InquireBase;
import com.seven.gwc.modular.lawrecord.enums.InvestigatePositionEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipStatusEnum;
import com.seven.gwc.modular.lawrecord.enums.WhetherEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-19
 * @description : 模板数据 笔录
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class InquireDO extends BaseDO {

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
    protected Integer Ask_Age;
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
     *渔船船长姓名（预留）
     */
    protected String Captain;
    /**
     *船上总人数
     */
    protected Integer Sum;
    /**
     *查获时渔船状态
     */
    protected String Status;
    /**
     *渔船上网具是否湿润（预留）
     */
    protected String flag1;
    /**
     *网具中是否有残留的渔获物（预留）
     */
    protected String flag2;
    /**
     *甲板是否刚冲洗过（预留）
     */
    protected String flag3;
    /**
     *携带身份证情况
     */
    protected  String A001;

    public InquireDO(InquireBase inquireBase){

        SexEnum sexEnum = SexEnum.findByCode(inquireBase.getInvestigateSex());
        InvestigatePositionEnum investigatePositionEnum = InvestigatePositionEnum.findByCode(inquireBase.getInvestigatePosition());
        ShipStatusEnum shipStatusEnum = ShipStatusEnum.findByCode(inquireBase.getShipStatus());
        WhetherEnum whetherEnum = WhetherEnum.findByCode(inquireBase.getIdentityCase());

        this.setBoat_Id(inquireBase.getShipName()).setAsk_Name(inquireBase.getInvestigateName())
            .setAsk_Sex(Objects.nonNull(sexEnum)?sexEnum.getMessage():"")
            .setAsk_Age(inquireBase.getInvestigateAge()).setAsk_Address(inquireBase.getInvestigateAddr())
            .setAsk_Phone(inquireBase.getInvestigateTel()).setAsk_ID(inquireBase.getIdentityCard())
            .setAsk_Position(Objects.nonNull(investigatePositionEnum)?investigatePositionEnum.getMessage():"")
            .setHolder1(inquireBase.getShipOwner()).setSum(inquireBase.getShipMember())
            .setStatus(Objects.nonNull(shipStatusEnum)?shipStatusEnum.getMessage():"")
            .setA001(Objects.nonNull(whetherEnum)?whetherEnum.getMessage():"");
    }









}
