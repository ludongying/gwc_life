package com.seven.gwc.modular.lawrecord.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :案件提示
 */
@Getter
public enum RecordTipEnum implements IEnum<Integer> {

    LEAVE_1(1,1,1,"没收渔获物和违法所得，罚款不得高于3.5万"),
    LEAVE_2(2,1,2,"没收渔获物和违法所得，罚款不得高于2.8万"),
    LEAVE_3(3,1,3,"没收渔获物和违法所得，罚款不得高于1.4万"),
    LEAVE_4(4,1,4,"没收渔获物和违法所得，罚款不得高于0.2万"),

    LEAVE_21(21,2,1,"没收渔获物和违法所得，罚款2万至3.5万"),
    LEAVE_22(22,2,2,"没收渔获物和违法所得，罚款1.6万至2.8万"),
    LEAVE_23(23,2,3,"没收渔获物和违法所得，罚款0.8万至1.4万"),
    LEAVE_24(24,2,4,"没收渔获物和违法所得，罚款不得高于0.4万"),

    LEAVE_41(41,3,1,"没收渔获物和违法所得，罚款3.5万至5万，如没收渔船罚款为1万至5万"),
    LEAVE_42(42,3,2,"没收渔获物和违法所得，罚款2.8万至4万，如没收渔船罚款为0.8万至4万"),
    LEAVE_43(43,3,3,"没收渔获物和违法所得，罚款1.4万至2万，如没收渔船罚款为0.4万至2万"),
    LEAVE_44(44,3,4,"没收渔获物和违法所得，罚款不得高于0.4万，如没收渔船罚款不得高于0.2万");

    Integer code;
    Integer severity;
    Integer level;
    @JsonValue
    String message;

    public static Integer HORSEPOWER_600=600;
    public static Integer HORSEPOWER_200=200;


    RecordTipEnum(Integer code, Integer severity,Integer level,String message) {
        this.code = code;
        this.severity=severity;
        this.level=level;
        this.message = message;
    }

    public static RecordTipEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (RecordTipEnum ms : RecordTipEnum.values()) {
                if (ms.getCode().equals(value)) {
                    return ms;
                }
            }
            return null;
        }
    }

    public static RecordTipEnum findBySeverity(Integer severity,Double power,Integer unit){
        if (severity == null) {
            return null;
        } else {
            Integer lev = calcPowerLevel(power, unit);
            for (RecordTipEnum ms : RecordTipEnum.values()) {
                if (ms.getSeverity().equals(severity) && ms.getLevel().equals(lev)) {
                    return ms;
                }
            }
            return null;
        }
    }




    public static Integer calcPowerLevel(Double power,Integer unit){
         if(Objects.isNull(power)){
             return 4;
         }
         if(PowerUnitEnum.KILOWATT.code.equals(unit)){
               power=power*1.36;
         }
         if(power>=HORSEPOWER_600){
             return 1;
         }else if(power>=HORSEPOWER_200){
             return 2;
         }else{
             return 3;
         }
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

}

