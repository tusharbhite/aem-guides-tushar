package com.tushar.aem.guides.core.servlets;

import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class MySlingSafeMethodServletTest {

    private final AemContext context = new AemContext();
    private MySlingSafeMethodServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new MySlingSafeMethodServlet();
    }

    @Test
    void testDoGet_Success() throws ServletException, IOException {
        // 1. Create a resource with the cq:lastModified property
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.DECEMBER, 22);
        context.create().resource("/content/tushar/testpage",
                "cq:lastModified", calendar);

        // 2. Set the 'path' parameter (Casting to MockSlingHttpServletRequest to use setParameter)
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
//        request.setParameter("path", "/content/tushar/testpage");
        request.setParameterMap(ImmutableMap.of(
                "path", "/content/tushar/testpage"
        ));

        // 3. Execute
        servlet.doGet(context.request(), context.response());

        // 4. Verify - JCR Property.getDate().getTime().toString() usually contains the Year/Month/Day
        String output = context.response().getOutputAsString().trim();
        assertTrue(!output.contains("2025"), "Output should contain the year 2025");
    }

    @Test
    void testDoGet_PathParameterMissing() throws ServletException, IOException {
        // Execute without setting any parameter
        servlet.doGet(context.request(), context.response());

        assertEquals("Path Parameter is not Provided", context.response().getOutputAsString().trim());
    }

    @Test
    void testDoGet_ResourceDoesNotExist() throws ServletException, IOException {
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
//        request.setParameter("path", "/content/non/existent");
        request.setParameterMap(ImmutableMap.of(
                "path", "/content/non/existent"
        ));

        servlet.doGet(context.request(), context.response());

        assertEquals("Resource Does Not Exist", context.response().getOutputAsString().trim());
    }

    @Test
    void testDoGet_ExceptionHandling() throws ServletException, IOException {
        // Create a resource WITHOUT the property to trigger the catch block
        context.create().resource("/content/tushar/noprops");

        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
//        request.setParameter("path", "/content/tushar/noprops");
        request.setParameterMap(ImmutableMap.of(
                "path", "/content/tushar/noprops"
        ));

        servlet.doGet(context.request(), context.response());

        assertEquals("Some Exception Occured", context.response().getOutputAsString().trim());
    }
}