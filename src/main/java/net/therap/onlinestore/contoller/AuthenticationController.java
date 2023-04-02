package net.therap.onlinestore.contoller;


import net.therap.onlinestore.command.Credentials;
import net.therap.onlinestore.service.UserService;
import net.therap.onlinestore.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
public class AuthenticationController {

    private static final org.apache.log4j.Logger logger = Logger.getLogger(CategoryController.class);

    private static final String LOGIN_VIEW = "login-page";
    private static final String LOGOUT_URL = "logout";

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(LOGIN_PAGE_URL)
    public String showLoginPage(ModelMap modelMap) {
        modelMap.put(CREDENTIALS, new Credentials());
        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);

        return LOGIN_VIEW;
    }

    @GetMapping(LOGOUT_URL)
    public String logOut(HttpSession httpSession, Model model) {
        logger.info("Logged out user: " + Util.getActiveUser(httpSession).getId());
        httpSession.removeAttribute(ACTIVE_USER);
        httpSession.invalidate();

        if (model.containsAttribute(ACTIVE_USER)) {
            model.asMap().remove(ACTIVE_USER);
        }

        return REDIRECT;
    }

    @PostMapping(LOGIN_URL)
    public String login(@Valid @ModelAttribute(CREDENTIALS) Credentials credentials,
                        ModelMap modelMap,
                        HttpSession httpSession) throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (userService.isValidCredential(credentials)) {
            httpSession.setAttribute(ACTIVE_USER, userService.findByEmail(credentials.getEmail()));

            return REDIRECT;
        }

        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);
        modelMap.put(INVALID_LOGIN, INVALID_LOGIN);
        logger.info("Logged in user: " + Util.getActiveUser(httpSession).getId());

        return LOGIN_VIEW;
    }
}
