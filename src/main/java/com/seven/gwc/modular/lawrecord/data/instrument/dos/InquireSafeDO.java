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

    }
}
