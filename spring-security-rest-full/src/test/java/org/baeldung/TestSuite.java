package org.baeldung;

import org.baeldung.persistence.PersistenceTestSuite;
import org.baeldung.security.SecurityTestSuite;
import org.baeldung.web.LiveTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    PersistenceTestSuite.class
    ,SecurityTestSuite.class
    ,LiveTestSuite.class
}) //
public class TestSuite {

}
