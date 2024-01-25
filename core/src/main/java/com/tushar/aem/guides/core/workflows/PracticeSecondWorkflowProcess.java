package com.tushar.aem.guides.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = WorkflowProcess.class,
        property = {"process.label=Practice Second Workflow Process 2nd " })
public class PracticeSecondWorkflowProcess implements WorkflowProcess{

    protected final Logger logger = LoggerFactory.getLogger(PracticeSecondWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession wfSession,
        MetaDataMap metaDataMap) throws WorkflowException {
        String name = workItem.getWorkflow().getMetaDataMap().get("user", String.class);
        String age = workItem.getWorkflow().getMetaDataMap().get("Age", String.class);

        logger.error("Name >>>>>>> {}", name);
        logger.error("Age >>>>>>> {}", age);
    }
}