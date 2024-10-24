package ru.dmitry.naujava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import ru.dmitry.naujava.entity.Expense;
import ru.dmitry.naujava.entity.Income;
import ru.dmitry.naujava.entity.Transaction;
import ru.dmitry.naujava.repository.TransactionRepository;

import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private TransactionStatus transactionStatus;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTransactions_Success() {
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        Transaction transaction1 = new Income();
        transaction1.setId(1);
        Transaction transaction2 = new Expense();
        transaction1.setId(2);

        transactionService.saveTransacrions(transaction1, transaction2);

        verify(transactionRepository, times(1)).save(transaction1);
        verify(transactionRepository, times(1)).save(transaction2);
        verify(transactionManager, times(1)).commit(transactionStatus);
    }

    @Test
    void testSaveTransactions_RollbackOnFailure() {
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

        Transaction transaction1 = new Income();
        transaction1.setId(1);
        Transaction transaction2 = new Expense();
        transaction1.setId(2);

        doThrow(new RuntimeException("Error saving transaction")).when(transactionRepository).save(transaction2);

        Assertions.assertThrows(
                RuntimeException.class, () -> transactionService.saveTransacrions(transaction1, transaction2));

        verify(transactionRepository, times(1)).save(transaction1);
        verify(transactionRepository, times(1)).save(transaction2);
        verify(transactionManager, times(1)).rollback(transactionStatus);
    }
}
