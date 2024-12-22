package com.tushar.aem.guides.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = WorkflowProcess.class,
        property = {"process.label=Practice Custom Workflow Process" })
public class PracticeCustomWorkflowProcess implements WorkflowProcess{

    protected final Logger logger = LoggerFactory.getLogger(PracticeCustomWorkflowProcess.class);

    public void execute(WorkItem workItem, WorkflowSession wfSession,
        MetaDataMap metaDataMap) throws WorkflowException {
            String payloadType = workItem.getWorkflowData().getPayloadType();

            if (StringUtils.equals(payloadType, "JCR_PATH")) {
                logger.error("PracticeCustomWorkflowProcess Payload type: {}", payloadType);

                String path = workItem.getWorkflowData().getPayload().toString();
                logger.error("PracticeCustomWorkflowProcess Payload path: {}", path);
            }

            String args = metaDataMap.get("PROCESS_ARGS", String.class);
            logger.error("Process args: {}", args);
    }
}