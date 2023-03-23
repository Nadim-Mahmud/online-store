package net.therap.onlinestore.helper;

import java.util.Objects;
import java.util.regex.Pattern;

import static net.therap.onlinestore.constant.Constants.PASSWORD_PATTERN;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
public class UserHelper {

    public static boolean isInvalidPassword(String cell) {

        if (Objects.isNull(cell)) {
            return true;
        }

        if (Pattern.compile(PASSWORD_PATTERN).matcher(cell).find()) {
            return false;
        }

        return true;
    }
}
