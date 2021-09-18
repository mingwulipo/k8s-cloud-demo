package org.study.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    //http://localhost:9050/test/hello
    @RequestMapping("/hello")
    public Object hello() {
        log.info("hello");
        return "hello";
    }
}
