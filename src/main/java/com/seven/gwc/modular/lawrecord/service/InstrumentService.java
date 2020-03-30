package com.seven.gwc.modular.lawrecord.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.modular.lawrecord.data.instrument.dto.FilePathDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description : 文书录服务类
 *
 * @author : ZZL
 * @date : 2020-03-24
 */
public interface InstrumentService {

    /**
     * 生成文书-所有
     * @param id
     */
    void generateSystemInstrument(String id);


    /**
     * 根据修改内容修改部分文书
     * @param id
     * @param clazz
     */
    void generateInstrument(String id,Class<?> clazz);


    /**
     * 获取文书列表
     * @param id
     * @return
     */
    List<FilePathDTO>  getInstrument(String id);


    /**
     * 上传文书文件
     * @param file
     * @return
     */
    BaseResult uploadInstrument(String id,Integer code,String generateName,MultipartFile file);


    /**
     * 生成文案
     * @param id
     * @return
     */
    BaseResult<String> generateCase(String id);












}
