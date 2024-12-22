package com.tushar.aem.guides.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.*;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(service = Servlet.class,
        property = {
                SLING_SERVLET_PATHS + "=/bin/custom/query",
        })
@ServiceDescription("Query Servlet to fetch node paths and properties")
@ServiceVendor("Your Company")
public class CustomQueryServletNew extends SlingAllMethodsServlet {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        String queryString = request.getParameter("query");
        String[] propertyNames = request.getParameterValues("properties");

        if (queryString == null || queryString.isEmpty()) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Query string is required.");
            return;
        }

        List<Map<String, Object>> results = new ArrayList<>();
        try {
            // Get JCR session and Query Manager
            Session session = resourceResolver.adaptTo(Session.class);
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
            QueryResult queryResult = query.execute();
            NodeIterator nodeIterator = queryResult.getNodes();
            

            while (nodeIterator.hasNext()) {
                Node node = nodeIterator.nextNode();  // Fetch the next node
                try {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("path", node.getPath());

                    if (propertyNames != null) {
                        Map<String, Object> propertiesMap = new HashMap<>();
                        for (String propName : propertyNames) {
                            if (node.hasProperty(propName)) {
                                javax.jcr.Property property = node.getProperty(propName);
                                if (property.isMultiple()) {
                                    propertiesMap.put(propName, Arrays.asList(property.getValues()));
                                } else {
                                    propertiesMap.put(propName, property.getValue().getString());
                                }
                            }
                        }
                        resultMap.put("properties", propertiesMap);
                    }

                    results.add(resultMap);
                } catch (RepositoryException e) {
                    // Handle exception
                }
            }

            // Convert result list to JSON
            String jsonResponse = objectMapper.writeValueAsString(results);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);

        } catch (RepositoryException e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error executing query: " + e.getMessage());
        }
    }
}
