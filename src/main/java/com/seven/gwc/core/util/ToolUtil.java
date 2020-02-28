package com.seven.gwc.core.util;

import com.seven.gwc.config.constant.SysConsts;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用工具类
 */
public class ToolUtil {

    /**
     * 校验有元素（空、null、""、.length=0）
     *
     * @param o 要检测的容器
     * @return 返回boolean类型true或false，有元素为true，无元素为false
     * <p>
     * 例如："abcdefghijk" -> true
     * 例如："" -> false
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 多参数校验无元素（空、null、""、.length=0）
     *
     * @param os 要检测的容器
     * @return 返回boolean类型true或false，有元素为false，无元素为true
     * <p>
     * 例如："abcdefghijk","123456","!@#$%^" -> false
     * 例如："abcdefghijk","","!@#$%^" -> true
     */
    public static boolean isOneEmpty(Object... os) {
        Object[] var1 = os;
        int var2 = os.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Object o = var1[var3];
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验无元素（空、null、""、.length=0）
     *
     * @param o 要检测的容器
     * @return 返回boolean类型true或false，有元素为false，无元素为true
     * <p>
     * 例如："abcdefghijk" -> false
     * 例如："" -> true
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else {
            if (o instanceof String) {
                if ("".equals(o.toString().trim())) {
                    return true;
                }
            } else if (o instanceof List) {
                if (((List) o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Map) {
                if (((Map) o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Set) {
                if (((Set) o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Object[]) {
                if (((Object[]) ((Object[]) o)).length == 0) {
                    return true;
                }
            } else if (o instanceof int[]) {
                if (((int[]) ((int[]) o)).length == 0) {
                    return true;
                }
            } else if (o instanceof long[] && ((long[]) ((long[]) o)).length == 0) {
                return true;
            }

            return false;
        }
    }

    /**
     * 首字母变小写
     *
     * @param str 字符串
     * @return 返回首字母变小写的字符串
     * <p>
     * 例如：HELLO_WORLD -> hELLO_WORLD
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母大写
     *
     * @param val 字符串
     * @return 返回首字母变大写的字符串
     * <p>
     * 例如：hello_world -> Hello_world
     */
    public static String firstCharToUpperCase(String val) {
        char firstChar = val.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = val.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return val;
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板,被替换的部分用{}表示
     * @param values   参数值
     * @return 格式化后的文本
     * <p>
     * 例如：\{}\{}.html,aaaa,bbbb -> \aaaa\bbbb.html
     */
    public static String format(String template, Object... values) {
        if (values == null || values.length == 0 || template == "") {
            return template;
        }

        final StringBuilder sb = new StringBuilder();
        final int length = template.length();

        int valueIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++) {
            if (valueIndex >= values.length) {
                sb.append(sub(template, i, length));
                break;
            }

            currentChar = template.charAt(i);
            if (currentChar == '{') {
                final char nextChar = template.charAt(++i);
                if (nextChar == '}') {
                    sb.append(values[valueIndex++]);
                } else {
                    sb.append('{').append(nextChar);
                }
            } else {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }

    /**
     * 截取字符串
     *
     * @param string    字符串
     * @param fromIndex 开始的索引(包含)
     * @param toIndex   结束索引(不包含)
     * @return 返回截取后的字符串
     * <p>
     * 例如：abcdefghijk,2,0 -> ab
     * 例如：abcdefghijk,2,3 -> c
     * 例如：abcdefghijk,2,-3 -> cdefgh
     */
    public static String sub(String string, int fromIndex, int toIndex) {
        int len = string.length();
        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex >= len) {
            fromIndex = len - 1;
        }
        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }
        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }
        if (fromIndex == toIndex) {
            return "";
        }
        char[] strArray = string.toCharArray();
        char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
        return new String(newStrArray);
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     * <p>
     * 例如：hello_world->helloWorld
     * 例如：HELLO_WORLD->helloWorld
     */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains("_")) {
            name = name.toLowerCase();

            StringBuilder sb = new StringBuilder(name.length());
            boolean upperCase = false;
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);

                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    /**
     * 剔除指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 返回剔除指定前缀得字符串, 若前缀不是preffix, 返回原字符串
     * <p>
     * 例如：sys_user,sys_ -> user
     * 例如：sys_user,aaa_ -> sys_user
     */
    public static String removePrefix(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }
        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }

        return str;
    }

    /**
     * 获取随机生成“盐”
     *
     * @param length 生成随即“盐”的长度
     * @return 生成对应长度的随机数
     * <p>
     * 例如：6 -> 7m6ulo
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 密码加密
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return 生成后返回SimpleHash格式的32位MD5值
     * <p>
     * 例如：123465,7m6ulo -> c17c7f07bf50c48552701409a4e82da4
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash("MD5", credentials, salt, 1024).toString();
    }

    /**
     * 获取文件后缀名
     *
     * @param fileWholeName 转换前的下划线大写方式命名的字符串
     * @return 截取后返回后缀名
     * <p>
     * 例如：test.xml -> xml
     */
    public static String getFileSuffix(String fileWholeName) {
        if (isEmpty(fileWholeName)) {
            return "none";
        } else {
            int lastIndexOf = fileWholeName.lastIndexOf(".");
            return fileWholeName.substring(lastIndexOf + 1);
        }
    }

    /**
     * 根据文件地址获取文件名
     *
     * @param FileUrl 需要获取文件名得文件
     * @return 截取后返回文件名
     * <p>
     * 例如：例如：12345678901234567890123456789012张三.xml -> 12345678901234567890123456789012张三
     */
    public static String getFileName(String fileUrl) {
        if (isEmpty(fileUrl)) {
            return "none";
        } else {
            int lastIndexOf1 = fileUrl.lastIndexOf("/");
            int lastIndexOf2 = fileUrl.lastIndexOf(".");
            return fileUrl.substring(lastIndexOf1 + 1, lastIndexOf2);
        }
    }

    /**
     * 替换后缀名
     *
     * @param FileUrl 文件全路径
     * @return 返回替换后的文件路径
     * <p>
     * 例如：例如：12345678901234567890123456789012张三.xml -> 12345678901234567890123456789012张三.pdf
     */
    public static String getFileUpSuffix(String fileUrl, String suffix) {
        if (isEmpty(fileUrl)) {
            return "none";
        } else {
            int lastIndexOf2 = fileUrl.lastIndexOf(".");
            return fileUrl.substring(0, lastIndexOf2) + suffix;
        }
    }

    /**
     * 根据文件地址获取移除唯一标识的文件名
     *
     * @param FileUrl 需要获取文件名得文件
     * @return 截取后返回文件名
     * <p>
     * 例如：12345678901234567890123456789012张三.xml -> 张三
     */
    public static String getFileNameNoUUID(String fileUrl) {
        if (isEmpty(fileUrl)) {
            return "none";
        } else {
            int lastIndexOf1 = fileUrl.lastIndexOf("/");
            int lastIndexOf2 = fileUrl.lastIndexOf(".");
            return fileUrl.substring(lastIndexOf1 + 33, lastIndexOf2);
        }
    }


    /**
     * ID自动生成规则
     *
     * @param mark
     * @return 时间+mark+UUID
     */
    public String JSid(String mark) {
        String date = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        String id = UUID.randomUUID().toString().substring(0, 13);
        return date + mark + id;
    }


    /**
     * 随即UUID移除横杠方法
     *
     * @return
     */
    public static String getUUIDremoveBars() {
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }

    public static String getPostfix(String path) {
        if (path == null || SysConsts.EMPTY.equals(path.trim())) {
            return SysConsts.EMPTY;
        }
        if (path.contains(SysConsts.POINT)) {
            return path.substring(path.lastIndexOf(SysConsts.POINT) + 1);
        }
        return SysConsts.EMPTY;
    }


    //处理数据格式，最后输出全是String类型
//    public static String getCellValue(Cell cell) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Object result = "";
//        if (cell != null) {
//            switch (cell.getCellType()) {
//                case Cell.CELL_TYPE_STRING:
//                    result = cell.getStringCellValue();
//                    break;
//                case Cell.CELL_TYPE_NUMERIC:
//                    if (DateUtil.isCellDateFormatted(cell)) {
//                        result = sdf.format(cell.getDateCellValue());
//                    } else {
//                        BigDecimal db = new BigDecimal(cell.getNumericCellValue());
//                        result = db.toPlainString();
//                    }
//                    break;
//                case Cell.CELL_TYPE_BOOLEAN:
//                    result = cell.getBooleanCellValue();
//                    break;
//                case Cell.CELL_TYPE_FORMULA:
//                    try {
//                        result = String.valueOf(cell.getNumericCellValue());
//                    } catch (IllegalStateException e) {
//                        result = String.valueOf(cell.getRichStringCellValue());
//                    }
//                    break;
//                case Cell.CELL_TYPE_ERROR:
//                    result = cell.getErrorCellValue();
//                    break;
//                case Cell.CELL_TYPE_BLANK:
//                    break;
//                default:
//                    break;
//            }
//        }
//        return result.toString();
//    }

    /**
     * 计算天数
     * @param start
     * @param end
     * @return
     */
    public static double getBiDays(Date start,Date end){
        Long time=end.getTime()-start.getTime();
        return time/(24.0*60*60*1000);
    }

    public static String getExceptionMsg(Throwable e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    /**
     * 判断是否是Ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString())) ;
    }

}
