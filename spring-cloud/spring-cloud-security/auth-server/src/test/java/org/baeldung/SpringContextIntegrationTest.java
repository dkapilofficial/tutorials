package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.AuthServer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServer.class)
public class SpringContextIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
