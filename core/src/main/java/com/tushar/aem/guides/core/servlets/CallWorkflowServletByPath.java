package com.tushar.aem.guides.core.servlets;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
    service=Servlet.class,
    property={
        Constants.SERVICE_DESCRIPTION + "=Custom Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/wfservlet"
    }
)
public class CallWorkflowServletByPath extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(final SlingHttpServletRequest req,
        final SlingHttpServletResponse resp) throws IOException {
        try {
            logger.error("Entering CallWorkflowServletByPath >>>>>>>>>>>>>>>>>");

            // Workflow model to get call as part of workflow API
            final String model = "/var/workflow/models/practice-content-workflow-model";

            // Content path on which workflow will get trigger
            final String payloadContentPath = "/content/practice/us/en/test";

            final ResourceResolver resolver = req.getResourceResolver();
            final WorkflowSession workflowSession = resolver.adaptTo(WorkflowSession.class);

            // Create workflow model using mode path
            final WorkflowModel workflowModel = workflowSession.getModel(model);

            final WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payloadContentPath);

            // Pass value to workflow
            final Map<String, Object> workflowMetadata = new HashMap<>();
            workflowMetadata.put("pathInfo", req.getPathInfo());

            // Trigger practice-content-workflow-model workflow
            workflowSession.startWorkflow(workflowModel, workflowData, workflowMetadata);

            logger.error("Exiting CallWorkflowServletByPath >>>>>>>>>>>>>>>>>");
        } catch (WorkflowException e) {
            e.printStackTrace();
        }

        resp.setStatus(SlingHttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print("{\"response message\" : \" Workflow Called\"}");
    }
}