package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.UserType;

import java.util.Arrays;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
public class UserTypeHelper {

    public static List<UserType> getUserTypeSelectList() {
        List<UserType> userTypeList = new java.util.ArrayList<>(Arrays.asList(UserType.values()));
        userTypeList.removeIf(s -> (s.equals(UserType.ADMIN) || s.equals(UserType.CUSTOMER)));

        return userTypeList;
    }
}
