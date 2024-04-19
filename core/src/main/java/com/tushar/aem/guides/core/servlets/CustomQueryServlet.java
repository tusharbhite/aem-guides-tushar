package com.tushar.aem.guides.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// import groovy.json.JsonBuilder;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.jcr.query.QueryManager;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.PageManager;
import com.tushar.aem.guides.core.utils.PageResult;

@Component(service = Servlet.class, property = {
    "sling.servlet.paths=/bin/customquery"})
public class CustomQueryServlet extends SlingAllMethodsServlet {

private static final String JCR_SQL_PARAM = "jcrSql";
private static final String PAGE_TITLE_PROP = "jcr:title";
private static final String PAGE_PATH_PROP = "sling:resourceType"; // Using sling:resourceType for path
private static final String PRIMARY_IMAGE_PROP = "primaryImage";

@Override
protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
    String jcrSql = null;

    // Securely retrieve JCR-SQL from request body
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        jcrSql = sb.toString();
    } catch (Exception e) {
        response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("Invalid request body format");
        return;
    }

    // Validate JCR-SQL (optional, enhance security)
    // Consider using a JCR-SQL validator library to prevent potential injection attacks.

    ResourceResolver resolver = request.getResourceResolver();
    try {
        Session session = resolver.adaptTo(Session.class);
        if (session == null) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error");
            return;
        }
    JsonBuilderFactory factory = Json.createBuilderFactory(Collections.emptyMap());
    JsonArrayBuilder jab = factory.createArrayBuilder();


        List<String> resultList=getResultList(jcrSql,session);
        
    PageResult  pageResultObjArray []=new PageResult[200];
    for(int i=0;i<resultList.size();i++ ){
        String nodePath =  resultList.get(i);
        Node pageNode=resolver.getResource(nodePath).adaptTo(Node.class);
        Node contentNode=pageNode.getNode("jcr:content");

            String type="";
            if(pageNode.getPath().contains("movie")){
                type="Movie";
            }
            if(pageNode.getPath().contains("song")){
                type="Song";
            }

          JsonObjectBuilder obj = Json.createObjectBuilder();
          obj.add("pageTitle",getPropertyAsString(contentNode, "jcr:title"));
          obj.add("pagePath",pageNode.getPath());
          obj.add("primaryImage",getPropertyAsString(contentNode,"primaryimage"));
          obj.add("type",type);
          obj.build();
          jab.add(obj);

    }
    //jab.build().toString();

        response.setContentType("application/json");
        response.getWriter().write(jab.build().toString());
    } catch (RepositoryException e) {
        response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Error processing query#"+jcrSql+"#"+e.getMessage()+"!!!!!!!!!!!!!!");
    } finally {
        if (resolver != null) {
            resolver.close();
        }
    }
}

private String getPropertyAsString(Node node, String propertyName) throws RepositoryException {
    if(node.hasProperty(propertyName)){
        Property property = node.getProperty(propertyName);
        if (property != null) {
            return property.getValue().getString();
        }
    }

    return "NA";
}
List <String> getResultList(String queryString, Session session) throws RepositoryException{
    QueryManager qm = session.getWorkspace().getQueryManager();
    List <String> resultList=new ArrayList<>(); 
    Query query = qm.createQuery(queryString,Query.JCR_SQL2);
    QueryResult result = query.execute();
    NodeIterator ne= result.getNodes();
    while (ne.hasNext()) {
       Node n=  ne.nextNode();
       resultList.add(n.getPath());
    }    
    return resultList;
    
}

@Override
protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
    response.getWriter().write("GET REQUEST SUCCESSFUL");

}

}
