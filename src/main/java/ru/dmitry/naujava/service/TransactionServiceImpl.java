package ru.dmitry.naujava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.dmitry.naujava.entity.Transaction;
import ru.dmitry.naujava.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  PlatformTransactionManager transactionManager, TransactionTemplate transactionTemplate) {
        this.transactionRepository = transactionRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void saveTransacrions(Transaction... transaction) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Transaction transaction : transaction) {
                    transactionRepository.save(transaction);
                }
            }
        });
    }
}
