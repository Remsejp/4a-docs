package com.minsuite_java.minsuite_java.repositories;

import com.minsuite_java.minsuite_java.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUsernameOrigin (String usernameOrigin);
    List<Transaction> findByUsernameDestiny (String usernameDestiny);
}