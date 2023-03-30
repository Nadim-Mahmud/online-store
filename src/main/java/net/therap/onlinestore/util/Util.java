package net.therap.onlinestore.util;

import net.therap.onlinestore.entity.User;

import javax.servlet.http.HttpSession;

import static net.therap.onlinestore.constant.Constants.ACTIVE_USER;

/**
 * @author nadimmahmud
 * @since 3/30/23
 */
public class Util {

    public static User getActiveUser(HttpSession httpSession) {

        return (User) httpSession.getAttribute(ACTIVE_USER);
    }
}
