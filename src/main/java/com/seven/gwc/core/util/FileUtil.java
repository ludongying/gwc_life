package com.seven.gwc.core.util;


import com.alibaba.fastjson.JSONObject;
import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传工具类
 */
@Slf4j
public class FileUtil {


    public static JSONObject uploadFile(String fileDown, @RequestParam("file") MultipartFile file, String ip, String type) {
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

                if (type.equals("video")) {
                    String pictureName = ip + FileUtils.file_sep + fileName.substring(0, fileName.lastIndexOf(".")) + ".jpg";
                    FileUtil.fetchFrame(fileFullName, fileDown, fileName.substring(0, fileName.lastIndexOf(".")) + ".jpg");
                    jsonObject.put("videoName", fileName);
                    jsonObject.put("fileName", pictureName);
                } else {
                    jsonObject.put("fileName", ip + FileUtils.file_sep + fileName);
                }
                jsonObject.put("CODE", 200);

                return jsonObject;
            }
        } catch (Exception e) {
            jsonObject.put("CODE", 500);
            jsonObject.put("fileName", file.getOriginalFilename() + "上传失败");
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 截取视频帧数
     * @param videoFile 视频物理路径  (D:\myfile\file//1586939529095短视频.mp4)
     * @param frameFile 保存图片文件路径  (D:\myfile\file\)
     * @param fileName 保存之后的文件名  (1586939529095短视频.jpg)
     * @throws Exception
     */
    public static void fetchFrame(String videoFile, String frameFile, String fileName) throws Exception {
        File targetFile = new File(frameFile + "/" + fileName);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            f = ff.grabFrame();
            if ((i > 3) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        ff.flush();
        ff.stop();
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
            in = new DataInputStream(new FileInputStream(new File(path + "/" + fileName)));
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

    /**
     * 判断文件是否存在，不存在则创建
     *
     * @param dirPath
     */
    public static void fileIsExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    /**
     * @param path
     * @return String
     * @description 将文件转base64字符串
     * @date 2018年3月20日
     * @author changyl
     * File转成编码成BASE64
     */
    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * BASE64解码成File文件
     * baseStr = baseStr.replace("data:image/jpeg;base64,", "");
     * base64解密部分乱码问题（“+” 号，在urlecode编码中会被解码成空格）
     */
    public static String base64ToFile(String destPath, String base64) {
        //创建文件目录
        File dir = new File(destPath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            String suf = "";
            String str = base64;
            if (str.indexOf("jpeg;base64") > 0) {
                suf = ".jpeg";
                str = str.replace("data:image/jpeg;base64,", "");
            } else if (str.indexOf("png;base64") > 0) {
                suf = ".png";
                str = str.replace("data:image/png;base64,", "");
            } else if (str.indexOf("jpg;base64") > 0) {
                suf = ".jpg";
                str = str.replace("data:image/jpg;base64,", "");
            }
            String path = ToolUtil.getUUIDremoveBars() + suf;
            base64ToFile(destPath, str, path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * BASE64解码成File文件
     * baseStr = baseStr.replace("data:image/jpeg;base64,", "");
     * base64解密部分乱码问题（“+” 号，在urlecode编码中会被解码成空格）
     */
    public static String base64ToFileName(String destPath, String base64, String fileName) {
        //创建文件目录
        File dir = new File(destPath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            String suf = "";
            String str = base64;
            if (str.indexOf("jpeg;base64") > 0) {
                suf = ".jpeg";
                str = str.replace("data:image/jpeg;base64,", "");
            } else if (str.indexOf("png;base64") > 0) {
                suf = ".png";
                str = str.replace("data:image/png;base64,", "");
            } else if (str.indexOf("jpg;base64") > 0) {
                suf = ".jpg";
                str = str.replace("data:image/jpg;base64,", "");
            }
            String path = fileName + suf;
            base64ToFile(destPath, str, path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * BASE64解码成File文件
     * baseStr = baseStr.replace("data:image/jpeg;base64,", "");
     * base64解密部分乱码问题（“+” 号，在urlecode编码中会被解码成空格）
     */
    public static void base64ToFile(String destPath, String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(destPath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> base64ToFiles(String destPath, List<String> fileList) {
        List<String> fileNames = new ArrayList<>();
        if (Objects.nonNull(fileList) && !fileList.isEmpty()) {
            for (int i = 0; i < fileList.size(); i++) {
                String s = base64ToFile(destPath, fileList.get(i));
                if (s == null) {
                    log.error("第" + i + "个文件存储失败。");
                }
            }
        }
        return fileNames;
    }


    /**
     * 删除文件
     *
     * @param fileDown 文件路径
     * @param fileName 文件名
     * @return
     */
    public static JSONObject deleteFile(String fileDown, String fileName) {
        JSONObject jsonObject = new JSONObject();
        File folder = new File(fileDown);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.getName().equals(fileName)) {
                file.delete();
            }
        }
        jsonObject.put("CODE", 200);
        jsonObject.put("fileName", fileName);
        return jsonObject;
    }

    //文件转流预览
    public static ResponseEntity<Resource> previewFile(String filePath, HttpServletResponse response) {
        DataInputStream in = null;
        OutputStream out = null;
        try {
            //输入流：本地文件路径
            in = new DataInputStream(
                    new FileInputStream(new File(filePath)));
            //输出流
            out = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.flush();     //清空缓存的内容
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
        return null;
    }

}
