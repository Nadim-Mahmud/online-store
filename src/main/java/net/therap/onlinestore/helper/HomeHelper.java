package net.therap.onlinestore.helper;

import net.therap.onlinestore.constant.Constants;
import net.therap.onlinestore.entity.PaginationPageType;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * @author nadimmahmud
 * @since 3/26/23
 */
@Component
public class HomeHelper {

    private static final String SEARCH_KEY_PARAM = "searchKey";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemHelper itemHelper;

    public void populateHomePageModel(ModelMap modelMap, PaginationPageType pageType, String pageStartValue, String categoryId, String tagId){
        int start = PaginationHelper.calculateStartPage(pageStartValue, pageType);
        modelMap.put(Constants.CATEGORY_LIST, categoryService.findAll());
        modelMap.put(Constants.TAG_LIST, tagService.findAll());
        modelMap.put(Constants.ITEM_LIST, itemHelper.filter(categoryId, tagId, start, Constants.ITEM_PER_PAGE));
        modelMap.put(Constants.PAGE_START_VALUE, start);
        modelMap.put(Constants.CATEGORY_ID, categoryId);
        modelMap.put(Constants.TAG_ID, tagId);
    }

    public void populateSearchPageModle(ModelMap modelMap, PaginationPageType pageType, String pageStartValue, String searchKey){
        int start = PaginationHelper.calculateStartPage(pageStartValue, pageType);
        modelMap.put(Constants.CATEGORY_LIST, categoryService.findAll());
        modelMap.put(Constants.TAG_LIST, tagService.findAll());
        modelMap.put(Constants.ITEM_LIST, itemService.search(searchKey, start, Constants.ITEM_PER_PAGE));
        modelMap.put(SEARCH_KEY_PARAM, searchKey);
        modelMap.put(Constants.PAGE_START_VALUE, start);
    }
}
