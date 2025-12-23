package com.tushar.aem.guides.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyPracticeCustomWorkflowProcessTest {

    private final AemContext context = new AemContext();

    private MyPracticeCustomWorkflowProcess workflowProcess;

    @Mock
    private WorkItem workItem;

    @Mock
    private WorkflowSession workflowSession;

    @Mock
    private MetaDataMap metaDataMap;

    @Mock
    private WorkflowData workflowData;

    @BeforeEach
    void setUp() {
        workflowProcess = new MyPracticeCustomWorkflowProcess();

        // Mock the nested structure: WorkItem -> WorkflowData
        when(workItem.getWorkflowData()).thenReturn(workflowData);
    }

    @Test
    void testExecute_JcrPathPayload() throws WorkflowException {
        // 1. Setup mock data for JCR_PATH
        when(workflowData.getPayloadType()).thenReturn("JCR_PATH");
        when(workflowData.getPayload()).thenReturn("/content/tushar/us/en/page");

        // 2. Mock workflow process arguments (from the dialog)
        when(metaDataMap.get("PROCESS_ARGS", String.class)).thenReturn("my-custom-argument");

        // 3. Execute
        assertDoesNotThrow(() -> workflowProcess.execute(workItem, workflowSession, metaDataMap));

        // 4. Verify interactions
        verify(workflowData, times(1)).getPayloadType();
        verify(workflowData, times(1)).getPayload();
    }

    @Test
    void testExecute_OtherPayloadType() throws WorkflowException {
        // Setup mock data for a non-JCR path (like a URL)
        when(workflowData.getPayloadType()).thenReturn("URL");

        // The code logic skips the logger.error for path if not JCR_PATH
        assertDoesNotThrow(() -> workflowProcess.execute(workItem, workflowSession, metaDataMap));

        // Verify that payload path was NEVER called because type wasn't JCR_PATH
        verify(workflowData, never()).getPayload();
    }
}