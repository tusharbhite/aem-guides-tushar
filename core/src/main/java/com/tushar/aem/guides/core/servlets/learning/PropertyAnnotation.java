package com.tushar.aem.guides.core.servlets.learning;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service= {Servlet.class},
        property={
                "sling.servlet.methods="+ HttpConstants .METHOD_GET,
                "sling.servlet.methods="+ HttpConstants .METHOD_POST,
                "sling.servlet.resourceTypes="+ "tushar/components/page",
                "sling.servlet.paths=" + "/bin/pathtypeservlet",
                "sling.servlet.selectors=" + "ds",
                "sling.servlet.extensions"+"="+"txt"
        })
public class PropertyAnnotation extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyAnnotation.class);


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        // Hit 
        // http://localhost:4502/bin/pathtypeservlet.ds.txt
        // http://localhost:4502/bin/pathtypeservlet
        // http://localhost:4502/content/tushar/us/en/home/about-us/jcr:content.ds.txt
        response.getWriter().write("Hello from Property Annotation Servlet class"+request.getResource().getResourceType());

    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
       try {
            LOG.info("\n ------------------------STARTED POST-------------------------");
            List<RequestParameter> requestParameterList=request.getRequestParameterList();
            for(RequestParameter requestParameter : requestParameterList){
                LOG.info("\n ==PARAMETERS===>  {} : {} ",requestParameter.getName(),requestParameter.getString());
            }
        }catch (Exception e){
            LOG.info("\n ERROR IN REQUEST {} ",e.getMessage());
        }
        response.getWriter().write("======FORM SUBMITTED========");
        response.getWriter().write("Post Method Property Annotation Servlet class"+request.getResource().getResourceType());

    }
}