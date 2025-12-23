package com.tushar.aem.guides.core.filters;

import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyServletFilterPreventPublishTest {

    private final AemContext context = new AemContext();

    private MyServletFilterPreventPublish filter;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @BeforeEach
    void setUp() {
        filter = new MyServletFilterPreventPublish();
        // Register the mock service to satisfy the @Reference
        context.registerService(ResourceResolverFactory.class, resourceResolverFactory);
        context.registerInjectActivateService(filter);

        // Create a dummy resource to prevent NPE on logger info line
        context.create().resource("/bin/replicate");
        context.currentResource("/bin/replicate");
    }

    @Test
    void testDoFilter_BlockRestrictedPath() throws ServletException, IOException {
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
        MockSlingHttpServletResponse response = (MockSlingHttpServletResponse) context.response();

        // Simulate the "Activate" command on a restricted path
//        request.setParameter("cmd", "activate");
//        request.setParameter("path", "/content/restricted/secret-page");
        request.setParameterMap(ImmutableMap.of(
                "cmd", "activate",
                "path", "/content/restricted/secret-page"
        ));

        filter.doFilter(request, response, filterChain);

        // Verify status is 403 Forbidden
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        assertEquals("Publishing pages under /content/restricted is not allowed.", response.getStatusMessage());

        // Ensure the chain was NOT continued
        verify(filterChain, times(0)).doFilter(request, response);
    }

    @Test
    void testDoFilter_AllowNormalPath() throws ServletException, IOException {
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
        MockSlingHttpServletResponse response = (MockSlingHttpServletResponse) context.response();

        // Simulate "Activate" on a safe path
//        request.setParameter("cmd", "activate");
//        request.setParameter("path", "/content/tushar/home");
        request.setParameterMap(ImmutableMap.of(
                "cmd", "activate",
                "path", "/content/tushar/home"
        ));

        filter.doFilter(request, response, filterChain);

        // Verify the chain was continued
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilter_IgnoreNonActivateCommand() throws ServletException, IOException {
        MockSlingHttpServletRequest request = (MockSlingHttpServletRequest) context.request();
        MockSlingHttpServletResponse response = (MockSlingHttpServletResponse) context.response();

        // Different command even on restricted path should pass this specific filter
//        request.setParameter("cmd", "delete");
//        request.setParameter("path", "/content/restricted/secret-page");

        request.setParameterMap(ImmutableMap.of(
                "cmd", "delete",
                "path", "/content/restricted/secret-page"
        ));

        filter.doFilter(request, response, filterChain);

        // Verify the chain was continued
        verify(filterChain, times(1)).doFilter(request, response);
    }
}