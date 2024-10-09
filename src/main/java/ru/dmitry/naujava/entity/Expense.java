package ru.dmitry.naujava.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

/**
 * Расход пользователя
 */
@Entity
@DiscriminatorValue("EXPENSE")
public final class Expense extends Transaction {
    public Expense() {
    }

    public Expense(BigDecimal sum, Category category, Currency currency, User author, String description) {
        super(sum, category, currency, author, description);
    }
}
