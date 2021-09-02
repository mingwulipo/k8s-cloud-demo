package org.study.cloud.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.study.cloud.zuul.util.Result;
import org.study.cloud.zuul.util.ResultCodeEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2019\8\17 0017.
 */
@Component
public class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("utf-8");

        //跨域
        response.setHeader("Content-type", "text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,OPTION");
        response.setHeader("Access-Control-Max-Age","3600");
        //前端header中要传递Authorization, 这里必须加上, 使用不方便
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Referer, Content-Type, Accept,option,Authorization,uid");

        // 清除客户端缓存
        response.setHeader("Pragma","No-Cache");
        response.setHeader("Cache-Control","No-Cache");
        response.setDateHeader("Expires", 0);

        Result result = Result.from(ResultCodeEnum.SUCCESS);

        String method = request.getMethod();

        if("options".equalsIgnoreCase(method)){

            OutputStream out = null;

            try {
                out = response.getOutputStream();

                result.setData("通过OPTIONS请求");
                String jsonData = JSONObject.toJSONString(result);

                out.write(jsonData.getBytes(request.getCharacterEncoding()));

                return;
            } catch (IOException e) {
                throw e;
            } finally {
                if(out != null){
                    out.close();
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
