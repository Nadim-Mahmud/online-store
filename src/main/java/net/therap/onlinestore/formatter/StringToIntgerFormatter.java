package net.therap.onlinestore.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Component
public class StringToIntgerFormatter implements Formatter<Integer> {

    @Override
    public Integer parse(String number, Locale locale) throws ParseException {

        if (Objects.isNull(number) || number.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(number);
    }

    @Override
    public String print(Integer integer, Locale locale) {
        return integer.toString();
    }
}
