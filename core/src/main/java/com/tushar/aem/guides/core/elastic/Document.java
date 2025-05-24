package com.tushar.aem.guides.core.elastic;

import com.google.gson.Gson;

class Document {
    public String Title;
    public String Path;
    public String Unique_ID;
    public String Live_URL;
    public String Page_Type;
    public String Primary_Image;
    public String Tagline;

    public Document(String Title, String Path, String Unique_ID, String Live_URL, String Page_Type, String Primary_Image, String Tagline) {
    this.Title = Title;
    this.Path =Path;
    this.Unique_ID= Unique_ID;
    this.Live_URL= Live_URL;
    this.Page_Type =Page_Type;
    this.Primary_Image= Primary_Image;
    this.Tagline= Tagline;
    }
}
//Usage
// Document person = new Document("Alice", "path", "uid","LU","PT","PI","TL");
//         Gson gson = new Gson();
//         String jsonString = gson.toJson(person);
//         println jsonString ;