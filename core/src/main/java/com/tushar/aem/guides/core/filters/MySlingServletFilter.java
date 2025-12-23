package com.tushar.aem.guides.core.filters;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.servlets.annotations.SlingServletFilter;
import org.apache.sling.servlets.annotations.SlingServletFilterScope;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import java.io.IOException;

@Component
@SlingServletFilter(scope = {SlingServletFilterScope.REQUEST},
        // resourceTypes = {"practice/components/page"},
        pattern = "/content/.*",
        extensions = {"html","txt","json"},
        // selectors = {"foo","bar"},
        methods = {"GET","HEAD"})
public class MySlingServletFilter implements  Filter{
    private static final Logger log = LoggerFactory.getLogger(MySlingServletFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        SlingHttpServletResponse slingResponse = (SlingHttpServletResponse)response;
        SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        log.error(">>>>>>>>>>>>>>>>>>>>>>> MySlingServletFilter Called Resource Path is : "+slingRequest.getResource().getPath());

        chain.doFilter(request, slingResponse);
    }

    @Override
    public void destroy() {

    }
}
