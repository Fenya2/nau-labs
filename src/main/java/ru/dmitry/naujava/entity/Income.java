package ru.dmitry.naujava.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

/**
 * Доход пользователя
 */
@Entity
@DiscriminatorValue("INCOME")
public final class Income extends Transaction {
    public Income(BigDecimal sum, Category category, Currency currency, User author, String description) {
        super(sum, category, currency, author, description);
    }

    public Income() {
    }
}
