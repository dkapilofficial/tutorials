package org.baeldung.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.baeldung.model.User;


public interface UserRepository extends MongoRepository<User, String> {
   
}
