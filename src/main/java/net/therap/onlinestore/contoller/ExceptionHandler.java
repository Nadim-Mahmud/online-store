package net.therap.onlinestore.contoller;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@ControllerAdvice
public class ExceptionHandler {

    private static final String ERROR_VIEW = "error";

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public String handleError() {
        return ERROR_VIEW;
    }
}
