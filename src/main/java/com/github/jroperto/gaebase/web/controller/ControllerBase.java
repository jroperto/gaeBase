package com.github.jroperto.gaebase.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

public class ControllerBase {

    private static final Logger LOGGER = Logger.getLogger(ControllerBase.class.getName());


    /**
     * Handle all other exceptions that end up back at the controller.
     *
     * @param throwable Exception thrown
     * @return An error status with the error message
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleExceptions(Throwable throwable) {
        LOGGER.severe("Exception not handled, throwing internal server error - " + throwable.getMessage());

        return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
