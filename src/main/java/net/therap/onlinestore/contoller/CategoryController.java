package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Category;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.helper.CategoryHelper;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.validator.CategoryValidator;
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
@SessionAttributes(CATEGORY)
public class CategoryController {

    private static final String CATEGORY_REDIRECT_URL = "admin/category";
    private static final String CATEGORY_URL = "category";
    private static final String CATEGORY_VIEW = "category/category-list";
    private static final String CATEGORY_FORM_URL = "category/form";
    private static final String CATEGORY_FORM_SAVE_URL = "category/save";
    private static final String CATEGORY_FORM_VIEW = "category/category-form";
    private static final String CATEGORY_ID_PARAM = "categoryId";
    private static final String CATEGORY_DELETE_URL = "category/delete";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryHelper categoryHelper;

    @InitBinder(CATEGORY)
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addValidators(categoryValidator);
    }

    @GetMapping(CATEGORY_URL)
    public String showCategory(@SessionAttribute(value = ACTIVE_USER, required = false) User user,
                               ModelMap modelMap) throws IllegalAccessException {

        categoryHelper.checkAccess(user);

        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(NAV_ITEM, CATEGORY);

        return CATEGORY_VIEW;
    }

    @GetMapping(CATEGORY_FORM_URL)
    public String showCategoryForm(@SessionAttribute(value = ACTIVE_USER, required = false) User user,
                                   @RequestParam(value = CATEGORY_ID_PARAM, required = false) String categoryId,
                                   ModelMap modelMap) throws IllegalAccessException {

        categoryHelper.checkAccess(user);

        Category category = nonNull(categoryId) ? categoryService.findById(Integer.parseInt(categoryId)) : new Category();
        modelMap.put(CATEGORY, category);
        modelMap.put(NAV_ITEM, CATEGORY);

        return CATEGORY_FORM_VIEW;
    }

    @PostMapping(CATEGORY_FORM_SAVE_URL)
    public String saveOrUpdateCategory(@SessionAttribute(value = ACTIVE_USER, required = false) User user,
                                       @Valid @ModelAttribute(CATEGORY) Category category,
                                       BindingResult bindingResult,
                                       ModelMap modelMap,
                                       SessionStatus sessionStatus,
                                       RedirectAttributes redirectAttributes) throws Exception {

        categoryHelper.checkAccess(user);

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, CATEGORY);

            return CATEGORY_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (category.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        categoryService.saveOrUpdate(category);
        sessionStatus.setComplete();

        return REDIRECT + CATEGORY_REDIRECT_URL;
    }

    @PostMapping(CATEGORY_DELETE_URL)
    public String deleteCategory(@SessionAttribute(value = ACTIVE_USER, required = false) User user,
                                 @RequestParam(CATEGORY_ID_PARAM) int categoryId,
                                 RedirectAttributes redirectAttributes) throws Exception {

        categoryHelper.checkAccess(user);

        if (categoryService.isCategoryNotInUse(categoryId)) {
            categoryService.delete(categoryId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        }

        return REDIRECT + CATEGORY_REDIRECT_URL;
    }
}
