package com.tushar.aem.guides.core.jobs;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.event.Event;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyJobCreatorTest {

    private final AemContext context = new AemContext();

    private MyJobCreator jobCreator;

    @Mock
    private JobManager jobManager;

    @Mock
    private Job job;

    @BeforeEach
    void setUp() {
        jobCreator = new MyJobCreator();

        // Register the mock JobManager to satisfy the @Reference
        context.registerService(JobManager.class, jobManager);
        context.registerInjectActivateService(jobCreator);
    }

    @Test
    void testHandleEvent_CreatesJobWithCorrectProperties() {
        // 1. Create a mock OSGi Event
        String topic = "org/apache/sling/api/resource/Resource/ADDED";
        Map<String, Object> eventProps = new HashMap<>();
        eventProps.put(SlingConstants.PROPERTY_PATH, "/content/tushar/us/en/new-page");
        Event mockEvent = new Event(topic, eventProps);

        // 2. Setup JobManager mock to return a dummy job
        when(jobManager.addJob(eq("geeks/job"), anyMap())).thenReturn(job);

        // 3. Trigger the handler
        jobCreator.handleEvent(mockEvent);

        // 4. Use an ArgumentCaptor to inspect the properties passed to the JobManager
        ArgumentCaptor<Map<String, Object>> mapCaptor = ArgumentCaptor.forClass(Map.class);
        verify(jobManager).addJob(eq("geeks/job"), mapCaptor.capture());

        // 5. Assert the properties inside the job map
        Map<String, Object> capturedProperties = mapCaptor.getValue();
        assertEquals(topic, capturedProperties.get("event"));
        assertEquals("/content/tushar/us/en/new-page", capturedProperties.get("path"));
        assertEquals("heroPage", capturedProperties.get("heropage"));
    }

    @Test
    void testHandleEvent_ExceptionHandling() {
        // Force the JobManager to throw an exception to test the catch block
        doThrow(new RuntimeException("Job Error")).when(jobManager).addJob(anyString(), anyMap());

        Event mockEvent = new Event("topic", new HashMap<>());

        // Verify that the code catches the exception and doesn't crash the test
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> jobCreator.handleEvent(mockEvent));
    }
}