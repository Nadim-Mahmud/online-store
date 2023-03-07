package net.therap.onlinestore.validator;


import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.helper.UserHelper;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/6/23
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "email", "input.email");
        User user = (User) target;

        if (userService.isDuplicateEmail(user)) {
            errors.rejectValue("email", "input.email.duplicate");
        }

        if (UserHelper.isInvalidCellNumber(user.getCell())) {
            errors.rejectValue("cell", "input.cell");
        }

        if (Objects.nonNull(user.getPassword()) && !user.getPassword().equals(user.getConfirmPassword()) && user.isNew()) {
            errors.rejectValue("password", "input.password.confirm");
            errors.rejectValue("confirmPassword", "input.password.confirm");
        }
    }
}
