package com.baeldung.spring.session.tomcatex;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tschi on 11/12/2016.
 */
@RestController
public class TestController {

    @RequestMapping
    public String helloDefault() {
        return "hello default";
    }

    @RequestMapping("/tomcat")
    public String helloTomcat() {
        return "hello tomcat";
    }

    @RequestMapping("/tomcat/admin")
    public String helloTomcatAdmin() {
        return "hello tomcat admin";
    }
}
