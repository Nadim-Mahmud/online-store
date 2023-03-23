package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.PaginationPageType;
import net.therap.onlinestore.helper.ItemHelper;
import net.therap.onlinestore.helper.PaginationHelper;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private static final String CATEGORY_ID = "categoryId";
    private static final String PAGE_TYPE = "pageType";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemHelper itemHelper;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(HOME_URL)
    public String showHome(@RequestParam(value = PAGE_TYPE, required = false) PaginationPageType pageType,
                           @RequestParam(value = PAGE_START_VALUE, required = false) String pageStartValue,
                           @RequestParam(value = CATEGORY_ID, required = false) String categoryId,
                           @RequestParam(value = TAG_ID, required = false) String tagId, ModelMap modelMap) {
        int start = PaginationHelper.calculateStartPage(pageStartValue, pageType);
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemHelper.filter(categoryId, tagId, start, ITEM_PER_PAGE));
        modelMap.put(PAGE_START_VALUE, start);
        modelMap.put(CATEGORY_ID, categoryId);
        modelMap.put(TAG_ID, tagId);

        return HOME_VIEW;
    }

    @GetMapping(ITEM_SEARCH_URL)
    public String searchItems(@RequestParam(value = PAGE_TYPE, required = false) PaginationPageType pageType,
                              @RequestParam(value = PAGE_START_VALUE, required = false) String pageStartValue,
                              @RequestParam(value = SEARCH_KEY_PARAM, required = false) String searchKey,
                              ModelMap modelMap) {

        if (Objects.isNull(searchKey) || searchKey.isEmpty()) {
            return REDIRECT;
        }

        int start = PaginationHelper.calculateStartPage(pageStartValue, pageType);
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemService.search(searchKey, start, ITEM_PER_PAGE));
        modelMap.put(SEARCH_KEY_PARAM, searchKey);
        modelMap.put(PAGE_START_VALUE, start);

        return HOME_VIEW;
    }
}
