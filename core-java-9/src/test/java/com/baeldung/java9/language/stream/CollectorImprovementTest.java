package com.baeldung.java9.language.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class CollectorImprovementTest {
    private static class Blog {
        private String authorName;
        private List<String> comments;        
        
        public Blog(String authorName) {
            this.authorName = authorName;
            this.comments = new LinkedList<String>();
        }
        
        public String getAuthorName() {
            return this.authorName;
        }
        
        public List<String> getComments() {
            return new LinkedList<String>(this.comments);
        }
        
        public void addComment(String comment) {
            this.comments.add(comment);
        }
    }
    
    @Test
    public void givenList_whenSatifyPredicate_thenMapValueWithOccurences() {
        List<Integer> numbers = List.of(1, 2, 3, 5, 5);
 
        Map<Integer, Long> result = numbers.stream()
          .filter(val -> val > 3).collect(Collectors.groupingBy(
          Function.identity(), Collectors.counting()));
     
        assertEquals(1, result.size());
     
        result = numbers.stream().collect(Collectors.groupingBy(
          Function.identity(), Collectors.filtering(val -> val > 3, 
          Collectors.counting())));
     
        assertEquals(4, result.size());
    }
    
    @Test
    public void givenListOfBlogs_whenAuthorName_thenMapAuthorWithComments() {
        Blog blog1 = new CollectorImprovementTest.Blog("1");
        blog1.addComment("Nice");
        blog1.addComment("Very Nice");
        Blog blog2 = new CollectorImprovementTest.Blog("2");
        blog2.addComment("Disappointing");
        blog2.addComment("Ok");
        blog2.addComment("Could be better");
        List<Blog> blogs = List.of(blog1, blog2);
             
        Map<String,  List<List<String>>> authorComments1 = 
          blogs.stream().collect(Collectors.groupingBy(
          Blog::getAuthorName, Collectors.mapping(
          Blog::getComments, Collectors.toList())));
            
        assertEquals(2, authorComments1.size());
        assertEquals(2, authorComments1.get("1").get(0).size());
        assertEquals(3, authorComments1.get("2").get(0).size());
     
        Map<String, List<String>> authorComments2 = 
          blogs.stream().collect(Collectors.groupingBy(
          Blog::getAuthorName, Collectors.flatMapping(
          blog -> blog.getComments().stream(), 
          Collectors.toList())));
     
        assertEquals(2, authorComments2.size());
        assertEquals(2, authorComments2.get("1").size());
        assertEquals(3, authorComments2.get("2").size());
    }
}
