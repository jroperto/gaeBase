package com.github.jroperto.gaebase.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    /**
     * Handle all other exceptions that end up back at the controller.
     *
     * @param throwable
     * @return a ModelAndView that contains the stack trace as an html comment
     */
    @ExceptionHandler(Throwable.class)
    public String handleExceptions(Throwable throwable) {
        return "error";
    }
}
