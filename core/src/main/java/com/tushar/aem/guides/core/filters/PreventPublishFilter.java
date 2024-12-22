package com.tushar.aem.guides.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.engine.EngineConstants;

@Component(service = Filter.class,
           property = {
               EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
               EngineConstants.SLING_FILTER_PATTERN + "=" + "/bin/replicate"
           })
public class PreventPublishFilter implements Filter {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        SlingHttpServletRequest httpRequest = (SlingHttpServletRequest) request;
        SlingHttpServletResponse httpResponse = (SlingHttpServletResponse) response;

        // Check if the request is a publish request
        if ("activate".equals(httpRequest.getParameter("cmd")) || "Activate".equals(httpRequest.getParameter("cmd"))) {
            String path = httpRequest.getParameter("path");

            // Check if the path is under the restricted area
            if (path != null && path.startsWith("/content/restricted")) {
                // Block the publish request
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Publishing pages under /content/restricted is not allowed.");
                return;
            }
        }

        // Continue with the filter chain if not blocked
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
