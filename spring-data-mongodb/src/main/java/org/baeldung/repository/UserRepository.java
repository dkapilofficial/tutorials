package org.baeldung.repository;

import java.util.List;

import org.baeldung.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends MongoRepository<User, String>, QueryDslPredicateExecutor<User> {
    @Query("{ 'name' : ?0 }")
    List<User> findUsersByName(String name);

    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<User> findUsersByAgeBetween(int ageGT, int ageLT);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<User> findUsersByRegexpName(String regexp);

    List<User> findByName(String name);

    List<User> findByNameLikeOrderByAgeAsc(String name);

    List<User> findByAgeBetween(int ageGT, int ageLT);

    List<User> findByNameStartingWith(String regexp);

    List<User> findByNameEndingWith(String regexp);
}
