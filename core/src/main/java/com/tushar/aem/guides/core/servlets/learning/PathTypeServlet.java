package com.tushar.aem.guides.core.servlets.learning;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

/**
 * SimpleServlet
 */
@Component(service={Servlet.class})
@SlingServletPaths("/bin/resourcemap")
public class PathTypeServlet extends SlingSafeMethodsServlet {
    @Override
     protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
      {
        ResourceResolver resolver=request.getResourceResolver();
        String path = request.getParameter("path");
        String mappedURLString=resolver.map(path);
        response.getWriter().println(mappedURLString);
      } 
    // Hit 
    // http://localhost:4502/bin/resourcemap?path=content/tushar/us/en/home/about-us/jcr:content
}