package com.seven.gwc.modular.lawrecord.entity;

import com.seven.gwc.core.base.GwcBaseEntity;
import lombok.Data;

/**
 * @author zzl
 */
@Data
public class DecisionBase extends GwcBaseEntity {

    /** 主键 */
    private String id;

    /** 当事人类型（枚举） */
    private Integer punishPersonType;

    /** 单位名称 */
    private String punishCompanyName;

    /** 法定负责人 */
    private String punishPersonName;

    /** 年龄 */
    private Integer punishAge;

    /** 性别 */
    private Integer punishSex;

    /** 身份证号 */
    private String punishIdentityCard;

    /** 联系电话 */
    private String punishTel;

    /** 地址 */
    private String punishAddr;

    /** 情节严重性 */
    private Integer severity;

    /** 罚款 */
    private String fine;

    /** 罚款 大写*/
    private String fineUpper;

    /** 资源补偿费 */
    private String resourceCompensation;

    /** 资源补偿费大写 */
    private String resourceCompensationUpper;

    /** 处罚文表述 */
    private String description;

    /** 是否有暴力抗法 */
    private Integer caseViolenceLaw;
}
