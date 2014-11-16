package com.github.jroperto.gaebase.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/serverStatus")
public class ServerStatusController extends ControllerBase {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get() {
        return "server OK";
    }
}
