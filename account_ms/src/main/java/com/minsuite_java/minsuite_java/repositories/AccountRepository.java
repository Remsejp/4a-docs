package com.minsuite_java.minsuite_java.repositories;

import com.minsuite_java.minsuite_java.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface AccountRepository extends MongoRepository <Account, String> { }