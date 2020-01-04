package com.seven.gwc.modular.path.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * description : path实体
 *
 * @author: QQC
 * @date: 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pointlist")
public class PointlistEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 执法船只编号
    private String shipId;

    //  本船经度
    private String lon;

    //   本船纬度
    private String lat;

    // 本船航向
    private String course;

    // 本船航速
    private String speed;

    // 记录该地点的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 记录该数据的数据源，北斗/gps
    private String deviceSource;

}
