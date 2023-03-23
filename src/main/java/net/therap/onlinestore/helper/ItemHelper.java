package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author nadimmahmud
 * @since 3/22/23
 */
@Component
public class ItemHelper {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TagService tagService;

    public List<Item> filter(String categoryId, String tagId, int start, int limit) {

        if ((isNull(categoryId) || categoryId.isEmpty()) && (isNull(tagId) || tagId.isEmpty())) {
            return itemService.findAllPaginated(start, limit);
        }

        if ((nonNull(categoryId) && !categoryId.isEmpty()) && (isNull(tagId) || tagId.isEmpty())) {
            return itemService.findByCategoryPaginated(Integer.parseInt(categoryId), start, limit);
        }

        Tag tag = tagService.findById(Integer.parseInt(tagId));

        if ((nonNull(tagId) && !tagId.isEmpty()) && (isNull(categoryId) || categoryId.isEmpty())) {

            return itemService.findByTagPaginated(tag, start, limit);
        }

        return itemService.findByTagAndCategoryIdPaginated(tag, Integer.parseInt(categoryId), start, limit);
    }
}
