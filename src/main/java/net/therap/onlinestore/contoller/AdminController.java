package net.therap.onlinestore.contoller;

import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/6/23
 */
@Controller
@RequestMapping(ADMIN_BASE_URL)
public class AdminController {

    private static final String HOME_VIEW = "home";

    @Autowired
    private UserService userService;

    @GetMapping(HOME_URL)
    String showHome(HttpSession httpSession){
        httpSession.setAttribute(ACTIVE_USER, userService.findById(10));

        return HOME_VIEW;
    }
}
