package org.baeldung.jackson.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.baeldung.jackson.jsonview.Item;
import org.baeldung.jackson.jsonview.MyBeanSerializerModifier;
import org.baeldung.jackson.jsonview.User;
import org.baeldung.jackson.jsonview.Views;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class JacksonJsonViewTest {

    @Test
    public void whenUseJsonViewToSerialize_thenCorrect() throws JsonProcessingException {
        final User user = new User(1, "John");

        final ObjectMapper mapper = new ObjectMapper();
        final String result = mapper.writerWithView(Views.Public.class).writeValueAsString(user);

        assertThat(result, containsString("John"));
        assertThat(result, containsString("1"));
    }

    @Test
    public void whenUsePublicView_thenOnlyPublicSerialized() throws JsonProcessingException {
        final User owner = new User(1, "John");
        final Item item = new Item(2, "book", owner);

        final ObjectMapper mapper = new ObjectMapper();
        final String result = mapper.writerWithView(Views.Public.class).writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));

        assertThat(result, not(containsString("John")));
        assertThat(result, not(containsString("1")));
    }

    @Test
    public void whenUseInternalView_thenAllSerialized() throws JsonProcessingException {
        final User owner = new User(1, "John");
        final Item item = new Item(2, "book", owner);

        final ObjectMapper mapper = new ObjectMapper();
        final String result = mapper.writerWithView(Views.Internal.class).writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));

        assertThat(result, containsString("John"));
        assertThat(result, containsString("1"));
    }

    @Test
    public void whenUseJsonViewToDeserialize_thenCorrect() throws IOException {
        final String json = "{\"id\":1,\"name\":\"John\"}";

        final ObjectMapper mapper = new ObjectMapper();

        final User user = mapper.readerWithView(Views.Public.class).withType(User.class).readValue(json);
        assertEquals(1, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    public void whenUseCustomJsonViewToSerialize_thenCorrect() throws JsonProcessingException {
        final User user = new User(1, "John");
        final SerializerFactory serializerFactory = BeanSerializerFactory.instance.withSerializerModifier(new MyBeanSerializerModifier());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializerFactory(serializerFactory);

        final String result = mapper.writerWithView(Views.Public.class).writeValueAsString(user);
        assertThat(result, containsString("JOHN"));
        assertThat(result, containsString("1"));
    }
}