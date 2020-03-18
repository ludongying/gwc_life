package com.seven.gwc.config.constant;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author : zzl
 * @Date: 2020-03-09
 * @description :渔政船 业务常量
 */
public class GwcConsts {

    /**
     * 执法船船号
     */
    public static final String lawShipCode="中国渔政32511";
    /**
     * 船号
     */
    public static final String shipCode="1";

    /**
     * 案件编号
     */
    public static final String lawCode="连海渔执";
    /**
     * 执法单位
     */
    public static final String  enforcementAgency="连云港市海洋与渔业综合行政执法支队";
    /**
     * 处罚机关地址
     * 连云港市新浦区朝阳中路14号
     */
    public static final String  agencyAddr="连云港市新浦区朝阳中路14号";

    /**
     * 处罚机关联系人
     */
    public static final String agencyPerson="王学爱";

    /**
     * 处罚机关联系电话
     */
    public static final String agencyTel="13338982116";
    /**
     * 指定银行
     */
    public static final String bank="中国农业银行连云港分行营业部";
    /**
     * 复议机关
     */
    public static final String reviewAgency="连云港市海洋与渔业局";
    /**
     * 诉讼机关
     */
    public static final String lawsuitAgency="连云港市连云区人民法院";


    /**
     * 所有常量
     */
    private static Map<String,Object> fileds=new HashMap<>();

    /**
     * 获取所有常量
     * @return
     */
    public static Map<String,Object> getFileds(){
         if(fileds.isEmpty()){
             Field[] fs = GwcConsts.class.getFields();
             for (Field f : fs) {
                 f.setAccessible(true);
                 try {
                     fileds.put(f.getName(),f.get(GwcConsts.class));
                 } catch (Exception e) {
                 }
             }
         }
         return fileds;
     }

    /**
     * 将编号转换成三位
     * @param code
     */
     public static String getCodeStr(Integer code){
         if(Objects.nonNull(code)){
             int length = code.toString().length();
             switch (length){
                 case 1:
                     return shipCode+"00"+code;
                 case 2:
                     return shipCode+"0"+code;
                 default:
                     return shipCode+code.toString();
             }
         }
         return "";
     }

    /**
     *  code转int
     * @param code
     */
    public static Integer getCode(String code){
        if(Objects.nonNull(code) && !code.isEmpty()){
            String val = code.substring(shipCode.length());
            return Integer.parseInt(val);
        }
        return 0;
    }


    /**
     * 获取罚号
     * @return
     */
     public static String getLawCaseFineCode(){
         return lawCode+ LocalDate.now().getYear();
     }



}
