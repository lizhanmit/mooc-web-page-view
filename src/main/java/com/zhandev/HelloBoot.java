package com.zhandev;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * For testing Spring Boot web app works well.
 */
@RestController
public class HelloBoot {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi() {
        return "Hi";
    }
}
