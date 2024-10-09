package ru.dmitry.naujava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Валюта
 */
@Entity
@Table(name = "tbl_currency")
public class Currency {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя
     */
    @Column(nullable = false)
    private String name;

    /**
     * Символ валюты
     */
    @Column(nullable = false, length = 1)
    private char symbol;

    /**
     * Короткое имя
     */
    @Column(nullable = false, length = 3)
    private String shortName;

    public Currency() {}

    public Currency(String name, char symbol, String shortName) {
        this.name = name;
        this.symbol = symbol;
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Currency currency)) return false;
        return id == currency.id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
