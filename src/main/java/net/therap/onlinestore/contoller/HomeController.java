package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/20/23
 */
@Controller
@RequestMapping({BASE_URL, ADMIN_BASE_URL, CUSTOMER_BASE_URL})
public class HomeController {

    private static final String HOME_VIEW = "home";
    private static final String ITEM_SEARCH_URL = "/item/search";
    private static final String SEARCH_KEY_PARAM = "searchKey";
    private static final String ITEM_CATEGORY_ID = "item/{categoryId}";
    private static final String CATEGORY_ID = "categoryId";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ItemService itemService;

    @GetMapping(HOME_URL)
    public String showHome(@RequestParam(value = CATEGORY_ID, required = false) String categoryId, @RequestParam(value = TAG_ID, required = false) String tagId, ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemService.filter(categoryId, tagId));
        modelMap.put(CATEGORY_ID, categoryId);
        modelMap.put(TAG_ID, tagId);

        return HOME_VIEW;
    }

    @GetMapping(ITEM_SEARCH_URL)
    public String searchItems(@RequestParam(value = SEARCH_KEY_PARAM, required = false) String searchKey, ModelMap modelMap) {

        if (Objects.isNull(searchKey)) {
            return REDIRECT;
        }

        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemService.search(searchKey));
        modelMap.put(SEARCH_KEY_PARAM, searchKey);

        return HOME_VIEW;
    }

    @GetMapping(ITEM_CATEGORY_ID)
    @ResponseBody
    public List<Item> getItemByCategoryId(@PathVariable(CATEGORY_ID) int categoryId) {
        return itemService.findByCategory(categoryId);
    }
}
