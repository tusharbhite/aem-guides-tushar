package com.tushar.aem.guides.core.filters;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MySlingServletFilterTest {

    private final AemContext context = new AemContext();

    private MySlingServletFilter filter;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new MySlingServletFilter();

        // 1. Create a dummy resource to prevent NPE in the log.error line
        Resource resource = context.create().resource("/content/practice/testpage");

        // 2. Set the current resource so slingRequest.getResource() is not null
        context.currentResource(resource);
    }

    @Test
    void testDoFilter() throws ServletException, IOException {
        // 3. Execute the filter
        filter.doFilter(context.request(), context.response(), filterChain);

        // 4. Verify that the filter chain continues (this is the primary job of this filter)
        // We pass context.request() and context.response() as arguments
        verify(filterChain, times(1)).doFilter(context.request(), context.response());
    }

    @Test
    void testInitAndDestroy() throws ServletException {
        // These methods are empty in your class, but calling them ensures code coverage
        filter.init(null);
        filter.destroy();
    }
}