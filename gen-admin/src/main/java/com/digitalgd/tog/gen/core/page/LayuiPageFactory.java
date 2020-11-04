package com.digitalgd.tog.gen.core.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Layui Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class LayuiPageFactory {

    /**
     * 获取layui table的分页参数
     *
     * @author fengshuonan
     * @Date 2019/1/25 22:13
     */
    public static Page defaultPage() {

        HttpServletRequest request = null;

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        } else {
            return new Page(1, 20);
        }

        //每页多少条数据
        int limit = Integer.valueOf(request.getParameter("limit"));

        //第几页
        int page = Integer.valueOf(request.getParameter("page"));

        return new Page(page, limit);
    }

    /**
     * 创建layui能识别的分页响应参数
     *
     * @author fengshuonan
     * @Date 2019/1/25 22:14
     */
    public static LayuiPageInfo createPageInfo(Page page) {
        LayuiPageInfo result = new LayuiPageInfo();
        result.setCount(page.getTotal());
        result.setData(page.getRecords());
        return result;
    }
}
