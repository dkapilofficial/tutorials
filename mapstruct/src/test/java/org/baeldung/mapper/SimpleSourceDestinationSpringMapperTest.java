package org.baeldung.mapper;

import static org.junit.Assert.assertEquals;

import org.baeldung.dto.SimpleSource;
import org.baeldung.entity.SimpleDestination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class SimpleSourceDestinationSpringMapperTest {

    @Autowired
    private SimpleSourceDestinationSpringMapper simpleSourceDestinationSpringMapper;

    @Test
    public void givenSimpleSourceToSimpleDestination_whenMaps_thenCorrect() {

        SimpleSource simpleSource = new SimpleSource();
        simpleSource.setName("SourceName");
        simpleSource.setDescription("SourceDescription");

        SimpleDestination destination = simpleSourceDestinationSpringMapper.sourceToDestination(simpleSource);

        assertEquals(simpleSource.getName(), destination.getName());
        assertEquals(simpleSource.getDescription(), destination.getDescription());
    }

    @Test
    public void givenSimpleDestinationToSourceDestination_whenMaps_thenCorrect() {
        SimpleDestination destination = new SimpleDestination();
        destination.setName("DestinationName");
        destination.setDescription("DestinationDescription");

        SimpleSource source = simpleSourceDestinationSpringMapper.destinationToSource(destination);

        assertEquals(destination.getName(), source.getName());
        assertEquals(destination.getDescription(), source.getDescription());
    }

}
