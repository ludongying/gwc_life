package com.seven.gwc.modular.lawrecord.data.file;

import com.drew.imaging.*;
import com.drew.metadata.*;
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

    /**
     * D:\myfile\file\
     */
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String dirPath;
    /**
     * http://192.168.18.73:88/
     */
    @Value("${server.ip}")
    private String ip;



    /**
     * 上传单个文件
     * @param file
     * @return
     */
    public BaseResult<FileBase> uploadFile(MultipartFile file){
       return uploadFile(file,String.valueOf(System.currentTimeMillis()),null);
    }

    /**
     * 上传单个文件
     * @param file
     * @return dir  /dir
     */
    public BaseResult<FileBase> uploadOrgFile(MultipartFile file,String dir){
        return uploadFile(file,"",dir);
    }

    /**
     * 上传单个文件
     * @param file
     * @return
     */
    private BaseResult<FileBase> uploadFile(MultipartFile file,String time,String dir){
        BaseResult<FileBase> result=new BaseResult();
        if (Objects.nonNull(file) && !file.isEmpty()) {
            try {
                if(Objects.nonNull(dir)){
                    FileUtils.generateDir(dirPath+dir);
                }else{
                    FileUtils.generateDir(dirPath);
                }
                byte[] bytes= file.getBytes();
                String  fileName =time+ file.getOriginalFilename().replaceAll(",", "");
                String fileFullName;
                if(Objects.nonNull(dir)){
                    fileFullName = dirPath +dir+ FileUtils.file_sep+ fileName;
                }else{
                    fileFullName = dirPath + fileName;
                }

                Path path = Paths.get(fileFullName);
                try{
                    Files.write(path, bytes);

                    FileBase fileBase=new FileBase();
                    fileBase.setPath(fileFullName);
                    if(Objects.nonNull(dir)){
                        fileBase.setUrl(ip+dir+FileUtils.file_sep+ fileName);
                    }else{
                        fileBase.setUrl(ip+ fileName);
                    }
                    fileBase.setType(FileUtils.getFileType(fileName).getCode());
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


    public static void printImageTags(File file) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //标签名
                String tagName = tag.getTagName();
                //标签信息
                String desc = tag.getDescription();
                if (tagName.equals("Image Height")) {
                    System.out.println("图片高度: " + desc);
                } else if (tagName.equals("Image Width")) {
                    System.out.println("图片宽度: " + desc);
                } else if (tagName.equals("Date/Time Original")) {
                    System.out.println("拍摄时间: " + desc);
                } else if (tagName.equals("GPS Latitude")) {
                    System.err.println("纬度 : " + desc);
                    //                    System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
                } else if (tagName.equals("GPS Longitude")) {
                    System.err.println("经度: " + desc);
                    //System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
                }
            }
        }
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

    /**
     * 下载文件
     * @param name 文件路径
     */
    public void downloadTemplate(String name,HttpServletResponse resp) {
           download(FileUtils.getStaticPath()+FileUtils.file_sep+FileUtils.template_path+FileUtils.file_sep+name,resp);
}

    /**
     * 加载文件列表
     * @param paths
     * @return
     */
    public List<FileData> listFile(Collection<String> paths){
        List<FileData> list=new ArrayList<>();
        if(Objects.nonNull(paths) && !paths.isEmpty()){
            paths = paths.stream().map(String::trim).filter(p->!p.isEmpty()).filter(FileUtils::existFile).collect(Collectors.toList());
            if(!paths.isEmpty()){
                for (String path : paths) {
                    FileData data = FileUtils.getByPath(path);
                    data.setUrl(ip+data.getRealName());
                    list.add(data);
                }
            }
        }
        return list;
    }
    /**
     * 加载文件列表--单个字段存路径 ","分隔路径
     * @param pathStr
     * @return
     */
    public List<FileData> listFile(String pathStr){
        if(Objects.nonNull(pathStr) && !pathStr.trim().isEmpty()){
            String[] list = pathStr.split(FileUtils.file_2_file_sep);
            if(list.length > 0){
                return listFile(Arrays.asList(list));
            }
        }
        return listFile(new ArrayList<>());
    }






}
