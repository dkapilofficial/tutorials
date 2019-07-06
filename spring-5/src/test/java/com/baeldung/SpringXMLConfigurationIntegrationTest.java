package com.baeldung;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.components.AccountService;
import com.baeldung.components.UserService;

public class SpringXMLConfigurationIntegrationTest {

    @Test
    public void givenContextAnnotationConfigOrContextComponentScan_whenDependenciesAndBeansAnnotated_thenNoXMLNeeded() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        UserService userService = context.getBean(UserService.class);
        AccountService accountService = context.getBean(AccountService.class);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(accountService);
        Assert.assertNotNull(userService.getAccountService());
    }

}
