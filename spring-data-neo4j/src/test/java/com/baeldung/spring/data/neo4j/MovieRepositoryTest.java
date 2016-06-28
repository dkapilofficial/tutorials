package com.baeldung.spring.data.neo4j;

import com.baeldung.spring.data.neo4j.config.MovieDatabaseNeo4jTestConfiguration;
import com.baeldung.spring.data.neo4j.domain.Movie;
import com.baeldung.spring.data.neo4j.domain.Person;
import com.baeldung.spring.data.neo4j.domain.Role;
import com.baeldung.spring.data.neo4j.repostory.MovieRepository;
import com.baeldung.spring.data.neo4j.repostory.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MovieDatabaseNeo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryTest {
    
    @Autowired
    private MovieRepository instance;

    @Autowired
    private PersonRepository personRepository;
    
    public MovieRepositoryTest() {
    }
    
    @Before 
    public void initializeDatabase() {
       System.out.println("seeding embedded database");
       Movie matrix = new Movie();
       matrix.setTitle("The Italian Job");
       matrix.setReleased(1999);
       instance.save(matrix);
       
       Person mark = new Person();
       mark.setName("Mark Wahlberg");
       personRepository.save(mark);
       
       Role neo = new Role();
       neo.setMovie(matrix);
       neo.setPerson(mark);
       Collection<String> roleNames = new HashSet();
       roleNames.add("Charlie Croker");
       neo.setRoles(roleNames);
       List<Role> roles = new ArrayList();
       roles.add(neo);
       matrix.setRoles(roles);
       instance.save(matrix);
    }

    @Test
    @DirtiesContext
    public void testFindByTitle() {
        System.out.println("findByTitle");
        String title = "The Italian Job";
        Movie result = instance.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    @Test
    @DirtiesContext
    public void testCount() {
        System.out.println("count");
        long result = instance.count();
        assertNotNull(result);
        assertEquals(1, result);
    }

    @Test
    @DirtiesContext
    public void testFindAll() {
        System.out.println("findAll");
        Collection<Movie> result = (Collection<Movie>) instance.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DirtiesContext
    public void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Italian";
        Collection<Movie> result = instance.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1,result.size());
    }

    @Test
    @DirtiesContext
    public void testGraph() {
        System.out.println("graph");
        List<Map<String,Object>> graph = instance.graph(5);
        assertEquals(1,graph.size());
        Map<String,Object> map = graph.get(0);
        assertEquals(2,map.size());
        String[] cast = (String[])map.get("cast");
        String movie = (String)map.get("movie");
        assertEquals("The Italian Job",movie);
        assertEquals("Mark Wahlberg", cast[0]);
    }

    @Test
    @DirtiesContext
    public void testDeleteMovie() {
        System.out.println("deleteMovie");
        instance.delete(
                instance.findByTitle("The Italian Job"));
        assertNull(instance.findByTitle("The Italian Job"));
    }

    @Test
    @DirtiesContext
    public void testDeleteAll() {
        System.out.println("deleteAll");
        instance.deleteAll();
        Collection<Movie> result = (Collection<Movie>) instance.findAll();
        assertEquals(0,result.size());
    }
}
