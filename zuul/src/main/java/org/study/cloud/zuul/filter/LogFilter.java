package org.study.cloud.zuul.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.study.cloud.zuul.util.HttpUtil;
import org.study.cloud.zuul.util.RequestWrapper;
import org.study.cloud.zuul.util.ResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 浏览器发请求，过滤器经过两次，一次真正请求/api-user/order/listOrder，有全部参数
 * 一次是图标/favicon.ico，没有参数
 * @author lipo
 * @version v1.0
 * @date 2019-10-25 10:34
 */
@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //过滤图标请求
        String requestUri = request.getRequestURI();
        if ("/favicon.ico".equals(requestUri)) {
            return;
        }

        RequestWrapper requestWrapper = new RequestWrapper(request);
        Map<String, String> header = HttpUtil.getHeaderMap(requestWrapper);
        String param = HttpUtil.getBodyString(requestWrapper);
        String ip = HttpUtil.getIpAddress(requestWrapper);

        log.info("requestUri = {}, requestMethod = {}, ip = {}, param = {}, header = {}", requestUri, request.getMethod(), ip, param, JSON.toJSONString(header));

        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
    }
}
