package com.seven.gwc.modular.lawrecord.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.gwc.core.base.GwcIdBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * description : 执法记录实体
 *
 * @author : ZZL
 * @date : 2020-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_record")
public class LawRecordEntity extends GwcIdBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 状态 */
    private Integer status;
    /** 案件类型（安全/生产--枚举） */
    private Integer lawType;
    /** 文书地址 无需要手写*/
    private String autoWritFilePath;
    /** 文书地址 需要手写 模板*/
    private String manualWritFilePath;
    /** 用户上传文书地址 需要手写 模板*/
    private String writFilePath;
    /** 主案由 */
    private Integer mainReason;
    /** 副案由 */
    private String secondReason;
    /**
     * 是否已经生成文书
     */
    private boolean writFlag;









}
