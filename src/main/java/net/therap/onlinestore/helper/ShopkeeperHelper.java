package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.AccessType;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/27/23
 */
@Component
public class ShopkeeperHelper {

    public void checkAccess(User user, AccessType accessType) throws IllegalAccessException {

        if(Objects.isNull(user)){
            throw new IllegalAccessException();
        }

        if (AccessType.VIEW_ALL.equals(accessType) && !UserType.SHOPKEEPER.equals(user.getType())){

            throw new IllegalAccessException();
        }
    }
}
