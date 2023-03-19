package net.therap.onlinestore.contoller;


import net.therap.onlinestore.command.Credentials;
import net.therap.onlinestore.entity.Category;
import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.Link;

import java.util.List;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
public class AuthenticationController {

    private static final String HOME_VIEW = "home";
    private static final String LOGIN = "login";
    private static final String LOGIN_URL = "login-page";
    private static final String LOGIN_VIEW = "login-page";
    private static final String LOGOUT_URL = "logout";

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(HOME_URL)
    public String showHome(@RequestParam(value = CATEGORY_ID, required = false) String categoryId, @RequestParam(value = TAG_ID, required = false) String tagId, ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemService.filter(categoryId, tagId));
        modelMap.put(CATEGORY_ID, categoryId);
        modelMap.put(TAG_ID, tagId);

        return HOME_VIEW;
    }

    @GetMapping(LOGIN_URL)
    public String showLoginPage(ModelMap modelMap) {
        modelMap.put(CREDENTIALS, new Credentials());
        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);

        return LOGIN_VIEW;
    }

    @PostMapping(LOGIN)
    public String login(@Valid @ModelAttribute(CREDENTIALS) Credentials credentials,
                        ModelMap modelMap,
                        HttpSession httpSession) throws Exception {

        if (userService.isValidCredential(credentials)) {
            httpSession.setAttribute(ACTIVE_USER, userService.findByEmail(credentials.getEmail()));

            return REDIRECT;
        }

        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);
        modelMap.put(INVALID_LOGIN, INVALID_LOGIN);

        return LOGIN_VIEW;
    }

    @GetMapping(LOGOUT_URL)
    public String logOut(HttpSession httpSession, Model model) {
        httpSession.removeAttribute(ACTIVE_USER);
        httpSession.invalidate();

        if (model.containsAttribute(ACTIVE_USER)) {
            model.asMap().remove(ACTIVE_USER);
        }

        return REDIRECT;
    }
}
