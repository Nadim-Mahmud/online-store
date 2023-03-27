package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.AccessType;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class UserHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageSource messageSource;

    public void checkAccess(User user, AccessType accessType) throws IllegalAccessException {

        if (Objects.isNull(user)) {
            throw new IllegalAccessException();
        }

        if (Arrays.asList(AccessType.VIEW_ALL, AccessType.FORM_LOAD, AccessType.SAVE, AccessType.DELETE).contains(accessType) && !UserType.ADMIN.equals(user.getType())) {
            throw new IllegalAccessException();
        }
    }

    public void populateUserListModels(ModelMap modelMap, String userType) {
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
    }

    public void populateUserFormData(ModelMap modelMap, User user) {
        modelMap.put(USER_TYPE_LIST, UserTypeHelper.getUserTypeSelectList());
        modelMap.put(NAV_ITEM, USER);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }
    }

    public void populateDeleteRedirectMessage(RedirectAttributes redirectAttributes, User user, int userId) throws Exception {

        if (orderService.isUserInUse(user)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            userService.delete(userId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }
    }

    public static boolean isInvalidPassword(String cell) {

        if (Objects.isNull(cell)) {
            return true;
        }

        if (Pattern.compile(PASSWORD_PATTERN).matcher(cell).find()) {
            return false;
        }

        return true;
    }
}
