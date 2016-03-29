package com.dingup.onlinetest.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanggavin on 16/3/3.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model){
        return "index";
    }
}
