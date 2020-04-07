package com.seven.gwc.modular.path.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 禁渔区实体
 *
 * @author: QQC
 * @date: 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_fish_forbidden_area")
public class FishForbiddenAreaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    private String id;

    // 禁渔区编号
    private String areaId;

    // 禁渔区名称
    private String areaName;

    // 序号
    private Integer num;

    // 经度（度）
    private String lon;

    // 纬度（度）
    private String lat;

}
