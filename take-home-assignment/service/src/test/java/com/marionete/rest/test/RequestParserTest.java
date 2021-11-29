package com.marionete.rest.test;

import com.marionete.rest.rest.service.RequestParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParserTest {

    @Test
    void emptyJson() {
        assertEquals(RequestParser.getUserNameAndPassword(""), null);
        assertEquals(RequestParser.getUserNameAndPassword("{}"), null);
        assertEquals(RequestParser.getUserNameAndPassword("{\"\"}"), null);
        assertEquals(RequestParser.getUserNameAndPassword("{\"\":\"\"}"), null);
    }

    @Test
    void invalidJson() {
        assertEquals(RequestParser.getUserNameAndPassword("abcd"), null);
        assertEquals(RequestParser.getUserNameAndPassword("{\"abcd\"}"), null);
        assertEquals(RequestParser.getUserNameAndPassword("{\"abcd\":\"efgh\"}"), null);
    }

    @Test
    void partiallyInvalidJson() {
        assertEquals(RequestParser.getUserNameAndPassword("{\"username\":\"abcd\"}"), null);
        assertEquals(RequestParser.getUserNameAndPassword("{\"password\":\"efgh\"}"), null);
    }

    @Test
    void validJson() {
        String[] params = RequestParser.getUserNameAndPassword("{\"username\":\"regi\", \"password\":\"Password1*\"}");
        assertEquals("regi", params[0]);
        assertEquals("Password1*", params[1]);
    }

}
