package com.seven.gwc.modular.lawrecord.data.instrument;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.InquireProduceDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 中文金额转数字工具
 * @author zzl
 */
public class InstrumentUtil {

    private static char[] cnNum = new char[]{'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static char[] unitArr = new char[]{'厘', '分', '角', '圆', '拾', '佰', '仟', '万', '亿'};
    /**
     * 中文金额转数字
     * @param chineseNumber 中文金额
     * @return
     */
    public static BigDecimal chinese2Number(String chineseNumber) {
        BigDecimal result = new BigDecimal(0);
        int lastUnitIndex = 0, num = 0;
        chineseNumber = chineseNumber.replace("元", "圆");
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean isUnit = true;
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnNum.length; j++) {
                // 是数字
                if (c == cnNum[j]) {
                    // 数字值 = 索引
                    num = j;
                    isUnit = false;
                    break;
                }
            }
            if (isUnit) {
                // 第一个就是单位，如：拾伍万圆整
                if (i == 0) {
                    num = 1;
                }
                int unitIndex = getUnitIndex(c);
                BigDecimal unit = getUnit(c);
                if (unitIndex > lastUnitIndex) {
                    result = result.add(new BigDecimal(num)).multiply(unit);
                } else {
                    result = result.add(new BigDecimal(num).multiply(unit));
                }
                lastUnitIndex = unitIndex;
                num = 0;
            }
        }
        return result.setScale(2,BigDecimal.ROUND_DOWN);
    }

    private static int getUnitIndex(char c) {
        for (int j = 0; j < unitArr.length; j++) {
            if (c == unitArr[j]) {
                return j;
            }
        }
        return 0;
    }

    private static BigDecimal getUnit(char c) {
        double num = 0;
        int unitIndex = getUnitIndex(c);
        switch (unitIndex) {
            // '厘', '分', '角', '圆', '拾', '佰', '仟', '万', '亿'
            case 4:
                num = 10;
                break;
            case 5:
                num = 100;
                break;
            case 6:
                num = 1000;
                break;
            case 7:
                num = 10000;
                break;
            case 8:
                num = 100000000;
                break;
            case 3:
                num = 1;
                break;
            case 2:
                num = 0.1;
                break;
            case 1:
                num = 0.01;
                break;
            case 0:
                num = 0.001;
                break;
            default:
                break;
        }
        return new BigDecimal(num);
    }

    public static void main(String args[]) {
        String s1 = "壹万伍仟肆佰壹拾圆叁角伍分肆厘";
        String s2 = "捌万陆仟肆佰壹拾圆整";
        String s3 = "壹万伍仟肆佰壹拾元贰角捌分肆厘";
        String s4 = "拾壹亿壹仟万伍仟肆佰壹拾元贰角捌分肆厘";
        String s5 = "贰元叁角伍分";
        System.out.printf("%s = %6.2f\n", s1, chinese2Number(s1));
        System.out.printf("%s = %6.2f\n", s2, chinese2Number(s2));
        System.out.printf("%s = %6.2f\n", s3, chinese2Number(s3));
        System.out.printf("%s = %6.2f\n", s4, chinese2Number(s4));
        System.out.printf("%s = %6.2f\n", s5, chinese2Number(s5));

        InquireProduceDO inquireDO = new InquireProduceDO();
        inquireDO.setAsk_Address("e");
        System.out.println(toMap(inquireDO));
    }

    public static  Map<String,Object> toMap(Object object){
        if(Objects.nonNull(object)){
            return JSON.parseObject(JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteMapNullValue), Map.class);
        }
        return null;
    }

    public static LocalDate toLocalDate(Date date){
        if(Objects.nonNull(date)){
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
    public static LocalDateTime toLocalDateTime(Date date){
        if(Objects.nonNull(date)){
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }
}

