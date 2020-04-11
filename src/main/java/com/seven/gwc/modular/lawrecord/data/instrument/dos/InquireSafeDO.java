package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import com.seven.gwc.modular.lawrecord.entity.InquireSafeEntity;
import com.seven.gwc.modular.lawrecord.enums.ShipCaseEnum;
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
public class InquireSafeDO extends InquireDO{

    /**
     *船名号是否清晰 （预留）
     */
    protected String Clear;
    /**
     * 船名号涂写情况
     */
    protected  String A002;
    /**
     * 船籍港涂写情况
     */
    protected  String A003;
    /**
     * 船名牌悬挂情况
     */
    protected  String A004;

    /**
     * 配备救生圈数量
     */
    protected Integer A008;
    /**
     * 配备灭火器数量
     */
    protected Integer A009;
    /**
     * 配备救生衣数量
     */
    protected Integer A010;
    /**
     * 配备救生筏数量
     */
    protected Integer A011;
    /**
     * 职务船员数量
     */
    protected Integer A013;
    /**
     * 普通船员数量
     */
    protected Integer A014;

    /**
     * 整改通知书第一条 （预留）
     */
    protected String A015;
    /**
     * 整改通知书第二条（预留）
     */
    protected String A016;
    /**
     * 处理意见书损失表述（预留）
     */
    protected String A017;

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
     * 渔船船长
     */
    private String c201;




    public InquireSafeDO(InquireSafeEntity entity) {
        super(entity);
        ShipCaseEnum a002 = ShipCaseEnum.findByCode(entity.getShipCaseName());
        ShipCaseEnum a003 = ShipCaseEnum.findByCode(entity.getShipCaseRegistry());
        ShipCaseEnum a004 = ShipCaseEnum.findByCode(entity.getShipCaseCard());
        this.setA002(Objects.nonNull(a002)?a002.getMessage():"").setA003(Objects.nonNull(a003)?a003.getMessage():"")
            .setA004(Objects.nonNull(a004)?a004.getMessage():"");
        this.setA008(entity.getLifebuoyCount()).setA009(entity.getExtinguisherCount())
                .setA010(entity.getLifeVestCount()).setA011(entity.getLifeRaftCount())
                .setA013(entity.getShipCertificateCount()).setA014(entity.getShipCommonCertificateCount());

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

        if(Objects.nonNull(entity.getShipLength())){
            this.c201=entity.getShipLength()+"米";
        }




    }




}
