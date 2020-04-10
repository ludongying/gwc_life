package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import cn.hutool.core.date.DateUtil;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.enums.PowerUnitEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipRatedTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.ShipRealTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 笔录
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InquireProduceDO extends InquireDO{

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
    protected Integer Net_Number;
    /**
     *到达作业渔区的时间
     */
    protected String Net_Time;
    /**
     * 渔获物
     */
    protected String A005;

    /**
     * 造成渔业损失
     */
    protected String Lost;

    /**
     * 渔船出海海口
     */
    protected String FISHING_AREAS;

    /**
     * 核定主机功率
     */
    protected String c32;

    /**
     * 渔获物价值
     */
    protected String A017;



    public InquireProduceDO(InquireEntity entity) {
        super(entity);
        String shipGoodsValue = entity.getShipGoodsValue();
        ShipRealTypeEnum shipRealTypeEnum = ShipRealTypeEnum.findByCode(entity.getShipRealType());
        ShipRatedTypeEnum shipRatedTypeEnum = ShipRatedTypeEnum.findByCode(entity.getShipRatedType());
        this.setType(Objects.nonNull(shipRealTypeEnum)?shipRealTypeEnum.getMessage():"").setNet(entity.getShipInfo());

        Double shipRealPower = entity.getShipRealPower();
        PowerUnitEnum real = PowerUnitEnum.findByCode(entity.getShipRealPowerUnit());
        String realUnit=Objects.nonNull(real)?real.getMessage():"";
        this.setBoat_Power(Objects.nonNull(shipRealPower)?shipRealPower+realUnit:"")
             .setType_paperwork(Objects.nonNull(shipRatedTypeEnum)?shipRatedTypeEnum.getMessage():"");

        this.setSea_Time(DateUtil.format(entity.getShipOutDate(), "yyyy-MM-dd HH:mm"))
                .setSea_Port(entity.getShipOutPort())
                .setNet_Number(entity.getShipGenerateCount())
                .setNet_Time(DateUtil.format(entity.getShipFishAreaDate(), "yyyy-MM-dd HH:mm"))
                .setA005(entity.getShipGoodsCount());

        this.setFISHING_AREAS(this.getSea_Port());

        this.setC32(this.getBoat_Power());

        this.A017="";
        this.Lost="";
        this.Product="";
        if(Objects.nonNull(shipGoodsValue) && !shipGoodsValue.trim().isEmpty()){
            this.setLost("造成约"+shipGoodsValue+"的渔业损失，");
            this.setA017("据连云港物价局民生价格公示最新一期指导价格，该批渔获物价值约"+shipGoodsValue+"，");
            this.setProduct(this.getA005()+"，市场价值约"+shipGoodsValue);
        }



    }



}
