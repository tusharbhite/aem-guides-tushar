package com.tushar.aem.guides.core.jobs;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer.JobResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyJobConsumerTest {

    private final AemContext context = new AemContext();

    private MyJobConsumer jobConsumer;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Mock
    private Job job;

    @BeforeEach
    void setUp() {
        jobConsumer = new MyJobConsumer();

        // Register the mock service to satisfy @Reference
        context.registerService(ResourceResolverFactory.class, resourceResolverFactory);
        context.registerInjectActivateService(jobConsumer);
    }

    @Test
    void testProcess_Success() {
        // 1. Mock the properties inside the job
        when(job.getProperty("path")).thenReturn("/content/tushar/us/en/page");
        when(job.getProperty("event")).thenReturn("org/apache/sling/api/resource/Resource/ADDED");
        when(job.getProperty("heropage")).thenReturn("heroPage");

        // 2. Execute the consumer
        JobResult result = jobConsumer.process(job);

        // 3. Verify the result is OK
        assertEquals(JobResult.OK, result);
        verify(job, atLeastOnce()).getProperty("path");
    }

    @Test
    void testProcess_Failure() {
        // 1. Force an exception by making a property call throw an error
        // Your code casts to (String), so returning an Integer will trigger a ClassCastException
        when(job.getProperty("path")).thenReturn(123);

        // 2. Execute
        JobResult result = jobConsumer.process(job);

        // 3. Verify the result is FAILED as per your catch block logic
        assertEquals(JobResult.FAILED, result);
    }

    @Test
    void testProcess_NullProperties() {
        // 1. Mock properties returning null
        when(job.getProperty(anyString())).thenReturn(null);

        // 2. Execute
        JobResult result = jobConsumer.process(job);

        // 3. Should still return OK because null strings in logging don't throw exceptions
        assertEquals(JobResult.OK, result);
    }
}