package com.seven.gwc.modular.lawrecord.data.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author : zzl
 * @Date: 2020-03-04
 * @description :
 */
public class FileUtils {

    public static final String regex_dot          =  "\\.";

    public static final String file_sep           =  "\\";

    public static final String file_2_file_sep    =   ",";

    private static final List<String> file_units  =  Arrays.asList("b","kb","M","G","T");

    private static final int base_num             =  1024;

    private static double mathLog(int basement, Long n){
        return Math.log(n) / Math.log(basement);
    }

    private static String calcDouble(double d){
        return  String.format("%.1f", d);
    }

    private static String calcLength(Long length){
        if(length>base_num){
            int index = (int)mathLog(base_num, length);
            double ratio = Math.pow(base_num, index);
            return calcDouble(length/ratio)+file_units.get(index);
        }else{
            return length+file_units.get(0);
        }
    }

    /**
     * 生成文件夹
     */
    public static void generateDir(String dirPath){
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     * @param realPath
     * @return
     */
    public static boolean existFile(String realPath){
        File file = new File(realPath);
        if(file.exists() &&file.isFile()){
            return true;
        }
        return false;
    }

    /**
     * 是否为图片
     * @param fileName
     * @return
     */
    public static boolean isImage(String fileName){
        String[] arr = fileName.split(regex_dot);
        String suf = arr[arr.length - 1];
        return FileTypeEnum.image.suffix.contains("."+suf);
    }

    /**
     * 是否为视频
     * @param fileName
     * @return
     */
    public static boolean isVideo(String fileName){
        String[] arr = fileName.split(regex_dot);
        String suf = arr[arr.length - 1];
        return FileTypeEnum.video.suffix.contains("."+suf);
    }

    public static boolean isPdf(String fileName){
        return fileName.endsWith("."+FileTypeEnum.pdf.suffix.get(0));
    }

    public static FileTypeEnum getFileType(String fileName){
        if(isImage(fileName)){
            return FileTypeEnum.image;
        }else if(isVideo(fileName)){
            return FileTypeEnum.video;
        }else if(isPdf(fileName)){
            return FileTypeEnum.pdf;
        }
        return FileTypeEnum.other;
    }

    public static FileData getByPath(String realPath){
        FileData fileData=new FileData(realPath);
        fileData.setSize(calcLength(fileData.getLength()));
        fileData.setType(getFileType(fileData.getRealName()).getCode());
        return fileData;
    }

    public static String getDir(String realPath){
        if(Objects.nonNull(realPath) && !realPath.trim().isEmpty()){
            return realPath.substring(0,realPath.lastIndexOf(file_sep));
        }
        return null;
    }

    public static String getStaticPath(){
        return Thread.currentThread().getContextClassLoader().getResource("").getPath()+FileUtils.file_sep+"static";
    }


}
