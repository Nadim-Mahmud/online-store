package net.therap.onlinestore.validator;

import net.therap.onlinestore.entity.Category;
import net.therap.onlinestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class CategoryValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (categoryService.isExistingCategory((Category) target)) {
            errors.rejectValue("name", "input.categoryName.duplicate");
        }
    }
}
