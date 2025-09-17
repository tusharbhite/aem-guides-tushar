package com.tushar.aem.guides.core.renderconditions;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.rendercondition.RenderCondition;
import com.adobe.granite.ui.components.rendercondition.SimpleRenderCondition;

/**
 * Servlet to conditionally render fields based on site (content paths) and/or app paths.
 * 
 * granite:rendercondition node properties:
 * - sling:resourceType: utils/granite/rendercondition/simple/sites-apps 
 * - hiddenSitePaths: content path regex for which field will not be rendered 
 * - hiddenAppPaths: apps path regex for which field will not be rendered in dialog 
 * - and: true to not render field based on both App and Content path regex; false otherwise.
 */
@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Custom Path RenderConditions Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=utils/granite/rendercondition/simple/sites-apps"
})
public class RenderConditionsServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(RenderConditionsServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        boolean render = true;
        ValueMap cfg = ResourceUtil.getValueMap(req.getResource());

        // Reading renderer properties
        String[] sitePatterns = cfg.get("hiddenSitePaths", new String[] {});
        String[] appPatterns = cfg.get("hiddenAppPaths", new String[] {});
        boolean andCondition = cfg.get("and", false);

        String sitePath = req.getRequestPathInfo().getSuffix();
        String appPath = req.getRequestPathInfo().getResourcePath().replaceFirst("^/mnt/override", "");

        // Evaluate render conditions
        if (shouldEvaluate(sitePatterns, sitePath) || shouldEvaluate(appPatterns, appPath)) {
            render = evaluateRenderCondition(sitePatterns, sitePath, appPatterns, appPath, andCondition);
        }

        req.setAttribute(RenderCondition.class.getName(), new SimpleRenderCondition(render));
    }

    private boolean shouldEvaluate(String[] patterns, String path) {
        return patterns != null && patterns.length > 0 && path != null;
    }

    private boolean evaluateRenderCondition(String[] sitePatterns, String sitePath, String[] appPatterns, String appPath, boolean andCondition) {
        boolean siteRender = isPathAllowed(sitePatterns, sitePath);
        boolean appRender = isPathAllowed(appPatterns, appPath);
        System.out.println("test");

        return andCondition ? siteRender && appRender : siteRender || appRender;
    }

    private boolean isPathAllowed(String[] patterns, String path) {
        for (String pattern : patterns) {
            if (path.matches(pattern)) {
                return false;
            }
        }
        return true;
    }
}