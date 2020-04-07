package com.seven.gwc.core.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : GD
 * description :
 * @date : 2019/9/12 11:34
 */
@Data
public class BaseResultPage<T extends Object> extends BaseResult {

    private long count;
    private List data;

    public static Page defaultPage() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        int limit = Integer.valueOf(request.getParameter("limit"));//每页多少条数据
        int page = Integer.valueOf(request.getParameter("page"));//第几页
        return new Page(page, limit);
    }

    public BaseResultPage<T> createPage(PageInfo pageInfo) {
        this.setCode(0);
        this.setCount(pageInfo.getTotal());
        this.setData(pageInfo.getList());
        return this;
    }

    public BaseResultPage<T> treeData(List data) {
        this.setData(data);
        this.setCode(0);
        return this;
    }
}
