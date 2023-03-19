package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @author nadimmahmud
 * @since 3/19/23
 */
public class ItemHelper {

    public List<Item> intersectItemList(List<Item> itemList1, List<Item> itemList2) {
        itemList1 = isNull(itemList1) ? new ArrayList<>() : itemList1;
        itemList2 = isNull(itemList2) ? new ArrayList<>() : itemList2;

        return itemList1.stream()
                .filter(itemList2::contains)
                .collect(Collectors.toList());
    }
}
