package com.seven.gwc.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * description : TODO
 * @date : 2019/12/16 10:07
 * @author : GD
 * @version : 1.0
 */
public class SysUtil {
    /**
     * 校验是否为Linux系统
     * @return
     */
    public static Boolean isLinux() {
        String os = System.getProperty("os.name");
        return !os.toLowerCase().startsWith("win");
    }

    public static String getmacAddress(){
        if (SysUtil.isLinux()) {
            // Linux操作系统
            return SysUtil.getMACAddressByLinux();
        } else {
            // Windows操作系统
            return SysUtil.getMACAddressByWindows();
        }
    }

    /**
     * 获取Linux系统中得MAC地址
     * @return
     * @throws Exception
     */
    public static String getMACAddressByLinux(){
        try {
            String[] cmd = {"ifconfig"};

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String str1 = sb.toString();
            String str2 = str1.split("ether")[1].trim();
            String result = str2.split("txqueuelen")[0].trim();
            br.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取Windows系统中的MAC地址
     * @return
     * @throws Exception
     */
    public static String getMACAddressByWindows(){
        try {
            String result = "";
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

            String line;
            int index = -1;
            while ((line = br.readLine()) != null) {
                index = line.toLowerCase().indexOf("物理地址");
                // 找到了
                if (index >= 0) {
                    index = line.indexOf(":");
                    if (index >= 0) {
                        result = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
            br.close();
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
