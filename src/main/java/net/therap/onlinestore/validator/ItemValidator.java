package net.therap.onlinestore.validator;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class ItemValidator implements Validator {

    @Autowired
    private ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (itemService.isExistingItem((Item) target)) {
            errors.rejectValue("name", "input.item.duplicate");
        }
    }
}
