package com.seven.gwc.modular.path.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : 海图渔区实体
 *
 * @author: QQC
 * @date: 2020-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("law_fish_area")
public class FishAreaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID
    private String id;

    // 名称
    private String name;

    // 区域坐标1 经度
    private String point1Lon;

    // 区域坐标1 纬度
    private String point1Lat;

    // 区域坐标2 经度
    private String point2Lon;

    // 区域坐标2 纬度
    private String point2Lat;

    // 区域坐标3 经度
    private String point3Lon;

    // 区域坐标3 纬度
    private String point3Lat;

    // 区域坐标4 经度
    private String point4Lon;

    // 区域坐标4 纬度
    private String point4Lat;

}
