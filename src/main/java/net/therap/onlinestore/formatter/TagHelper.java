package net.therap.onlinestore.formatter;

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
public class TagHelper {

    public void checkAccess(User user) throws IllegalAccessException {

        if (Objects.isNull(user) || !UserType.ADMIN.equals(user.getType())) {
            throw new IllegalAccessException();
        }
    }
}
