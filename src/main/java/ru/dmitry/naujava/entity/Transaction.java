package ru.dmitry.naujava.entity;

import jakarta.persistence.*;
import ru.dmitry.naujava.Constants;

import java.math.BigDecimal;

/**
 * Транзакция. Описывает общие свойства доходов и расходов
 */
@Entity
@Table(name = "tbl_transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
public abstract class Transaction {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Величина транзакции
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Категория, к которой транзакция относится
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Валюта, в которой транзакция выполнена
     */
    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    /**
     * Автор транзакции
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * Описание транзакции
     */
    @Column(length = Constants.DESCRIPTION_SIZE)
    private String description;

    protected Transaction() {}

    protected Transaction(BigDecimal amount, Category category, Currency currency, User author, String description) {
        this.amount = amount;
        this.category = category;
        this.currency = currency;
        this.author = author;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Transaction that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal sum) {
        this.amount = sum;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
