package net.therap.onlinestore.contoller;

import net.therap.onlinestore.command.Password;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.helper.ProfileHelper;
import net.therap.onlinestore.service.UserService;
import net.therap.onlinestore.util.Encryption;
import net.therap.onlinestore.util.Util;
import net.therap.onlinestore.validator.PasswordValidator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import static net.therap.onlinestore.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 3/8/23
 */
@Controller
@SessionAttributes({PASSWORD, USER})
public class ProfileController {

    private static final Logger logger = Logger.getLogger(ProfileController.class);

    private static final String REGISTRATION_FORM_REDIRECT_URL = "login-page";
    private static final String REGISTRATION_FORM_VIEW = "profile/registration-form";
    private static final String UPDATE_PASSWORD_URL = "update-password";
    private static final String UPDATE_PASSWORD_VIEW = "profile/password-form";
    private static final String SAVE_PASSWORD_URL = "/update-password/update";
    private static final String UPDATE_PROFILE_URL = "update-profile";
    private static final String UPDATE_PROFILE_VIEW = "profile/profile-update-form";
    private static final String SAVE_PROFILE_URL = "/update-profile/update";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ProfileHelper profileHelper;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.setDisallowedFields("id", "access_status", "version", "created_at", "updated_at");
    }

    @InitBinder(PASSWORD)
    public void passwordBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordValidator());
    }

    @InitBinder(USER)
    public void profileBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("password", "confirmPassword", "type");
        webDataBinder.addValidators(userValidator);
    }

    @InitBinder(CUSTOMER)
    public void registrationBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("type");
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping(REGISTRATION_URL)
    public String showRegistrationPage(ModelMap modelMap) {
        modelMap.put(CUSTOMER, new User());

        return REGISTRATION_FORM_VIEW;
    }

    @GetMapping(UPDATE_PASSWORD_URL)
    public String showPasswordUpdatePage(ModelMap modelMap, HttpSession httpSession) {
        Password password = new Password();
        password.setStoredUserPassword(Util.getActiveUser(httpSession).getPassword());
        modelMap.put(PASSWORD, password);

        return UPDATE_PASSWORD_VIEW;
    }

    @GetMapping(UPDATE_PROFILE_URL)
    String updateProfileFrom(ModelMap modelMap, HttpSession httpSession) {
        modelMap.put(USER, userService.findById(Util.getActiveUser(httpSession).getId()));
        modelMap.put(PROFILE_UPDATE_PAGE, true);

        return UPDATE_PROFILE_VIEW;
    }

    @PostMapping(REGISTER_URL)
    public String saveCustomer(@Valid @ModelAttribute(CUSTOMER) User customer,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, InvalidKeySpecException {

        profileHelper.updateAccessBlock(customer);

        if (bindingResult.hasErrors()) {
            return REGISTRATION_FORM_VIEW;
        }

        customer.setPassword(Encryption.getPBKDF2(customer.getPassword()));
        customer.setType(UserType.CUSTOMER);
        userService.saveOrUpdate(customer);
        logger.info("New customer registration: id-" + customer.getId() + " name-" + customer.getFirstName());
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.registration", null, Locale.getDefault()));

        return REDIRECT + REGISTRATION_FORM_REDIRECT_URL;
    }

    @PostMapping(SAVE_PASSWORD_URL)
    String savePassword(@SessionAttribute(ACTIVE_USER) User user,
                        @Valid @ModelAttribute(PASSWORD) Password password,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                        RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            return UPDATE_PASSWORD_VIEW;
        }

        user.setPassword(Encryption.getPBKDF2(password.getNewPassword()));
        userService.saveOrUpdate(user);
        logger.info("Updated password user: id-" + user.getId());
        httpSession.setAttribute(ACTIVE_USER, userService.findById(user.getId()));
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.password.updated", null, Locale.getDefault()));

        return REDIRECT + UPDATE_PASSWORD_URL;
    }

    @PostMapping(SAVE_PROFILE_URL)
    String updateProfile(@Valid @ModelAttribute(USER) User user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpSession httpSession) {

        profileHelper.checkAccess(Util.getActiveUser(httpSession), user);

        if (bindingResult.hasErrors()) {
            return UPDATE_PROFILE_VIEW;
        }

        userService.saveOrUpdate(user);
        logger.info("Updated profile: id-" + user.getId());
        httpSession.setAttribute(ACTIVE_USER, userService.findById(user.getId()));
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.profile.updated", null, Locale.getDefault()));

        return REDIRECT + UPDATE_PROFILE_URL;
    }
}
