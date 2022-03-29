package com.sn.manageservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 向南
 * @date 2021/12/22 14:48
 * @description ss
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
//    private static Logger log = Logger.getLogger(TestController.class.getClass());

    @RequestMapping(value = "api",method = RequestMethod.GET)
    @ResponseBody
    public String testApi(){
        log.info("sass:{}","ss");
        return "hello world";
    }
    @GetMapping(value = "/hello")
    public String hello(Model model) {
        String name = "jiangbei";
        model.addAttribute("name", name);
        return "hello";
    }

}
