package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.service.TagService;
import net.therap.onlinestore.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Controller
@RequestMapping(ADMIN_BASE_URL)
@SessionAttributes(TAG)
public class TagController {

    private static final String TAG_REDIRECT_URL = "admin/tag";
    private static final String TAG_URL = "tag";
    private static final String TAG_VIEW = "tag-list";
    private static final String TAG_FORM_URL = "tag/form";
    private static final String TAG_FORM_SAVE_URL = "tag/save";
    private static final String TAG_FORM_VIEW = "tag-form";
    private static final String TAG_ID_PARAM = "tagId";
    private static final String TAG_DELETE_URL = "tag/delete";

    @Autowired
    private TagService tagService;

    @Autowired
    private TagValidator tagValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder(TAG)
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.addValidators(tagValidator);
    }

    @GetMapping(TAG_URL)
    public String showTag(ModelMap modelMap) {
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(NAV_ITEM, TAG);

        return TAG_VIEW;
    }
    
    @GetMapping(TAG_FORM_URL)
    public String showTagForm(@RequestParam(value = TAG_ID_PARAM, required = false) String tagId,
                                   ModelMap modelMap) {
        Tag tag = nonNull(tagId) ? tagService.findById(Integer.parseInt(tagId)) : new Tag();

        modelMap.put(TAG, tag);
        modelMap.put(NAV_ITEM, TAG);

        return TAG_FORM_VIEW;
    }

    @PostMapping(TAG_FORM_SAVE_URL)
    public String saveOrUpdateTag(
            @Valid @ModelAttribute(TAG) Tag tag,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, TAG);

            return TAG_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (tag.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        tagService.saveOrUpdate(tag);
        sessionStatus.setComplete();

        return REDIRECT + TAG_REDIRECT_URL;
    }

    @PostMapping(TAG_DELETE_URL)
    public String deleteTag(@RequestParam(TAG_ID_PARAM) int tagId,
                                 RedirectAttributes redirectAttributes) throws Exception {
        tagService.delete(tagId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + TAG_REDIRECT_URL;
    }
}
