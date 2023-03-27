package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/26/23
 */
@Component
public class ItemHelper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ItemService itemService;

    public void checkAccess(User user, AccessType accessType) throws IllegalAccessException {

        if (Objects.isNull(user)) {
            throw new IllegalAccessException();
        }

        if (Arrays.asList(AccessType.VIEW_ALL, AccessType.FORM_LOAD, AccessType.SAVE, AccessType.DELETE).contains(accessType)
                && !UserType.ADMIN.equals(user.getType())) {
            throw new IllegalAccessException();
        }
    }

    public void populateItemFromRefData(ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(ALL_TAG_LIST, tagService.findAll());
        modelMap.put(AVAILABILITY_LIST, Availability.values());
        modelMap.put(NAV_ITEM, ITEM);
    }

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
