package com.tushar.aem.guides.core.servlets;


import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class MySlingAllMethodsServletTest {

    private final AemContext context = new AemContext();
    private MySlingAllMethodsServlet servlet;

    private static final String PAGE_PATH = "/content/tushar/page";
    private static final String RESOURCE_TYPE = "tushar/components/page";

    @BeforeEach
    void setUp() {
        servlet = new MySlingAllMethodsServlet();

        // Create the resource to test ResourceType-based resolution
        context.create().resource(PAGE_PATH, "sling:resourceType", RESOURCE_TYPE);
    }

    @Test
    void testDoGet_ResourceType() throws ServletException, IOException {
        // Prepare context for ResourceType hit
        context.currentResource(PAGE_PATH);
        context.requestPathInfo().setSelectorString("ds");
        context.requestPathInfo().setExtension("txt");

        servlet.doGet(context.request(), context.response());

        String output = context.response().getOutputAsString();
        assertTrue(output.contains("Hello from Property Annotation Servlet class"));
        assertTrue(output.contains(RESOURCE_TYPE));
    }

    @Test
    void testDoGet_PathBased() throws ServletException, IOException {
        // For path-based, the resource resolved is often the path itself or a synthetic resource
        context.request().setPathInfo("/bin/pathtypeservlet");

        // In MockSling, we should still provide a resource to avoid NPE if the servlet calls getResource()
        context.currentResource(context.create().resource("/bin/pathtypeservlet", "sling:resourceType", "sling:synthetic"));

        servlet.doGet(context.request(), context.response());

        String output = context.response().getOutputAsString();
        assertTrue(output.contains("Hello from Property Annotation Servlet class"));
    }

    @Test
    void testDoPost() throws ServletException, IOException {
        context.currentResource(PAGE_PATH);

        // Cast to MockRequest to add parameters for request.getRequestParameterList()
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
        request.setMethod("POST");
//        request.setParameter("firstName", "Tushar");
//        request.setParameter("lastName", "AEM");
        context.request().setParameterMap(ImmutableMap.of(
                "firstName", "Tushar",
                "lastName", "AEM"
        ));

        servlet.doPost(context.request(), context.response());

        String output = context.response().getOutputAsString();
        assertTrue(output.contains("======FORM SUBMITTED========"));
        assertTrue(output.contains("Post Method Property Annotation Servlet class" + RESOURCE_TYPE));
    }
}