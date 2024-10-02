package ru.dmitry.naujava.ui;

import org.springframework.stereotype.Component;
import ru.dmitry.naujava.entity.Category;

import java.text.MessageFormat;

/**
 * Класс для вывода строкового представления категорий в пользовательском интерфейсе
 */
@Component
public class CategoryFormatter {
    private MessageFormat messageFormat = new MessageFormat("{0}:{1}:{2}");

    /**
     * Возвращает строковое представление категории для вывода в пользовательский интерфейс
     */
    public String format(Category category) {
        if(category == null) {
            return "<нет категории>";
        }
        return messageFormat.format(
                new Object[]{category.getId(), category.getName(), category.getDescription()});
    }

    /**
     * Возвращает строковое представление категории для вывода в пользовательский интерфейс с первым
     * уровнем вложенных подкатегорий
     */
    public String formatWithChildsFirstLevel(Category category) {
        StringBuilder builder = new StringBuilder();
        builder.append(format(category));
        builder.append("\n");
        category.getNested().forEach(c -> builder.append("\t").append(format(c)).append("\n"));
        return builder.toString();
    }
}
