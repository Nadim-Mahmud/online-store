package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.exception.IllegalAccessException;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
public class AccessCheckHelper {

    public static void check(User user, AccessType access, Persistent entity) throws IllegalAccessException {

        if (entity instanceof Order) {
            Order order = (Order) entity;

            if (UserType.DELIVERYMAN.equals(user.getType())) {

                if (!AccessType.UPDATE.equals(access) || AccessType.DELETE.equals(access)) {
                    throw new IllegalAccessException();
                }

                if (!OrderStatus.PICKED.equals(order.getStatus()) && !OrderStatus.DELIVERED.equals(order.getStatus())) {
                    throw new IllegalAccessException();
                }

            } else if (UserType.SHOPKEEPER.equals(user.getType())) {

                if (AccessType.UPDATE.equals(access) && !OrderStatus.READY.equals(order.getStatus())) {
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
