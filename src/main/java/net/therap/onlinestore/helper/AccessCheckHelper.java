package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.OrderService;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
public class AccessCheckHelper {

    public static void check(User user, AccessType access, Persistent entity) throws IllegalAccessException {

        if (UserType.DELIVERYMAN.equals(user.getType())) {

            if (!AccessType.UPDATE.equals(access) || !(entity instanceof Order) || AccessType.DELETE.equals(access)) {
                throw new IllegalAccessException();
            }

            Order order = (Order) entity;

            if (!OrderStatus.PICKED.equals(order.getStatus()) && !OrderStatus.DELIVERED.equals(order.getStatus())) {
                throw new IllegalAccessException();
            }

        } else if (UserType.SHOPKEEPER.equals(user.getType())) {

            if (!(entity instanceof Order)) {
                throw new IllegalAccessException();
            }

            Order order = (Order) entity;

            if (AccessType.UPDATE.equals(access)) {

                if (!OrderStatus.READY.equals(order.getStatus())) {
                    throw new IllegalAccessException();
                }
            }
        }

        if (entity instanceof Item) {

            if ((AccessType.SAVE.equals(access) || AccessType.DELETE.equals(access)) && !UserType.ADMIN.equals(user.getType())) {
                throw new IllegalAccessException();
            }
        }
    }
}
