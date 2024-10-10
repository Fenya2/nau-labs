package ru.dmitry.naujava.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.dmitry.naujava.entity.Currency;
import ru.dmitry.naujava.entity.Expense;
import ru.dmitry.naujava.entity.Income;
import ru.dmitry.naujava.entity.Transaction;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public TransactionRepositoryTest(
            UserRepository userRepository,
            TransactionRepository transactionRepository,
            CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.currencyRepository = currencyRepository;
    }

    @Test
    void testFindAllByAuthor() {
        User creator1 = new User("dsyromyatnikov", "fenya74.09@gmail.com", LocalDate.now());
        User creator2 = new User("impostor", "dim74.09@mail.ru", LocalDate.now());
        userRepository.save(creator1);
        userRepository.save(creator2);

        Currency currency = new Currency("Russian rubble", '₽', "RUB");
        currencyRepository.save(currency);

        Transaction income1 = new Income(BigDecimal.ONE, null, currency, creator1, "transfer");
        Transaction income2 = new Expense(BigDecimal.ONE, null, currency, creator2, "transfer");

        transactionRepository.save(income1);
        transactionRepository.save(income2);

        List<Transaction> transactions = transactionRepository.findAllByAuthor(creator1);
        assertThat(transactions).containsExactly(income1);
    }

    @Test
    void testFindAllBySumBetween() {
        User creator1 = new User("dsyromyatnikov", "fenya74.09@gmail.com", LocalDate.now());
        userRepository.save(creator1);

        Currency currency = new Currency("Russian rubble", '₽', "RUB");
        currencyRepository.save(currency);

        Transaction income1 = new Income(BigDecimal.ONE, null, currency, creator1, "transfer");
        Transaction income2 = new Expense(BigDecimal.TEN, null, currency, creator1, "transfer");

        transactionRepository.save(income1);
        transactionRepository.save(income2);

        List<Transaction> transactions = transactionRepository.findAllByAmountBetween(BigDecimal.TWO, BigDecimal.TEN);
        assertThat(transactions).containsExactly(income2);
    }
}