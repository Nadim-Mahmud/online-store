package net.therap.onlinestore.validator;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class ItemValidator implements Validator {

    private static final String MIME_TYPE_IMAGE = "image/";

    @Autowired
    private ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if (itemService.isExistingItem(item)) {
            errors.rejectValue("name", "input.item.duplicate");

            return;
        }

        if (!item.getImage().isEmpty()) {
            if (Objects.nonNull(item.getImage()) && !item.getImage().getContentType().startsWith(MIME_TYPE_IMAGE)) {
                errors.rejectValue("image", "input.image");
            }
        }
    }
}
