package net.therap.onlinestore.helper;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
public class UserHelper {

    private static final String cellRegex = "01([0-9]])*";

    public static boolean isInvalidCellNumber(String cell) {

        if(Objects.isNull(cell)){
            return true;
        }

        if (Pattern.compile(cellRegex).matcher(cell).find() && cell.length() == 11) {
            return false;
        }

        return true;
    }
}
