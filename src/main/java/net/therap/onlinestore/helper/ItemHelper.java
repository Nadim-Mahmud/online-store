package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Item;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.*;

/**
 * @author nadimmahmud
 * @since 3/19/23
 */
public class ItemHelper {

    public List<Item> intersectItemList(List<Item> itemList1, List<Item> itemList2) {
        itemList1 = isNull(itemList1) ? new ArrayList<>() : itemList1;
        itemList2 = isNull(itemList2) ? new ArrayList<>() : itemList2;
        itemList1.removeAll(itemList2);
        itemList2.addAll(itemList1);

        return itemList2;
    }
}
