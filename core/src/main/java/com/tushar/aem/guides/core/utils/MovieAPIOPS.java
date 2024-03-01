package com.tushar.aem.guides.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MovieAPIOPS {
    private static final String BASE_URL = "https://www.movieposterdb.com/autoLazy?q=";

    public static String getPosterUrlFromMovieName(String movieName) throws IOException{
        String modifiedUrl="Not Found";
        String encodedMovieName = movieName.replaceAll(" ", "%20");

        String url = BASE_URL + encodedMovieName + "&type=title&page=1&items=1";

        // Connect to the URL and read the JSON content
        String jsonContent = getUrlContent(url);

        // Parse the JSON content
        JsonObject jsonData = parseJson(jsonContent);

        // Extract and modify poster URL, if available
        if (jsonData != null && jsonData.has("results")) {
          JsonArray resultsArray = jsonData.getAsJsonArray("results");
          if (resultsArray.size() > 0) {
              JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
              if (firstResult.has("primary_poster") && firstResult.get("primary_poster").getAsJsonObject().has("file_location")) {
                  String fileLocation = firstResult.get("primary_poster").getAsJsonObject().get("file_location").getAsString();
                   modifiedUrl = modifyUrl(fileLocation);
      
                  System.out.println("Modified URL: " + modifiedUrl);
                  System.out.println("File location: " + fileLocation);
              }
          
      } else {
                System.out.println("Error: Key 'file_location' not found in primary poster object.");
                modifiedUrl="Error: Key 'file_location' not found in primary poster object";
            }
        } else {
            System.out.println("Error: 'results' key not found in JSON data or 'results' array is empty.");
            modifiedUrl="Error: 'results' key not found in JSON data";
        }
        return modifiedUrl;
      }

       private static String getUrlContent(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Error: HTTP response code " + connection.getResponseCode());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
        finally {
            connection.disconnect(); // Terminate the connection here
        }
    }

    private static JsonObject parseJson(String jsonContent) throws IOException {
         // Use Gson for JSON parsing
        Gson gson = new Gson();
        return gson.fromJson(jsonContent, JsonObject.class);
    }

    private static String modifyUrl(String fileLocation) {
        String[] parts = fileLocation.split("/");
        parts[2] = "xl.movieposterdb.com";
        parts[parts.length - 1] = parts[parts.length - 1].replaceFirst("t_", "xl_");
        return String.join("/", parts);
    }

}
