package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.*;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
public class AccessCheck {

    public static void check(User user, AccessType access, Persistent entity) throws IllegalAccessException {

        if (UserType.DELIVERYMAN.equals(user.getType())) {

            if (!AccessType.UPDATE.equals(access) || !(entity instanceof Order)) {
                throw new IllegalAccessException();
            }

            Order order = (Order) entity;

            if (!OrderStatus.PICKED.equals(order.getStatus()) || !OrderStatus.DELIVERED.equals(order.getStatus())) {
                throw new IllegalAccessException();
            }

            if(order.getAddress().getUser().equals(user)){

            }
        } else if (UserType.SHOPKEEPER.equals(user.getType())) {

            if (!AccessType.UPDATE.equals(access) || !(entity instanceof Order)) {
                throw new IllegalAccessException();
            }

            Order order = (Order) entity;

            if (!OrderStatus.READY.equals(order.getStatus())) {
                throw new IllegalAccessException();
            }
        }
    }
}
