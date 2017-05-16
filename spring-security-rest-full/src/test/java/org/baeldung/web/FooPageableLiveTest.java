package org.baeldung.web;

import static org.baeldung.Consts.APPLICATION_PORT;
import static org.hamcrest.Matchers.is;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.List;

import org.baeldung.common.web.AbstractBasicLiveTest;
import org.baeldung.persistence.model.Foo;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.baeldung.spring.ConfigTest;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import org.junit.runner.RunWith;
import org.junit.Test;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigTest.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class FooPageableLiveTest extends AbstractBasicLiveTest<Foo> {

    public FooPageableLiveTest() {
        super(Foo.class);
    }

    // API

    @Override
    public final void create() {
        create(new Foo(randomAlphabetic(6)));
    }

    @Override
    public final String createAsUri() {
        return createAsUri(new Foo(randomAlphabetic(6)));
    }
    
    @Test
    public void whenResourcesAreRetrievedPaged_then200IsReceived() {
        final Response response = givenAuth().get(getPageableURL() + "?page=0&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final String url = getPageableURL() + "?page=" + randomNumeric(5) + "&size=10";
        final Response response = givenAuth().get(url);

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        create();

        final Response response = givenAuth().get(getPageableURL() + "?page=0&size=10");

        assertFalse(response.body().as(List.class).isEmpty());
    }

    protected String getPageableURL() {
        return "http://localhost:" + APPLICATION_PORT + "/spring-security-rest-full/auth/foos/pageable";
    }
    
}
