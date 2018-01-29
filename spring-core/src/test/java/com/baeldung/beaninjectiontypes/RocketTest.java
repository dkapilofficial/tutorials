package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class RocketTest {

    @Autowired
    Rocket rocket;

    @Test
    public void engineInjectionTest() {
        assertNotNull(rocket);
        assertNotNull(rocket.getEngine());
    }
}
