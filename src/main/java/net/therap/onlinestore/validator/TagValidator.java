package net.therap.onlinestore.validator;

import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class TagValidator implements Validator {

    @Autowired
    private TagService tagService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Tag.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (tagService.isExistingTag((Tag) target)) {
            errors.rejectValue("name", "input.item.duplicate");
        }
    }
}
