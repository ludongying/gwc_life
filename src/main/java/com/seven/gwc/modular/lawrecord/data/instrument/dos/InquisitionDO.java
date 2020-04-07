package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseEnum;
import com.seven.gwc.modular.lawrecord.enums.WhetherEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-20
 * @description : 模板数据 勘验
 */
@Data
@Accessors(chain = true)
public class InquisitionDO extends BaseDO {

    /**
     *是否携带证件
     */
    private String Carry;

    /**
     * 船名号涂写情况
     */
    private  String A002;
    /**
     * 船籍港涂写情况
     */
    private  String A003;
    /**
     * 船名牌悬挂情况
     */
    private  String A004;
    /**
     * 船名号正确涂写内容（预留）
     */
    private  String A0021;
    /**
     * 船籍港正确涂写内容（预留）
     */
    private  String A0031;
    /**
     * 船名牌正确悬挂内容（预留）
     */
    private  String A0041;
    
    
    public InquisitionDO(InquisitionEntity entity){
        WhetherEnum carry = WhetherEnum.findByCode(entity.getShipCaseCredentials());
        ShipCaseEnum a002= ShipCaseEnum.findByCode(entity.getShipCaseName());
        ShipCaseEnum a003 = ShipCaseEnum.findByCode(entity.getShipCaseRegistry());
        ShipCaseEnum a004 = ShipCaseEnum.findByCode(entity.getShipCaseCard());

        this.setCarry(Objects.nonNull(carry)?carry.getMessage():"")
                .setA002(Objects.nonNull(a002)?a002.getMessage():"")
                .setA003(Objects.nonNull(a003)?a003.getMessage():"")
                .setA004(Objects.nonNull(a004)?a004.getMessage():"");
    }
}
