package com.tushar.aem.guides.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RunApp {
    String pageTitle;
    String pagePath;
    String primaryImage;

    public static void main(String [] args){
        System.out.println("He;p");
        BufferedReader reader;
        int count =0;
        try{
            reader=new BufferedReader(new FileReader("core/src/main/java/com/tushar/aem/guides/core/utils/pages.txt"));
            String line=reader.readLine();
            while (line!=null) {
                count++;
				String expectedURL="http://localhost:8080"+line+".html";
             System.out.println(count+" "+expectedURL);
             URL url = new URL(expectedURL);
             HttpURLConnection connection=(HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.disconnect();

             line=reader.readLine();   
                
            }
            reader.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
