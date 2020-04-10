package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.InquisitionEntity;
import com.seven.gwc.modular.lawrecord.enums.PowerUnitEnum;
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
     * 该甲板及网具上是否有残留的渔获物
     */
    private String flag2;

    /**
     * 船名号正确涂写内容-符号
     */
    private  String A0021;
    /**
     * 船籍港正确涂写内容-符号
     */
    private  String A0031;
    /**
     * 船名牌正确悬挂内容-符号
     */
    private  String A0041;

    /**
     * 根据是否携带证件生成
     */
    private String License1;
    /**
     * 根据是否携带证件生成
     */
    private String License2;
    /**
     * 根据是否携带证件生成
     */
    private String License3;
    /**
     * 根据是否携带证件生成
     */
    private String License4;
    /**
     * 根据是否携带证件生成
     */
    private String License5;

    
    public InquisitionDO(InquisitionEntity entity){
        WhetherEnum carry = WhetherEnum.findByCode(entity.getShipCaseCredentials());
        ShipCaseEnum a002= ShipCaseEnum.findByCode(entity.getShipCaseName());
        ShipCaseEnum a003 = ShipCaseEnum.findByCode(entity.getShipCaseRegistry());
        ShipCaseEnum a004 = ShipCaseEnum.findByCode(entity.getShipCaseCard());

        this.setCarry(Objects.nonNull(carry)?carry.getMessage():"");

        if(Objects.nonNull(a002)){
             if(a002.equals(ShipCaseEnum.CORRECT)){
                  this.setA002("正确涂写，是渔船船名号");
             }else if(a002.equals(ShipCaseEnum.ERROR)){
                 this.setA002("未正确涂写");
             }else{
                 this.setA002("未涂写");
             }
        }
        if(Objects.nonNull(a003)){
            if(a003.equals(ShipCaseEnum.CORRECT)){
                this.setA003("正确涂写，是船港籍");
            }else if(a003.equals(ShipCaseEnum.ERROR)){
                this.setA003("未正确涂写");
            }else{
                this.setA003("未涂写");
            }
        }

        if(Objects.nonNull(a004)){
            if(a004.equals(ShipCaseEnum.CORRECT)){
                this.setA004("正确悬挂，是渔船船名号");
            }else if(a004.equals(ShipCaseEnum.ERROR)){
                this.setA004("未正确悬挂");
            }else{
                this.setA004("未悬挂");
            }
        }
        this.setA0021("，").setA0031("，").setA0041("。");

        this.setLicense1("").setLicense2("").setLicense3("").setLicense4("").setLicense5("");
        if(Objects.nonNull(carry)){
            Double shipPower = entity.getShipPower();
            String shipPowerStr="";
            if(Objects.nonNull(shipPower)){
                shipPowerStr=shipPower+ PowerUnitEnum.findByCode(entity.getShipPowerUnit()).getMessage();
            }
            if(WhetherEnum.YES.equals(carry)){
               this.setLicense1("出示了").setLicense2("出示了").setLicense3("从事")
                       .setLicense4("有")
                       .setLicense5("出示了捕捞许可证,编号是"+
                               (Objects.nonNull(entity.getShipCredentialsOwnerIdentity())?entity.getShipCredentialsOwnerIdentity():"")+
                               "，船号是"+
                               (Objects.nonNull(entity.getShipCode())?entity.getShipCode():"")+
                               "，主机功率是"+
                               shipPowerStr+"，" +
                               "作业方式是"+
                               (Objects.nonNull(entity.getShipOperatePerson())?entity.getShipOperatePerson():"")+
                               "，持证人是"+
                               (Objects.nonNull(entity.getShipCredentialsOwner())?entity.getShipCredentialsOwner():"")+
                               "。");
           }else if(WhetherEnum.NO.equals(carry)){
               this.setLicense1("没有出示").setLicense2("未能出示").setLicense3("未携带捕捞许可证，从事")
                       .setLicense4("有，忘记带了").setLicense5("未能出示捕捞许可证");
           }
        }

        if(WhetherEnum.YES.getCode().equals(entity.getShipCaseFish())){
            this.setFlag2("该船甲板及网具上有残留的渔获物");
        }else{
            this.setFlag2("该船甲板及网具上没有残留的渔获物");
        }

    }
}
