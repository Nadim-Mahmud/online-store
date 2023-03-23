package net.therap.onlinestore.validator;

import net.therap.onlinestore.entity.OrderItem;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/14/23
 */
public class OrderItemValidator {

    public static void validate(List<OrderItem> orderItemList, OrderItem orderItem, Errors errors) {

        if (Objects.isNull(orderItem.getItem())) {
            errors.rejectValue("item", "input.empty.selection");

            return;
        }

        if (orderItemList.contains(orderItem)) {
            errors.rejectValue("item", "select.item.exists");
        }
    }
}
