package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConverterFiles {


    public static String ConverterYAML(String dirResult, String name, String dirName) {
        String dirFile = dirResult + "/" + name;
        String fileName = name.substring(0, name.indexOf(".")) + ".yaml";
        File file = new File(dirName + "/" + fileName);
        String yaml = null;
        try {
            file.createNewFile();
            String json = ReadFromFile.readToString(dirFile);
            yaml = asYaml(json);
            writeToFile(file, yaml);
            return yaml;
        } catch (IOException e) {
            return yaml;
        }
    }


    public static String ConverterJSON(String dirResult, String name, String dirName) {
        String dirFile = dirResult + "/" + name;
        String fileName = name.substring(0, name.indexOf(".")) + ".json";
        File file = new File(dirName + "/" + fileName);
        String json = null;
        try {
            file.createNewFile();
            String yaml = ReadFromFile.readToString(dirFile);
            json = asJson(yaml);
            writeToFile(file, json);
        } catch (IOException e) {
            return json;
        }
        return json;
    }

    private static String asJson(String yamlString) {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj;
        try {
            obj = yamlReader.readValue(yamlString, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static String asYaml(String jsonString) {
        try {
            JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
            String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
            return jsonAsYaml;
        } catch (IOException e) {
            return "";
        }
    }

    public static boolean writeToFile(File file, String text) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(text);
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }


    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
