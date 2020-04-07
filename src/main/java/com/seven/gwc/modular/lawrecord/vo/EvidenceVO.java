package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.core.util.DateTimeUtil;
import com.seven.gwc.modular.lawrecord.entity.EvidenceEntity;
import lombok.Data;

/**
 * @author : zzl
 * @Date: 2020-03-04
 * @description : 证据vo
 */
@Data
public class EvidenceVO extends EvidenceEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 时间
     */
    private String evidenceTime;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 设置登录用户
     * @param userId
     */
    public void setUserId(String userId){
        this.userId=userId;
        this.init(userId);
    }

    /**
     * 时间设置
     */
    public void parseTime(){
        this.setEvidenceDate(DateTimeUtil.parse2Date(evidenceTime,"yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 路径设置
     */
/*    public void parsePath(){
        if(Objects.nonNull(path) && !path.trim().isEmpty()){
            List<FileBase> fileBases = JSON.parseArray(path, FileBase.class);
            if(Objects.nonNull(fileBases) && !fileBases.isEmpty()){
                String str = fileBases.stream().map(FileBase::getPath).collect(Collectors.joining(",", ",", ","));
                this.setEvidenceFilePath(str);
            }
        }
    }*/







}
