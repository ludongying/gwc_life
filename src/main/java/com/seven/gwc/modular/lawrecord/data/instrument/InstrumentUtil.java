package com.seven.gwc.modular.lawrecord.data.instrument;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
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
     * 大写数字
     */

    private static final String[] NUMBERS = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    /**
     * 整数部分的单位
      */
    private static final String[] IUNIT = {"元","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万","拾","佰","仟"};
    /**
     * 小数部分的单位
     */
    private static final String[] DUNIT = {"角","分","厘"};

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

    //转成中文的大写金额
    public static String number2Chinese(String str) {
        //判断输入的金额字符串是否符合要求
        if (StringUtils.isBlank(str) || !str.matches("(-)?[\\d]*(.)?[\\d]*")) {
            System.out.println("抱歉，请输入数字！");
            return str;
        }

        if("0".equals(str) || "0.00".equals(str) || "0.0".equals(str)) {
            return "零元";
        }

        //判断是否存在负号"-"
        boolean flag = false;
        if(str.startsWith("-")){
            flag = true;
            str = str.replaceAll("-", "");
        }
       //去掉","
        str = str.replaceAll(",", "");
        String integerStr;//整数部分数字
        String decimalStr;//小数部分数字


        //初始化：分离整数部分和小数部分
        if(str.indexOf(".")>0) {
            integerStr = str.substring(0,str.indexOf("."));
            decimalStr = str.substring(str.indexOf(".")+1);
        }else if(str.indexOf(".")==0) {
            integerStr = "";
            decimalStr = str.substring(1);
        }else {
            integerStr = str;
            decimalStr = "";
        }

        //beyond超出计算能力，直接返回
        if(integerStr.length()>IUNIT.length) {
            System.out.println(str+"：超出计算能力");
            return str;
        }
       //整数部分数字
        int[] integers = toIntArray(integerStr);
        //判断整数部分是否存在输入012的情况
        if (integers.length>1 && integers[0] == 0) {
            System.out.println("抱歉，请输入数字！");
            if (flag) {
                str = "-"+str;
            }
            return str;
        }
        //设置万单位
        boolean isWan = isWan5(integerStr);
        //小数部分数字
        int[] decimals = toIntArray(decimalStr);
        //返回最终的大写金额
        String result = getChineseInteger(integers,isWan)+getChineseDecimal(decimals);
        if(flag){
            //如果是负数，加上"负"
            return "负"+result;
        }else{
            return result;
        }
    }
    //将字符串转为int数组
    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];
        for(int i = 0;i<number.length();i++) {
            array[i] = Integer.parseInt(number.substring(i,i+1));
        }
        return array;
    }
    //将整数部分转为大写的金额
    public static String getChineseInteger(int[] integers,boolean isWan) {
        StringBuffer chineseInteger = new StringBuffer("");
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return "";
        }
        for(int i=0;i<length;i++) {
            String key = "";
            if(integers[i] == 0) {
                //万（亿）
                if((length - i) == 13)
                    key = IUNIT[4];
                    //亿
                else if((length - i) == 9) {
                    key = IUNIT[8];
                    //万
                }else if((length - i) == 5 && isWan) {
                    key = IUNIT[4];
                    //元
                }else if((length - i) == 1) {
                    key = IUNIT[0];
                }
                if((length - i)>1 && integers[i+1]!=0) {
                    key += NUMBERS[0];
                }
            }
            chineseInteger.append(integers[i]==0?key:(NUMBERS[integers[i]]+IUNIT[length - i -1]));
        }
        return chineseInteger.toString();
    }
    //将小数部分转为大写的金额
    private static String getChineseDecimal(int[] decimals) {
        StringBuffer chineseDecimal = new StringBuffer("");
        for(int i = 0;i<decimals.length;i++) {
            if(i == 3) {
                break;
            }
            chineseDecimal.append(decimals[i]==0?"":(NUMBERS[decimals[i]]+DUNIT[i]));
        }
        return chineseDecimal.toString();
    }
    //判断当前整数部分是否已经是达到【万】
    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if(length > 4) {
            String subInteger = "";
            if(length > 8) {
                subInteger = integerStr.substring(length- 8,length -4);
            }else {
                subInteger = integerStr.substring(0,length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        }else {
            return false;
        }
    }

    //Test
    public static void main(String[] args) {
        String number = "12";
        System.out.println(number+": "+ number2Chinese(number));

    }
    public static  Map<String,String> toMap(Object object){

        Map<String,String> res=new HashMap<>(32);
        if(Objects.nonNull(object)){
//            Map<String,Object> map = JSON.parseObject(JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty,
//                    SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteMapNullValue), Map.class);
            String str = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create().toJson(object);
            Map map = JSON.parseObject(str, Map.class);
            if(Objects.nonNull(map)){
                map.forEach((k,v)->res.put("${"+k+"}",Objects.nonNull(v)?v.toString():""));
            }
        }
        return res;
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

