package org.study.cloud.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    //http://localhost:9050/test/hello
    @RequestMapping("/hello")
    public Object hello(HttpServletRequest request) {
        log.info(request.getRequestURL() + "-hello");
        return "hello";
    }
}
