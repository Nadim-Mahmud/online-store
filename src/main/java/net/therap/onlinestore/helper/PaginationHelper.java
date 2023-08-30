package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.PaginationPageType;

import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.ITEM_PER_PAGE;
import static net.therap.onlinestore.entity.PaginationPageType.NEXT_PAGE;
import static net.therap.onlinestore.entity.PaginationPageType.PREVIOUS_PAGE;

/**
 * @author nadimmahmud
 * @since 3/22/23
 */
public class PaginationHelper {

    public static int calculateStartPage(String pageStartValue, PaginationPageType pageType) {
        int start = Objects.nonNull(pageStartValue) && !pageStartValue.isEmpty() ? Integer.parseInt(pageStartValue) : 0;

        start = NEXT_PAGE.equals(pageType) ? start + ITEM_PER_PAGE :
                PREVIOUS_PAGE.equals(pageType) ? start - ITEM_PER_PAGE : start;

        return Integer.max(0, start);
    }
}
