package org.pet.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Service
public class TransactionalExecutionService {

    @Transactional
    public void executeInTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public <T> T executeInTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
