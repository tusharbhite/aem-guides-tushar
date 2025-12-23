package com.tushar.aem.guides.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class MyResourceTypesServletTest {

    private final AemContext context = new AemContext();
    private MyResourceTypesServlet servlet;

    private static final String RESOURCE_PATH = "/content/tushar/page";
    private static final String RESOURCE_TYPE = "tushar/components/page";

    @BeforeEach
    void setUp() {
        servlet = new MyResourceTypesServlet();

        // 1. Create a resource that matches the servlet's resourceType
        context.create().resource(RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);

        // 2. Set the current resource in the context
        context.currentResource(RESOURCE_PATH);

        // 3. Set the selector and extension to match the servlet registration
        context.requestPathInfo().setSelectorString("hello");
        context.requestPathInfo().setExtension("html");
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        // 4. Invoke the servlet
        servlet.doGet(context.request(), context.response());

        // 5. Build expected string
        String expected = "Hello from SlingServletResourceTypesServlet class" + RESOURCE_TYPE;

        // 6. Verify response
        assertEquals(expected, context.response().getOutputAsString(),
                "Servlet response output does not match expected string");
    }
}
