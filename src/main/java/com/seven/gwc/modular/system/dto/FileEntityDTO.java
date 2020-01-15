package com.seven.gwc.modular.system.dto;

import lombok.Data;

/**
 * description:
 *
 * @author : GD
 * @date : 2019-9-30 10:11
 */
@Data
public class FileEntityDTO {
    /** 附件名称 */
    private String name;
    /** 附件url */
    private String url;
    /** 附件后缀 */
    private String suffix;
    /** 附件大小 */
    private Long fileSize;
}
