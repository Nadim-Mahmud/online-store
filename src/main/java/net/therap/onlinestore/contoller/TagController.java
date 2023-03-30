package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.helper.TagHelper;
import net.therap.onlinestore.service.TagService;
import net.therap.onlinestore.util.Util;
import net.therap.onlinestore.validator.TagValidator;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpSession;
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
@SessionAttributes(TAG)
public class TagController {

    private static final String TAG_REDIRECT_URL = "admin/tag";
    private static final String TAG_URL = "tag";
    private static final String TAG_VIEW = "tag/tag-list";
    private static final String TAG_FORM_URL = "tag/form";
    private static final String TAG_FORM_SAVE_URL = "tag/save";
    private static final String TAG_FORM_VIEW = "tag/tag-form";
    private static final String TAG_ID_PARAM = "tagId";
    private static final String TAG_DELETE_URL = "tag/delete";

    private static final Logger logger = Logger.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    @Autowired
    private TagValidator tagValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TagHelper tagHelper;

    @InitBinder(TAG)
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.setDisallowedFields("id", "access_status", "version", "created_at", "updated_at");
        webDataBinder.addValidators(tagValidator);
    }

    @GetMapping(TAG_URL)
    public String showTag(ModelMap modelMap, HttpSession httpSession) {

        tagHelper.checkAccess(Util.getActiveUser(httpSession));

        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(NAV_ITEM, TAG);

        return TAG_VIEW;
    }

    @GetMapping(TAG_FORM_URL)
    public String showTagForm(@RequestParam(value = TAG_ID_PARAM, required = false) String tagId,
                              ModelMap modelMap,
                              HttpSession httpSession) {

        tagHelper.checkAccess(Util.getActiveUser(httpSession));

        Tag tag = nonNull(tagId) ? tagService.findById(Integer.parseInt(tagId)) : new Tag();
        modelMap.put(TAG, tag);
        modelMap.put(NAV_ITEM, TAG);

        return TAG_FORM_VIEW;
    }

    @PostMapping(TAG_FORM_SAVE_URL)
    public String saveOrUpdateTag(@Valid @ModelAttribute(TAG) Tag tag,
                                  BindingResult bindingResult,
                                  ModelMap modelMap,
                                  SessionStatus sessionStatus,
                                  HttpSession httpSession,
                                  RedirectAttributes redirectAttributes) {

        tagHelper.checkAccess(Util.getActiveUser(httpSession));

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, TAG);

            return TAG_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (tag.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        tagService.saveOrUpdate(tag);
        logger.info("Saved tag: " + tag.getName());
        sessionStatus.setComplete();

        return REDIRECT + TAG_REDIRECT_URL;
    }

    @PostMapping(TAG_DELETE_URL)
    public String deleteTag(@RequestParam(TAG_ID_PARAM) int tagId,
                            RedirectAttributes redirectAttributes,
                            HttpSession httpSession) {

        tagHelper.checkAccess(Util.getActiveUser(httpSession));

        tagService.delete(tagId);
        logger.info("deleted tag: " + tagId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + TAG_REDIRECT_URL;
    }
}
