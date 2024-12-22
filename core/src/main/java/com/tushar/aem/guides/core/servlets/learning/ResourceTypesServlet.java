package com.tushar.aem.guides.core.servlets.learning;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
    resourceTypes="tushar/components/page", 
    methods= "GET",
    extensions="html",
    selectors="hello")
public class ResourceTypesServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        // Hit http://localhost:4502/content/tushar/us/en/home/about-us/jcr:content.hello.html
        response.getWriter().write("Hello from SlingServletResourceTypesServlet class"+request.getResource().getResourceType());
    }
}