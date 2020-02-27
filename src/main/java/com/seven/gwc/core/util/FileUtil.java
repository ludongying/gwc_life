package com.seven.gwc.core.util;


import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传工具类
 */
public class FileUtil {

    public static JSONObject uploadFile(String fileDown, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = "";
        JSONObject jsonObject = new JSONObject();
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();

                File nodeFile = new File(fileDown);
                if (!nodeFile.exists()) {//如果文件夹不存在
                    nodeFile.mkdirs();//创建文件夹
                }
                fileName = System.currentTimeMillis() + file.getOriginalFilename().replaceAll(",", "");

                String fileFullName = nodeFile + "//" + fileName;

                Path path = Paths.get(fileFullName);
                Files.write(path, bytes);

                jsonObject.put("CODE",200);
                jsonObject.put("fileName",fileName);
                return jsonObject;
            }
        } catch (Exception e) {
            jsonObject.put("CODE",500);
            jsonObject.put("fileName", file.getOriginalFilename() + "上传失败");
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 指定位置开始写入文件
     *
     * @param tempFile 输入文件
     * @param outPath  输出文件的路径(路径+文件名)
     * @throws IOException
     */
    public static void randomAccessFile(String outPath, InputStream tempFile) throws IOException {
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;
        try {
            File dirFile = new File(outPath);
            //以读写的方式打开目标文件
            raFile = new RandomAccessFile(dirFile, "rw");
            raFile.seek(raFile.length());
            inputStream = new BufferedInputStream(tempFile);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                raFile.write(buf, 0, length);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (raFile != null) {
                    raFile.close();
                }
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    /**
     * 下載文件
     *
     * @param path     盘符地址
     * @param fileName 文件地址
     * @param response
     */
    public static void download(String path, String fileName, HttpServletResponse response) {
        DataInputStream in = null;
        OutputStream out = null;

        try {
            response.reset();// 清空输出流
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));// 设定输出文件头
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Content-type", "application-download");
            //输入流：本地文件路径
            in = new DataInputStream(new FileInputStream(new File(path + fileName)));
            //输出流
            out = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.reset();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static JSONObject deleteFile(String fileDown, String fileName) {
        JSONObject jsonObject = new JSONObject();
        File folder = new File(fileDown);
        File[] files = folder.listFiles();
        for(File file:files){
            if(file.getName().equals(fileName)){
                file.delete();
            }
        }
        jsonObject.put("CODE",200);
        jsonObject.put("fileName",fileName);
        return jsonObject;
    }


    /**
     * 判断文件是否存在，不存在则创建
     * @param dirPath
     */
    public static void fileIsExist(String dirPath){
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


}
