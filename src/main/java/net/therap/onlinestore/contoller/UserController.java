package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.AccessType;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.helper.UserHelper;
import net.therap.onlinestore.service.UserService;
import net.therap.onlinestore.util.Encryption;
import net.therap.onlinestore.util.Util;
import net.therap.onlinestore.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/6/23
 */
@Controller
@RequestMapping(ADMIN_BASE_URL)
@SessionAttributes(USER)
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    private static final String SHOPKEEPER_REDIRECT_URL = "admin/shopkeeper/list";
    private static final String DELIVERYMAN_REDIRECT_URL = "admin/deliveryMan/list";
    private static final String CUSTOMER_REDIRECT_URL = "admin/customer/list";
    private static final String USER_URL = "{user-type}/list";
    private static final String USER_VIEW = "user/user-list";
    private static final String USER_FORM_URL = "user/form";
    private static final String USER_FORM_SAVE_URL = "user/save";
    private static final String USER_FORM_VIEW = "user/user-form";
    private static final String USER_ID_PARAM = "userId";
    private static final String USER_TYPE_PATH_VAR = "user-type";
    private static final String USER_DELETE_URL = "user/delete";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserHelper userHelper;

    @InitBinder({USER, DELIVERYMAN})
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addValidators(userValidator);
        webDataBinder.setDisallowedFields("id", "access_status", "version", "created_at", "updated_at");
        User user = (User) webDataBinder.getTarget();

        if (Objects.nonNull(user) && !user.isNew()) {
            webDataBinder.setDisallowedFields("password");
        }
    }

    @GetMapping(USER_URL)
    String showUserList(@PathVariable(USER_TYPE_PATH_VAR) String userType,
                        ModelMap modelMap,
                        HttpSession httpSession) {

        userHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.VIEW_ALL);
        userHelper.populateUserListModels(modelMap, userType);

        return USER_VIEW;
    }

    @GetMapping(USER_FORM_URL)
    String userForm(@RequestParam(value = USER_ID_PARAM, required = false) String userId,
                    ModelMap modelMap,
                    HttpSession httpSession) {

        userHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.FORM_LOAD);

        User user = nonNull(userId) ? userService.findById(Integer.parseInt(userId)) : new User();

        modelMap.put(USER, user);
        userHelper.populateUserFormData(modelMap, user);

        return USER_FORM_VIEW;
    }

    @PostMapping(USER_FORM_SAVE_URL)
    public String saveOrUpdateUser(@Valid @ModelAttribute(USER) User user,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   SessionStatus sessionStatus,
                                   HttpSession httpSession,
                                   RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, InvalidKeySpecException {

        userHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.SAVE);
        userHelper.checkUserAllowedUserType(user);

        if (bindingResult.hasErrors()) {
            userHelper.populateUserFormData(modelMap, user);

            return USER_FORM_VIEW;
        }

        if (user.isNew()) {
            user.setPassword(Encryption.getPBKDF2(user.getPassword()));
        }

        userService.saveOrUpdate(user);

        logger.info("Saved user: " + user.getId());

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));

        return REDIRECT + (UserType.SHOPKEEPER.equals(user.getType()) ? SHOPKEEPER_REDIRECT_URL :
                ((UserType.DELIVERYMAN.equals(user.getType())) ? DELIVERYMAN_REDIRECT_URL : CUSTOMER_REDIRECT_URL));
    }

    @PostMapping(USER_DELETE_URL)
    public String deleteUser(@RequestParam(USER_ID_PARAM) int userId,
                             HttpSession httpSession,
                             RedirectAttributes redirectAttributes) {

        userHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.DELETE);

        User user = userService.findById(userId);

        logger.info("Deleted user: " + userId);

        userHelper.populateDeleteRedirectMessage(redirectAttributes, user, userId);

        return REDIRECT + (UserType.SHOPKEEPER.equals(user.getType()) ? SHOPKEEPER_REDIRECT_URL :
                ((UserType.DELIVERYMAN.equals(user.getType())) ? DELIVERYMAN_REDIRECT_URL : CUSTOMER_REDIRECT_URL));

    }
}
