package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json);

    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(fileName));
            var strategy = new ColumnPositionMappingStrategy<Employee>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            var csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    public static void writeString(String jsonString) {
        try (FileWriter fileWriter = new FileWriter("data.json")) {
            fileWriter.write(jsonString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
