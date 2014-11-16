package com.github.jroperto.gaebase.web.controller.api;


import com.github.jroperto.gaebase.web.controller.ControllerBase;

public class ApiControllerBase extends ControllerBase {

    public static final String API_VERSION = "1";
    public static final String BASE_API_PATH = "/api/{version:\\d*}";
}
