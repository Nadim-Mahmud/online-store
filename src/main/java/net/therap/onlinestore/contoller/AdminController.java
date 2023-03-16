package net.therap.onlinestore.contoller;

import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    private ItemService itemService;

    @GetMapping(HOME_URL)
    String showHome(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }
}
