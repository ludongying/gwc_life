package com.seven.gwc.config.constant;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
     * 执法单位简称
     */
    public static final String shortName="苏连渔执";
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
    public static final String bank="中国农业银行连云港分行营业部（账户名称：连云港市海洋与渔业综合行政执法支队 账号431101012014549）";
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





}
