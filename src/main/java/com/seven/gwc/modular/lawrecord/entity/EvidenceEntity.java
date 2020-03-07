package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seven.gwc.core.base.GwcIdBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 证据实体
 *
 * @author : ZZL
 * @date : 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record_evidence")
public class EvidenceEntity extends GwcIdBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 执法记录id */
    private String recordId;

    /** 内容 */
    private String evidenceContent;

    /** 日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date evidenceDate;

    /** 路径 */
    private String evidenceFilePath;




}
