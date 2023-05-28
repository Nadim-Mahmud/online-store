package net.therap.onlinestore.util;

import net.therap.onlinestore.constant.Constants;
import net.therap.onlinestore.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @author nadimmahmud
 * @since 3/30/23
 */
public class Util {

    public static User getActiveUser(HttpSession httpSession) {

        return (User) httpSession.getAttribute(Constants.ACTIVE_USER);
    }
}
