package com.tushar.aem.guides.core.elastic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class ElasticAPIRequestUtil {
    public static String sendHttpRequest(String urlString, String method, String jsonBody, String apiKey) {
// def sendHttpRequest(String urlString, String method, String jsonBody, String apiKey) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set HTTP method (POST, PUT, DELETE)
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "APIKey " + apiKey);
            
            // Handle request body for POST and PUT
            if (!method.equalsIgnoreCase("DELETE")) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                try  {
                    OutputStream os = connection.getOutputStream();
                    os.write(jsonBody.getBytes("UTF-8"));
                    os.flush();
                }catch(Exception e){
                    
                }
            }

            // Get response
            int responseCode = connection.getResponseCode();
            // println responseCode
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Return response
            return response.toString();

         


        } catch (Exception e) {
            // println e.getMessage();
            return null;
        }
    }

}
