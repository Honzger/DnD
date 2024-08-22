package main.code;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {

    private String jsonFilePath = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/JSOON/";

    public JsonReader(String Flail) {
        this.jsonFilePath = this.jsonFilePath + Flail;
    }

    // Method to read the JSON file and extract numbers for a specific file name
    public int[] getNumbersForFileName(String targetFileName) {
        try {
            // Read the JSON file into a String
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            // Parse the JSON content
            JSONArray jsonArray = new JSONArray(jsonContent.toString());

            // Loop through the array and find the object with the target file name
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fileName = jsonObject.getString("string");

                if (fileName.equals(targetFileName)) {
                    int firstInt = jsonObject.getInt("firstInt");
                    int secondInt = jsonObject.getInt("secondInt");

                    // Return the extracted numbers
                    return new int[]{firstInt, secondInt};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return null or an appropriate default if the file name is not found
        return null;
    }
}
