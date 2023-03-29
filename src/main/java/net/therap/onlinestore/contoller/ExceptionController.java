package net.therap.onlinestore.contoller;

import net.therap.onlinestore.exception.IllegalAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@ControllerAdvice
public class ExceptionController {

    private static final String ERROR_VIEW = "error";
    private static final String ILLEGAL_ACCESS = "illegalAccess";
    private static final String EXCEPTION_TYPE = "exceptionType";

    @ExceptionHandler(value = Exception.class)
    public String handleException() {

        return ERROR_VIEW;
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ModelAndView handleIllegalAccess() {

        ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);
        modelAndView.addObject(EXCEPTION_TYPE, ILLEGAL_ACCESS);

        return modelAndView;
    }
}
