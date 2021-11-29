package com.marionete.rest.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestParser {
    public static String[] getUserNameAndPassword(String request) {
        try {
            var objectMapper = new ObjectMapper();
            var jsonNode = objectMapper.readTree(request);
            if (jsonNode.get("username") == null || jsonNode.get("password") == null) {
                return null;
            } else {
                return new String[] {jsonNode.get("username").asText(), jsonNode.get("password").asText()};
            }
        } catch(Exception e) {
            return null;
        }
    }
}
