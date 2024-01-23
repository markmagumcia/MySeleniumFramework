package com.Utilities;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
d
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {
    String currentDirectory = System.getProperty("user.dir");
    ObjectMapper mapper = new ObjectMapper();

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

    public ObjectNode createParentJSON (){
      ObjectNode rootNode = mapper.createObjectNode();
      return rootNode;
    }
    public ObjectNode createChildJSON(){
      ObjectNode childNode = mapper.createObjectNode();
      return childNode;
    }

    public ObjectNode addFieldsToChild(ObjectNode childNode,String fieldName, String value){
      childNode.put(fieldName,value);
      return childNode;
    }

    public void addChildToRoot(String propertyName, ObjectNode rootNode, ObjectNode childNode){
      rootNode.set(propertyName,childNode);
    }

    public String outputRoot(ObjectNode rootNode) throws JsonProcessingException{
      String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
      return jsonString;
    }

  
}
