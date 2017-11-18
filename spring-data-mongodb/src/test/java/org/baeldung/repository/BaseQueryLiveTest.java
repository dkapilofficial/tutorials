package org.baeldung.repository;

import org.baeldung.model.User;
import org.baeldung.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

public class BaseQueryLiveTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!mongoOps.collectionExists(User.class)) {
            mongoOps.createCollection(User.class);
        }
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(User.class);
    }
}