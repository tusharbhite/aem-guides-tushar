package com.tushar.aem.guides.core.services;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class MyServiceImplTest {

    private final AemContext context = new AemContext();
    private MyServiceInterface myService;

    @BeforeEach
    void setUp() {
        // registerInjectActivateService triggers the @Activate method automatically
        myService = context.registerInjectActivateService(new MyServiceImpl());
    }

    @Test
    void testGetRandomActivity() {
        List<String> expectedActivities = Arrays.asList("Running", "Cycling", "Skateboarding");

        // Execute multiple times to ensure the random logic always picks from the list
        for (int i = 0; i < 10; i++) {
            String activity = myService.getRandomActivity();
            assertNotNull(activity);
            assertTrue(expectedActivities.contains(activity),
                    "The activity '" + activity + "' should be in the initialized list");
        }
    }

    @Test
    void testGetCurrentString() {
        String result = myService.getCurrentString();
        assertEquals("Current String", result, "Method should return the hardcoded string");
    }

    @Test
    void testDeactivate() {
        // We verify deactivation by ensuring it doesn't throw exceptions
        // In more complex services, you might verify if resources are closed
        assertDoesNotThrow(() -> context.bundleContext().ungetService(
                context.bundleContext().getServiceReference(MyServiceInterface.class)));
    }
}