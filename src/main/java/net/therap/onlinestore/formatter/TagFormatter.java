package net.therap.onlinestore.formatter;

import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.service.TagService;
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
public class TagFormatter implements Formatter<Tag> {

    @Autowired
    private TagService tagService;

    @Override
    public Tag parse(String id, Locale locale) throws ParseException {
        return tagService.findById(Integer.parseInt(id));
    }

    @Override
    public String print(Tag tag, Locale locale) {
        return tag.getName();
    }
}
