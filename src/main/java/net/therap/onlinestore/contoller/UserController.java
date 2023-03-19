package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.helper.UserTypeHelper;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.UserService;
import net.therap.onlinestore.util.Encryption;
import net.therap.onlinestore.validator.UserValidator;
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

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
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

    private static final String SHOPKEEPER_REDIRECT_URL = "admin/shopkeeper/list";
    private static final String DELIVERYMAN_REDIRECT_URL = "admin/deliveryMan/list";
    private static final String CUSTOMER_REDIRECT_URL = "admin/customer/list";
    private static final String USER_URL = "{user-type}/list";
    private static final String USER_VIEW = "user-list";
    private static final String USER_FORM_URL = "user/form";
    private static final String USER_FORM_SAVE_URL = "user/save";
    private static final String USER_FORM_VIEW = "user-form";
    private static final String USER_ID_PARAM = "userId";
    private static final String USER_TYPE_PATH_VAR = "user-type";
    private static final String USER_DELETE_URL = "user/delete";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserValidator userValidator;

    @InitBinder({USER, DELIVERYMAN})
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addValidators(userValidator);
        User user = (User) webDataBinder.getTarget();

        if (Objects.nonNull(user) && !user.isNew()) {
            webDataBinder.setDisallowedFields("password");
        }
    }

    @GetMapping(USER_URL)
    String showUserList(@PathVariable(USER_TYPE_PATH_VAR) String userType, ModelMap modelMap) {
        List<User> userList;

        if (DELIVERYMAN.equals(userType)) {
            userList = userService.finByUserType(UserType.DELIVERYMAN);
            modelMap.put(USER_TYPE, DELIVERYMAN);
        } else if (CUSTOMER.equals(userType)) {
            userList = userService.finByUserType(UserType.CUSTOMER);
            modelMap.put(USER_TYPE, CUSTOMER);
        } else {
            userList = userService.finByUserType(UserType.SHOPKEEPER);
            modelMap.put(USER_TYPE, SHOPKEEPER);
        }

        modelMap.put(USER_LIST, userList);
        modelMap.put(NAV_ITEM, USER);

        return USER_VIEW;
    }

    @GetMapping(USER_FORM_URL)
    String userForm(@RequestParam(value = USER_ID_PARAM, required = false) String userId,
                    ModelMap modelMap
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = nonNull(userId) ? userService.findById(Integer.parseInt(userId)) : new User();
        modelMap.put(USER, user);
        setupReferenceDataUserForm(modelMap, user);

        return USER_FORM_VIEW;
    }

    @PostMapping(USER_FORM_SAVE_URL)
    public String saveOrUpdateUser(@Valid @ModelAttribute(USER) User user,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataUserForm(modelMap, user);

            return USER_FORM_VIEW;
        }

        if (UserType.ADMIN.equals(user.getType())) {
            throw new IllegalAccessException();
        }

        if (user.isNew()) {
            user.setPassword(Encryption.getPBKDF2(user.getPassword()));
        }

        userService.saveOrUpdate(user);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));

        return REDIRECT + (UserType.SHOPKEEPER.equals(user.getType()) ? SHOPKEEPER_REDIRECT_URL :
                ((UserType.DELIVERYMAN.equals(user.getType())) ? DELIVERYMAN_REDIRECT_URL : CUSTOMER_REDIRECT_URL));
    }

    @PostMapping(USER_DELETE_URL)
    public String deleteUser(@RequestParam(USER_ID_PARAM) int userId, RedirectAttributes redirectAttributes) throws Exception {
        User user = userService.findById(userId);

        if (orderService.isUserInUse(user)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            userService.delete(userId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + (UserType.SHOPKEEPER.equals(user.getType()) ? SHOPKEEPER_REDIRECT_URL :
                ((UserType.DELIVERYMAN.equals(user.getType())) ? DELIVERYMAN_REDIRECT_URL : CUSTOMER_REDIRECT_URL));

    }

    private void setupReferenceDataUserForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(USER_TYPE_LIST, UserTypeHelper.getUserTypeSelectList());
        modelMap.put(NAV_ITEM, USER);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }
    }
}
