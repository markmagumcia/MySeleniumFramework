package com.Utilities;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    String currentDirectory = System.getProperty("user.dir");
    

    public static JsonNode loadJson(String path) {
        String userDir = System.getProperty("user.dir");
        Path fileName = Path.of(userDir + path);
        JsonNode jn = null;
        try {
            String str = Files.readString(fileName);
            ObjectMapper mapper = new ObjectMapper();
            jn = mapper.readValue(str, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jn;
    }
  
    public JsonNode convertToJsonNode(String stringToConvert) {
      JsonNode node = null;
      try {
      ObjectMapper mapper = new ObjectMapper();
      node = mapper.readTree(stringToConvert);
      } catch(Exception e) {
        e.printStackTrace();
      }
      return node;
    }
  
}
