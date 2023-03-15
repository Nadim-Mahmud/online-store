package net.therap.onlinestore.formatter;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Component
public class ItemFormatter implements Formatter<Item> {

    @Autowired
    private ItemService itemService;

    @Override
    public Item parse(String id, Locale locale) throws ParseException {
        return itemService.findById(Integer.parseInt(id));
    }

    @Override
    public String print(Item item, Locale locale) {
        return item.getName();
    }
}
