package com.baeldung.doublecolumn;

import com.baeldung.doublecolumn.function.TriFunction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;

import static com.baeldung.doublecolumn.ComputerUtils.*;

public class TestComputerUtils {


    @Before
    public void setup (){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void testFilter(){

        Computer c1=new Computer(2015,"white");
        Computer c2=new Computer(2009,"black");
        Computer c3=new Computer(2014,"black");

        BiFunction<Integer,String,Computer> c4Function=Computer::new;
        Computer c4=c4Function.apply(2013,"white");
        BiFunction<Integer,String,Computer> c5Function=Computer::new;
        Computer c5=c5Function.apply(2010,"black");
        BiFunction<Integer,String,Computer> c6Function=Computer::new;
        Computer c6=c6Function.apply(2008,"black");

        List<Computer> inventory = Arrays.asList(c1,c2,c3,c4,c5,c6);

        List<Computer> blackComputer = filter(inventory, blackPredicate);
        Assert.assertEquals("The black Computers are: ",blackComputer.size(),4);

        List<Computer> after2010Computer = filter(inventory, after2010Predicate);
        Assert.assertEquals("The Computer bought after 2010 are: ",after2010Computer.size(),3);

        List<Computer> before2011Computer = filter(inventory, c -> c.getAge() < 2011);
        Assert.assertEquals("The Computer bought before 2011 are: ",before2011Computer.size(),3);

        inventory.sort(Comparator.comparing(Computer::getAge));

        Assert.assertEquals("Oldest Computer in inventory", c6,inventory.get(0) );

    }

    @Test
    public void testRepair() {

        Computer c1 = new Computer(2015, "white", 35);
        Computer c2 = new Computer(2009, "black", 65);
        TriFunction<Integer, String, Integer, Computer> c6Function = Computer::new;
        Computer c3 = c6Function.apply(2008, "black", 90);

        List<Computer> inventory = Arrays.asList(c1, c2, c3);
        inventory.forEach(ComputerUtils::repair);

        Assert.assertEquals("Computer repaired", new Integer(100), c1.getHealty());
    }

}
