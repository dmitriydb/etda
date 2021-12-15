package com.github.dmitriydb.etda.model;

import java.util.Locale;

/**
 * Контракт, который выполняют все объекты модели
 *
 * В данный момент нужен для реализации метода format
 *
 * @version 0.1.2
 * @since 0.1.2
 */
public interface EtdaEntity {
    String format(Locale locale);
}
