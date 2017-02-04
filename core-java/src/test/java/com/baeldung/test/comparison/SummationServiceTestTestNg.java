package com.baeldung.test.comparison;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SummationServiceTestTestNg extends TestNG{
	
	private List<Integer> numbers;
	
	private int testCount=0;
	
	@BeforeClass
	public void initialize() {
		numbers = new ArrayList<>();
	}

	@AfterClass
	public void tearDown() {
		numbers = null;
	}

	@BeforeMethod
	public void runBeforeEachTest() {
	   testCount++;
	}

	@AfterMethod
	public void runAfterEachTest() {
		
	}
	
	@BeforeGroups("negative_tests")
	public void runBeforeEachNegativeGroup() {
	    numbers.clear();
	}
	
	@BeforeGroups("regression")
	public void runBeforeEachRegressionGroup() {
	    numbers.add(-11);
	    numbers.add(2);
	}
	
	@BeforeGroups("positive_tests")
	public void runBeforeEachPositiveGroup() {
	    numbers.add(1);
	    numbers.add(2);
	    numbers.add(3);
	}
	 
	@AfterGroups("positive_tests,regression,negative_tests")
	public void runAfterEachGroup() {
	    numbers.clear(); 
	}
	
	@Test(groups="positive_tests",enabled=false)
    public void givenNumbers_sumEquals_thenCorrect() {
        int sum = 0;
        for (int num : numbers)
            sum += num;
        Assert.assertEquals(sum, 6);
    }
	
	@Test(groups="negative_tests")
	public void givenEmptyList_sumEqualsZero_thenCorrect(){
		 int sum = 0;
	        for (int num : numbers)
	            sum += num;
	        Assert.assertEquals(0, sum);
	}
	
	@Test(groups = "regression")
	public void givenNegativeNumber_sumLessthanZero_thenCorrect() {
	    int sum = 0;
	    for (int num : numbers)
	        sum += num;
	    System.out.println(sum);
	    Assert.assertTrue(sum<0);;
	}
	
	@Test(groups="sanity")
	public void givenNumbers_doSum(){
		
	}
	
	@Test(expectedExceptions = ArithmeticException.class) 
	public void calculateWithException() { 
	    int i = 1/0;
	}
	
	
}
