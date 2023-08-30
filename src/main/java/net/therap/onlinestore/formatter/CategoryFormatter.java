package net.therap.onlinestore.formatter;

import net.therap.onlinestore.entity.Category;
import net.therap.onlinestore.service.CategoryService;
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
public class CategoryFormatter implements Formatter<Category> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category parse(String id, Locale locale) throws ParseException {
        return categoryService.findById(Integer.parseInt(id));
    }

    @Override
    public String print(Category category, Locale locale) {
        return category.getName();
    }
}
