package com.tushar.aem.guides.core.elastic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BasicSearch {
 
    public static void main(String[] args) {
        try {
            // Define the URL
            URL url = new URL("https://6168045f338d4ee78f864ecf75a62c29.us-central1.gcp.cloud.es.io:443/local_pages_report_file_uploaded_index/_search");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", 
                "APIKey TXlSaV9wWUJDWDZhZEMxTThBckU6Y3FTR2JtLXlEX2tMenEyTzVsaFhhUQ==");

            // Enable output to send request body
            connection.setDoOutput(true);
            String jsonPayload = "{ \"query\": { \"match\": { \"Title\": \"Iron\" } } }";

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonPayload.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print response
            // println "Response: " + response.toString();

            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
                    
        
}
