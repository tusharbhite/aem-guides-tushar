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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyPracticeSecondWorkflowProcessTest {

    private final AemContext context = new AemContext();
    private MyPracticeSecondWorkflowProcess workflowProcess;

    @Mock
    private WorkItem workItem;

    @Mock
    private Workflow workflow;

    @Mock
    private MetaDataMap workflowMetaDataMap;

    @Mock
    private WorkflowSession workflowSession;

    @Mock
    private MetaDataMap stepMetaDataMap;

    @BeforeEach
    void setUp() {
        workflowProcess = new MyPracticeSecondWorkflowProcess();

        // Chain the mocks to reach the metadata map
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workflow.getMetaDataMap()).thenReturn(workflowMetaDataMap);
    }

    @Test
    void testExecute_RetrievesMetadataValues() throws WorkflowException {
        // 1. Simulate data being present from the previous step
        when(workflowMetaDataMap.get("user", String.class)).thenReturn("Mike");
        when(workflowMetaDataMap.get("Age", String.class)).thenReturn("28");

        // 2. Execute
        assertDoesNotThrow(() -> workflowProcess.execute(workItem, workflowSession, stepMetaDataMap));

        // 3. Verify that the map was queried correctly
        verify(workflowMetaDataMap).get("user", String.class);
        verify(workflowMetaDataMap).get("Age", String.class);
    }

    @Test
    void testExecute_HandlesMissingMetadata() throws WorkflowException {
        // Simulate a scenario where the previous step didn't run or values are missing
        when(workflowMetaDataMap.get(anyString(), eq(String.class))).thenReturn(null);

        // Code should handle nulls without crashing (which yours does by passing null to the logger)
        assertDoesNotThrow(() -> workflowProcess.execute(workItem, workflowSession, stepMetaDataMap));
    }
}