package com.github.dmitriydb.etda.model;

import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;

/**
 * Класс, который форматирует даты и суммы зарплат (в дальнейшем варианты могут увеличиться)
 * с учетом переданной локали
 *
 * @version 0.1.2
 * @since 0.1.2
 */
public class LocaleManager {

    /**
     * Метод форматирует сумму salary в формате, принятом для денежных сумм в локали locale
     * @param salary
     * @param locale
     * @return
     *
     * @since 0.1.2
     */
    public static String formatSalaryToLocaleFormat(Long salary, Locale locale){
        if (salary == null || locale == null)
            throw new IllegalArgumentException();
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(Currency.getInstance(locale));
        return format.format(salary);
    }

    public static String formatSqlDateToLocaleFormat(Date date, Locale locale){
        if (date == null || locale == null)
            throw new IllegalArgumentException();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        return formatter.format(date.toLocalDate());
    }


}
