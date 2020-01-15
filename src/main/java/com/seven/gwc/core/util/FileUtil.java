package com.seven.gwc.core.util;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件上传工具类
 */
public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
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


}
