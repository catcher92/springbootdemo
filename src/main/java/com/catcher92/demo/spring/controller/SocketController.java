package com.catcher92.demo.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SocketController {

    @GetMapping("/webSocket")
    public ModelAndView socket() {
        return new ModelAndView("/webSocket");
    }

}
