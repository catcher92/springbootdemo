package com.catcher92.demo.spring.bootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by caoxuedong on 2017/2/27.
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"", "index"})
    public String index(HttpServletRequest request, Model model) {
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute("uuid");
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        session.setAttribute("uuid", uuid);
        model.addAttribute("message", ip+":"+session.getId()+":"+uuid);
        return "index";
    }

}
