package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Availability;
import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.formatter.CategoryFormatter;
import net.therap.onlinestore.formatter.TagFormatter;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.TagService;
import net.therap.onlinestore.validator.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Controller
@RequestMapping(ADMIN_BASE_URL)
@SessionAttributes(ITEM)
public class ItemController {

    private static final String ITEM_REDIRECT_URL = "admin/item";
    private static final String ITEM_URL = "item";
    private static final String ITEM_VIEW = "item-list";
    private static final String ITEM_FORM_URL = "item/form";
    private static final String ITEM_FORM_SAVE_URL = "item/save";
    private static final String ITEM_FORM_VIEW = "item-form";
    private static final String ITEM_ID_PARAM = "itemId";
    private static final String ITEM_DELETE_URL = "item/delete";
    private static final String ITEM_CATEGORY_ID = "item/{categoryId}";
    private static final String CATEGORY_ID = "categoryId";

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryFormatter categoryFormatter;

    @Autowired
    private TagFormatter tagFormatter;

    @Autowired
    private ItemValidator itemValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addCustomFormatter(categoryFormatter);
        webDataBinder.addCustomFormatter(tagFormatter);
        webDataBinder.addValidators(itemValidator);
    }

    @GetMapping(ITEM_URL)
    public String showItems(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(NAV_ITEM, ITEM);

        return ITEM_VIEW;
    }

    @GetMapping(ITEM_FORM_URL)
    public String showItemForm(@RequestParam(value = ITEM_ID_PARAM, required = false) String itemId,
                               ModelMap modelMap) {
        Item item = nonNull(itemId) ? itemService.findById(Integer.parseInt(itemId)) : new Item();

        modelMap.put(ITEM, item);
        setupReferenceDataItemForm(modelMap);

        return ITEM_FORM_VIEW;
    }

    @PostMapping(ITEM_FORM_SAVE_URL)
    public String saveOrUpdateItem(@Valid @ModelAttribute(ITEM) Item item,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataItemForm(modelMap);

            return ITEM_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (item.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        itemService.saveOrUpdate(item);
        sessionStatus.setComplete();

        return REDIRECT + ITEM_REDIRECT_URL;
    }

    @PostMapping(ITEM_DELETE_URL)
    public String deleteItem(@RequestParam(ITEM_ID_PARAM) int itemId,
                             RedirectAttributes redirectAttributes) throws Exception {


        itemService.delete(itemId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + ITEM_REDIRECT_URL;
    }

    @GetMapping(ITEM_CATEGORY_ID)
    public String getItemById(@PathVariable(CATEGORY_ID) String categoryId){
        System.out.println(categoryId);
        System.out.println("here...");
        return "nav-bar";
    }

    private void setupReferenceDataItemForm(ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(ALL_TAG_LIST, tagService.findAll());
        modelMap.put(AVAILABILITY_LIST, Availability.values());
        modelMap.put(NAV_ITEM, ITEM);
    }
}
