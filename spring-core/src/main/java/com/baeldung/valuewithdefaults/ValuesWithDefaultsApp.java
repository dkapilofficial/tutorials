package com.baeldung.valuewithdefaults;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

/**
 * Demonstrates setting defaults for @Value annotation.  Note that there are no properties 
 * defined in the specified property source.  We also assume that the user here
 * does not have a system property named some.key.
 *
 */
@Configuration
@PropertySource(name = "myProperties", value = "valueswithdefaults.properties")
public class ValuesWithDefaultsApp {

    @Value("${some.key:my default value}")
    private String stringWithDefaultValue;

    @Value("${some.key:}")
    private String stringWithBlankDefaultValue;

    @Value("${some.key:true}")
    private boolean booleanWithDefaultValue;
    
    @Value("${some.key:42}")
    private int intWithDefaultValue;  

    @Value("${some.key:one,two,three}")
    private String[] stringArrayWithDefaults;

    @Value("${some.key:1,2,3}")
    private int[] intArrayWithDefaults;

    @Value("#{systemProperties['some.key'] ?: 'my default system property value'}")
    private String spelWithDefaultValue;
    

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ValuesWithDefaultsApp.class);
    }

    @PostConstruct
    public void afterInitialize() {
    	// strings
    	Assert.isTrue(stringWithDefaultValue.equals("my default value"));
    	Assert.isTrue(stringWithBlankDefaultValue.equals(""));
    	
    	// other primitives
    	Assert.isTrue(booleanWithDefaultValue);
    	Assert.isTrue(intWithDefaultValue == 42);
    	
    	// arrays
    	List<String> stringListValues = Lists.newArrayList("one", "two", "three");
    	Assert.isTrue(Arrays.asList(stringArrayWithDefaults).containsAll(stringListValues));

    	List<Integer> intListValues = Lists.newArrayList(1, 2, 3);
    	Assert.isTrue(Ints.asList(intArrayWithDefaults).containsAll(intListValues));

    	// SpEL
    	Assert.isTrue(spelWithDefaultValue.equals("my default system property value"));
    	
    }
}
