package net.therap.onlinestore.contoller;

import net.therap.onlinestore.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@Controller
public class ErrorController {

    private static final String ERROR_VIEW = "error";

    @GetMapping("/errors")
    public String handleError(HttpServletRequest request, ModelMap modelMap) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Objects.nonNull(status)) {
            modelMap.put(Constants.ERROR_CODE, Integer.valueOf(status.toString()));
        }

        return ERROR_VIEW;
    }
}
