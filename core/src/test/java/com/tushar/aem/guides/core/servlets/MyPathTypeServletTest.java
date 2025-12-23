package com.tushar.aem.guides.core.servlets;


import com.google.common.collect.ImmutableMap;
import com.tushar.aem.guides.core.services.MyServiceInterface;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyPathTypeServletTest {

    private final AemContext context = new AemContext();

    private MyPathTypeServlet servlet;

    @Mock
    private MyServiceInterface myService;

    @BeforeEach
    void setUp() {
        // 1. Register the mock OSGi service
        context.registerService(MyServiceInterface.class, myService);

        // 2. Instantiate the servlet and inject the mock service
        servlet = new MyPathTypeServlet();
        context.registerInjectActivateService(servlet);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        // Define test data
        String inputPath = "/content/tushar/home";
        String mockMappedUrl = "/home.html";
        String mockActivity = "Swimming";

        // Setup mock behavior
        // ResourceResolver.map() is part of the context, but we can simulate mappings
        // Or we can let AEM Mocks use its default behavior (returning the same path)
        when(myService.getRandomActivity()).thenReturn(mockActivity);

        // 3. Set request parameters
        context.request().setParameterMap(ImmutableMap.of("path", inputPath));

        // 4. Call the doGet method
        servlet.doGet(context.request(), context.response());

        // 5. Verify the response output
        // resolver.map(path) by default in AemContext returns the path itself
        // unless you configure resource resolver mappings in the context.
        String expectedResponse = inputPath + "  " + mockActivity;

        // Note: println adds a newline \r\n or \n depending on OS
        assertEquals(expectedResponse, context.response().getOutputAsString().trim());
    }
}