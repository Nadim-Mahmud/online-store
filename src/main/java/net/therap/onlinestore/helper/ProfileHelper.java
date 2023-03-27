package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.exception.IllegalAccessException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/27/23
 */
@Component
public class ProfileHelper {

    public void checkAccess(User activeUser, User user) throws IllegalAccessException {

        if (Objects.isNull(activeUser) || Objects.isNull(user)) {
            throw new IllegalAccessException();
        }

        if (activeUser.getId() != user.getId()) {
            throw new IllegalAccessException();
        }
    }
}
