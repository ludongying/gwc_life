package com.seven.gwc.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GD
 * @Description: Entity基类
 * @Date: 2019/10/21 16:50
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 请求参数
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }

        return params;
    }
}
