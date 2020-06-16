package com.baeldung.exceptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;


public class TooManyOpenFilesExceptionLiveTest {

    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("testForException", "tmp");
    }

    @AfterEach
    public void tearDown() {
        System.gc();
        tempFile.delete();
    }

    @Test
    public void whenNotClosingResoures_thenIOExceptionShouldBeThrown() {
        try {
            for (int x = 0; x < 1000000; x++) {
                FileInputStream leakyHandle = new FileInputStream(tempFile);
            }
            Assertions.fail("Method Should Have Failed");
        } catch (IOException e) {
            assertTrue(e.getMessage().toLowerCase().contains("too many open files"));
        } catch (Exception e) {
            Assertions.fail("Unexpected exception");
        }
    }

    @Test
    public void whenClosingResoures_thenIOExceptionShouldNotBeThrown() {
        try {
            for (int x = 0; x < 1000000; x++) {
                FileInputStream nonLeakyHandle = null;
                try {
                    nonLeakyHandle = new FileInputStream(tempFile);
                } finally {
                    if (nonLeakyHandle != null) {
                        nonLeakyHandle.close();
                    }
                }
            }
        } catch (IOException e) {
            assertFalse(e.getMessage().toLowerCase().contains("too many open files"));
            Assertions.fail("Method Should Not Have Failed");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception");
        }
    }

    @Test
    public void whenUsingTryWithResoures_thenIOExceptionShouldNotBeThrown() {
        try {
            for (int x = 0; x < 1000000; x++) {
                try (FileInputStream nonLeakyHandle = new FileInputStream(tempFile)) {
                    //Do something with the file
                }
            }
        } catch (IOException e) {
            assertFalse(e.getMessage().toLowerCase().contains("too many open files"));
            Assertions.fail("Method Should Not Have Failed");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception");
        }
    }
}
