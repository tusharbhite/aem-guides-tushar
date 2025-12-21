package com.tushar.aem.guides.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import com.tushar.aem.guides.core.services.MyServiceInterface;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * SimpleServlet
 */
@Component(service={Servlet.class})
@SlingServletPaths("/bin/resourcemap")
public class MyPathTypeServlet extends SlingSafeMethodsServlet {

    @Reference
    MyServiceInterface myService;

    @Override

     protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
      {
        ResourceResolver resolver=request.getResourceResolver();
        String path = request.getParameter("path");
        String mappedURLString=resolver.map(path)+"  "+myService.getRandomActivity();
        response.getWriter().println(mappedURLString);
      } 
    // Hit 
    // http://localhost:4502/bin/resourcemap?path=content/tushar/us/en/home/about-us/jcr:content
}