package com.seven.gwc.core.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : GD
 * description : Entity基类
 * @date : 2019/10/21 16:50
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  请求参数
     */
    @TableField(exist = false)
    protected Map<String, Object> params;

    /**
     * 同步标识 true:已经同步，false:未同步
     */
    protected boolean synFlag;

    public Map<String, Object> getParams() {
            if (params == null) {
            params = new HashMap<>();
        }

        return params;
    }

}
