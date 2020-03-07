package com.seven.gwc.modular.lawrecord.data.file;

import com.seven.gwc.core.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : zzl
 * @Date: 2020-03-03
 * @description : 文件管理 负责 上传 删除 下载
 */
@Slf4j
public class FileManager {

    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String dirPath;


    /**
     * 上传单个文件
     * @param file
     * @return
     */
    public BaseResult uploadFile(MultipartFile file){
        BaseResult result=new BaseResult();
        if (Objects.nonNull(file) && !file.isEmpty()) {
            try {
                FileUtils.generateDir(dirPath);
                byte[] bytes= file.getBytes();
                String  fileName = System.currentTimeMillis() + file.getOriginalFilename().replaceAll(",", "");
                String fileFullName = dirPath + FileUtils.file_sep+ fileName;
                Path path = Paths.get(fileFullName);
                try{
                    Files.write(path, bytes);
                    FileBase fileBase=new FileBase();
                    fileBase.setPath(fileFullName);
                    result.setContent(fileBase);
                    result.setSuccess(true);
                }catch (IOException e){
                    result.setMessage("文件读取异常");
                }
            }catch (IOException e){
                result.setMessage("文件存储异常");
            }
        }else{
            result.setMessage("文件不能为空");
        }
        return result;
    }
    /**
     * 删除单个文件
     * @param realPath
     * @return
     */
    public BaseResult deleteFile(String realPath){
        BaseResult result=new BaseResult();
        File file = new File(realPath);
        if(file.exists() &&file.isFile()){
            file.delete();
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setMessage("无此文件");
        }
        return result;
    }
    /**
     * 下载文件
     * @param filePath 文件路径
     */
    public void download(String filePath,HttpServletResponse resp) {
        File file=new File(filePath);
        if(!FileUtils.existFile(filePath)){
          return;
        }
        String fileName=file.getName();
        resp.reset();// 清空输出流
        resp.setCharacterEncoding("UTF-8");
        // 设定输出文件头
        resp.setHeader("Content-disposition", "attachment; filename=" +
                new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Content-type", "application-download");
        try {
            //输入流：本地文件路径
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            //输出流
            OutputStream out = resp.getOutputStream();
            try{
                //输出文件
                int bytes;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.flush();
            }catch (Exception eo){
                log.error(">>>>>文件下载输出异常");
            }
            out.close();
            in.close();
            out.close();
        }catch (Exception e){
            log.error(">>>>>文件流生成关闭异常");
        }
    }



}
