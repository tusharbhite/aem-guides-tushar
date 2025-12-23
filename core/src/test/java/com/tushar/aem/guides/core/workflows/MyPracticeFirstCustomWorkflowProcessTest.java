package com.tushar.aem.guides.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyPracticeFirstCustomWorkflowProcessTest {

    private final AemContext context = new AemContext();
    private MyPracticeFirstCustomWorkflowProcess workflowProcess;

    @Mock
    private WorkItem workItem;

    @Mock
    private Workflow workflow;

    @Mock
    private MetaDataMap workflowMetaDataMap;

    @Mock
    private WorkflowSession workflowSession;

    @Mock
    private MetaDataMap stepMetaDataMap; // For the execute method signature

    @BeforeEach
    void setUp() {
        workflowProcess = new MyPracticeFirstCustomWorkflowProcess();

        // Chain the mocks: WorkItem -> Workflow -> MetaDataMap
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workflow.getMetaDataMap()).thenReturn(workflowMetaDataMap);
    }

    @Test
    void testExecute_SetsMetadataValues() throws WorkflowException {
        // 1. Execute the process
        workflowProcess.execute(workItem, workflowSession, stepMetaDataMap);

        // 2. Verify that values were put into the SHARED workflow metadata map
        verify(workflowMetaDataMap, times(1)).put("user", "Mike");
        verify(workflowMetaDataMap, times(1)).put("Age", "28");
    }
}