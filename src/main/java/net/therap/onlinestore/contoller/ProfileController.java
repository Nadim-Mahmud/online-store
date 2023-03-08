package net.therap.onlinestore.contoller;

import net.therap.onlinestore.command.Password;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.UserService;
import net.therap.onlinestore.util.Encryption;
import net.therap.onlinestore.validator.PasswordValidator;
import net.therap.onlinestore.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Locale;

import static net.therap.onlinestore.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 3/8/23
 */
@Controller
@SessionAttributes({PASSWORD, USER})
public class ProfileController {

    private static final String REGISTRATION_FORM_REDIRECT_URL = "login-page";
    private static final String REGISTRATION_FORM_URL = "registration";
    private static final String REGISTRATION_FORM_VIEW = "registration-form";
    private static final String REGISTRATION_FORM_SAVE_URL = "registration/save";

    private static final String UPDATE_PASSWORD_URL = "update-password";
    private static final String UPDATE_PASSWORD_VIEW = "password-form";
    private static final String SAVE_PASSWORD_URL = "/update-password/update";

    private static final String UPDATE_PROFILE_URL = "update-profile";
    private static final String UPDATE_PROFILE_VIEW = "profile-update-form";
    private static final String SAVE_PROFILE_URL = "/update-profile/update";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @InitBinder(PASSWORD)
    public void passwordBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordValidator());
    }

    @InitBinder(USER)
    public void profileBinder(WebDataBinder webDataBinder, ServletRequest servletRequest) {
        webDataBinder.setDisallowedFields("password", "confirmPassword", "type");
        webDataBinder.addValidators(userValidator);
    }

    @InitBinder(CUSTOMER)
    public void registrationBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("type");
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping(REGISTRATION_FORM_URL)
    public String showRegistrationPage(ModelMap modelMap) {
        modelMap.put(CUSTOMER, new User());

        return REGISTRATION_FORM_VIEW;
    }

    @PostMapping(REGISTRATION_FORM_SAVE_URL)
    public String saveCustomer(@Valid @ModelAttribute(CUSTOMER) User customer,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws Exception {

        if (!customer.isNew()) {
            throw new IllegalAccessException();
        }

        if (bindingResult.hasErrors()) {
            return REGISTRATION_FORM_VIEW;
        }

        customer.setPassword(Encryption.getPBKDF2(customer.getPassword()));
        customer.setType(UserType.CUSTOMER);
        userService.saveOrUpdate(customer);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.registration", null, Locale.getDefault()));

        return REDIRECT + REGISTRATION_FORM_REDIRECT_URL;
    }

    @GetMapping(UPDATE_PASSWORD_URL)
    public String showPasswordUpdatePage(ModelMap modelMap, @SessionAttribute(ACTIVE_USER) User user) {
        Password password = new Password();
        password.setStoredUserPassword(user.getPassword());
        modelMap.put(PASSWORD, password);

        return UPDATE_PASSWORD_VIEW;
    }

    @PostMapping(SAVE_PASSWORD_URL)
    String savePassword(@SessionAttribute(ACTIVE_USER) User user,
                        @Valid @ModelAttribute(PASSWORD) Password password,
                        BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return UPDATE_PASSWORD_VIEW;
        }

        user.setPassword(Encryption.getPBKDF2(password.getNewPassword()));
        userService.saveOrUpdate(user);

        return REDIRECT;
    }

    @GetMapping(UPDATE_PROFILE_URL)
    String updateProfileFrom(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(USER, userService.findById(user.getId()));
        modelMap.put(PROFILE_UPDATE_PAGE, true);

        return UPDATE_PROFILE_VIEW;
    }

    @PostMapping(SAVE_PROFILE_URL)
    String updateProfile(@SessionAttribute(ACTIVE_USER) User activeUser,
                         @Valid @ModelAttribute(USER) User user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws Exception {

        if (activeUser.getId() != user.getId()) {
            throw new IllegalAccessException();
        }

        if (bindingResult.hasErrors()) {
            return UPDATE_PROFILE_VIEW;
        }

        userService.saveOrUpdate(user);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.profile.updated", null, Locale.getDefault()));

        return REDIRECT;
    }
}
