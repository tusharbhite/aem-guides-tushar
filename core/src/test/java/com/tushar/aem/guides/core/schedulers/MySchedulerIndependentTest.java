package com.tushar.aem.guides.core.schedulers;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MySchedulerIndependentTest {

    private final AemContext context = new AemContext();

    private MySchedulerIndependent schedulerTask;

    @Mock
    private Scheduler scheduler;

    @Mock
    private ScheduleOptions scheduleOptions;

    @BeforeEach
    void setUp() {
        schedulerTask = new MySchedulerIndependent();

        // 1. USE LENIENT for stubbing in @BeforeEach to avoid UnnecessaryStubbingException
        lenient().when(scheduler.EXPR(anyString())).thenReturn(scheduleOptions);
        lenient().when(scheduleOptions.name(anyString())).thenReturn(scheduleOptions);
        lenient().when(scheduleOptions.canRunConcurrently(anyBoolean())).thenReturn(scheduleOptions);

        context.registerService(Scheduler.class, scheduler);
    }

    @Test
    void testActivate() {
        // 1. Mock the Config @interface
        MySchedulerIndependent.Config config = mock(MySchedulerIndependent.Config.class);

        // Use lenient() for properties that might not be accessed by the code during this specific test run
        lenient().when(config.scheduler_expression()).thenReturn("*/01 * * * * ?");
        lenient().when(config.scheduler_name()).thenReturn("TestTask");
        lenient().when(config.scheduler_concurrent()).thenReturn(false);
        lenient().when(config.myParameter()).thenReturn("some-value");

        // 2. Create the property map for AEM Context
        Map<String, Object> properties = new HashMap<>();
        properties.put("scheduler.expression", config.scheduler_expression());
        properties.put("scheduler.name", config.scheduler_name());
        // Add any other properties the activate method expects

        // 3. Activate
        context.registerInjectActivateService(schedulerTask, properties);

        // 4. Verify
        verify(scheduler, atLeastOnce()).schedule(eq(schedulerTask), any(ScheduleOptions.class));
    }

    @Test
    void testRun() {
        // testRun() doesn't use the scheduler stubs, but lenient() prevents the crash
        assertDoesNotThrow(() -> schedulerTask.run());
    }
}