package com.tushar.aem.guides.core.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

/**
 * SimpleServlet
 */
@Component(service={Servlet.class})
@SlingServletPaths("/bin/lastmodified")
public class MySlingSafeMethodServlet extends SlingSafeMethodsServlet {
    @Override
     protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
      {
        ResourceResolver resolver=request.getResourceResolver();
        String path = request.getParameter("path");
        if(path==null){
            response.getWriter().println("Path Parameter is not Provided");
        }else if(resolver.getResource(path)==null) {
            response.getWriter().println("Resource Does Not Exist");
        }else if(resolver.getResource(path)!=null){
            Resource res = resolver.getResource(path);
            Node node = res.adaptTo(Node.class);
            String lastModified = null;
            try {
//                lastModified = node.getProperty("cq:lastModified")?.getDate()?.getTime().toString();
                Property prop = node.getProperty("cq:lastModified");
                Calendar cal = prop.getDate();
                if (cal != null) {
                    // getTime() returns a java.util.Date object
                    lastModified = cal.getTime().toString();
                }

            } catch (Exception e) {
                lastModified = "Some Exception Occured";
            }
            response.getWriter().println(lastModified);
        }
      }

}