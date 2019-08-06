package com.baeldung.accessmodifiers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import com.baeldung.accessmodifiers.publicmodifier.ListOfThree;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@TestInstance(Lifecycle.PER_CLASS)
public class PublicAccessModifierUnitTest {

    @Test
    public void whenUsingBigDecimalIntValueMethod_correspondingIntIsReturned() {
        assertEquals(0, new BigDecimal(0).intValue()); //instance member
    }
    
    @Test
    public void whenUsingIntegerMaxValueField_maxPossibleIntValueIsReturned() {
        assertEquals(2147483647, Integer.MAX_VALUE); //static field
    }
    
    @Test
    public void whenUsingStringToLowerCase_stringTurnsToLowerCase() {
        assertEquals("alex", "ALEX".toLowerCase());
    }

    @Test
    public void whenParsingStringOne_parseIntReturns1() {
        assertEquals(1, Integer.parseInt("1"));
    }
    
    @Test
    public void whenConnectingToH2_connectionInstanceIsReturned() throws SQLException {

        final String url = "jdbc:h2:~/test";
        Connection conn = DriverManager.getConnection(url, "sa", "");
        assertNotNull(conn);
    }

    @Test
    public void whenCreatingCustomList_concreteAndInheritedMethodsWork() {

        String[] dataSet1 = new String[] {"zero", "one", "two"};
        
        List<String> list1 = new ListOfThree<String>(dataSet1);
        
        //our implemented methods
        assertEquals("one", list1.get(1));
        assertEquals(3, list1.size());

        //inherited implementations
        assertEquals(1, list1.indexOf("one"));
        
        String[] dataSet2 = new String[] {"two", "zero", "one"};
        List<String> list2 = new ListOfThree<String>(dataSet2);
        
        assertTrue(list1.containsAll(list2));
    }
    
}
