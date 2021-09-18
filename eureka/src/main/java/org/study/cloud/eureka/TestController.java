package org.study.cloud.eureka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    //http://localhost:9050/test/hello
    @RequestMapping("/hello")
    public Object hello() {
        return "hello";
    }
}
