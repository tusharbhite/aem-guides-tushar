package com.tushar.aem.guides.core.servlets;
import com.tushar.aem.guides.core.utils.MovieAPIOPS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * SimpleServlet
 */
@Component(service={Servlet.class})
@SlingServletPaths("/bin/songs")
public class SongsServlet extends SlingSafeMethodsServlet {

    @Override
     protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
      {
        ResourceResolver resolver=request.getResourceResolver();
        String lyrics = request.getParameter("lyrics");
        String searchString = request.getParameter("searchString");
        String url = "https://www.jiosaavn.com/api.php?__call=autocomplete.get&_format=json&_marker=0&cc=in&includeMetaTags=1&query=";
        if(searchString==null || searchString == "")
        url=url+"dhoom";
        else
        url=url+searchString;
        // Connect to the URL and get the document
    Document doc = Jsoup.connect(url).get();

    // Get the body element
    String bodyText = doc.body().text();

    System.out.println("Body content:");
    System.out.println(bodyText);

        response.getWriter().println(bodyText);
      }
      
      
}