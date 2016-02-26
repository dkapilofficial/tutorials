package com.baeldung.jpa.storedprocedure;

import com.baeldung.jpa.model.Car;
import org.junit.*;

import javax.persistence.*;

/**
 * Created by Giuseppe Bueti on 23/02/2016.
 */
public class StoredProcedureTest {

    private static EntityManagerFactory factory = null;
    private static EntityManager entityManager = null;

    @BeforeClass
    public static void init() {
        factory = Persistence.createEntityManagerFactory("jpa-db");
        entityManager = factory.createEntityManager();
    }

    @Before
    public void setup() {
    }

    @Test
    public void createCarTest() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Car car = new Car("Ford Mustang", 2015);
            entityManager.persist(car);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getCause());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Test
    public void findCarsByYear() {
        final StoredProcedureQuery findByYearProcedure = entityManager.createNamedStoredProcedureQuery("findByYearProcedure");
        StoredProcedureQuery storedProcedure = findByYearProcedure.setParameter("p_year", 2015);
        storedProcedure.getResultList().forEach(c -> Assert.assertEquals(new Integer(2015), ((Car) c).getYear()));
    }

    @Test
    public void findCarsByModel() {
        final StoredProcedureQuery findByModelProcedure = entityManager.createNamedStoredProcedureQuery("findByModelProcedure");
        StoredProcedureQuery storedProcedure = findByModelProcedure.setParameter("p_model", "Camaro");
        storedProcedure.getResultList().forEach(c -> Assert.assertEquals("Camaro", ((Car) c).getModel()));
    }

    @AfterClass
    public static void destroy() {

        if (entityManager != null) {
            entityManager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }
}
