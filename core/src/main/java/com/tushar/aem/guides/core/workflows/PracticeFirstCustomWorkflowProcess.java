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
        property = {"process.label=Practice Workflow pass value from one process step to another" })
public class PracticeFirstCustomWorkflowProcess implements WorkflowProcess{

    protected final Logger logger = LoggerFactory.getLogger(PracticeFirstCustomWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession wfSession,
        MetaDataMap metaDataMap) throws WorkflowException {
            logger.error("PracticeCustomWorkflowProcess called >>>>>>>>");

            workItem.getWorkflow().getMetaDataMap().put("user", "Mike");
            workItem.getWorkflow().getMetaDataMap().put("Age", "28");
    }
}