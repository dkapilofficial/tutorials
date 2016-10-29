package com.baeldung.java.networking.url;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class UrlTest {

    @Test
    public void givenUrl_whenCanIdentifyProtocol_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com");
        assertEquals("http", url.getProtocol());
    }

    @Test
    public void givenUrl_whenCanGetHost_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com");
        assertEquals("baeldung.com", url.getHost());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect2() throws MalformedURLException {
        URL url = new URL("http://baeldung.com/articles?topic=java&version=8");
        assertEquals("/articles?topic=java&version=8", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect1() throws MalformedURLException {
        URL url = new URL("http://baeldung.com/guidelines.txt");
        assertEquals("/guidelines.txt", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetPathParams_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com/articles?topic=java&version=8");
        assertEquals("/articles", url.getPath());
    }

    @Test
    public void givenUrl_whenCanGetQueryParams_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com/articles?topic=java");
        assertEquals("topic=java", url.getQuery());
    }

    @Test
    public void givenUrl_whenGetsDefaultPort_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com");
        assertEquals(-1, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenUrl_whenGetsPort_thenCorrect() throws MalformedURLException {
        URL url = new URL("http://baeldung.com:8090");
        assertEquals(8090, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenBaseUrl_whenCreatesRelativeUrl_thenCorrect() throws MalformedURLException {
        URL baseUrl = new URL("http://baeldung.com");
        URL relativeUrl = new URL(baseUrl, "a-guide-to-java-sockets");
        assertEquals("http://baeldung.com/a-guide-to-java-sockets", relativeUrl.toString());
    }

    @Test
    public void givenAbsoluteUrl_whenIgnoresBaseUrl_thenCorrect() throws MalformedURLException {
        URL baseUrl = new URL("http://baeldung.com");
        URL relativeUrl = new URL(baseUrl, "http://baeldung.com/a-guide-to-java-sockets");
        assertEquals("http://baeldung.com/a-guide-to-java-sockets", relativeUrl.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        String protocol = "http";
        String host = "baeldung.com";
        String file = "/guidelines.txt";
        URL url = new URL(protocol, host, file);
        assertEquals("http://baeldung.com/guidelines.txt", url.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect2() throws MalformedURLException {
        String protocol = "http";
        String host = "baeldung.com";
        String file = "/articles?topic=java&version=8";
        URL url = new URL(protocol, host, file);
        assertEquals("http://baeldung.com/guidelines.txt", url.toString());
    }

    @Test
    public void givenUrlComponentsWithPort_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        String protocol = "http";
        String host = "baeldung.com";
        int port = 9000;
        String file = "/guidelines.txt";
        URL url = new URL(protocol, host, port, file);
        assertEquals("http://baeldung.com:9000/guidelines.txt", url.toString());
    }

}
