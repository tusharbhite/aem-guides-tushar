package com.tushar.aem.guides.core.listeners;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.event.Event;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyOSGIEventHandlerTest {

    private final AemContext context = new AemContext();

    private MyOSGIEventHandler eventHandler;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @BeforeEach
    void setUp() {
        eventHandler = new MyOSGIEventHandler();

        // Register the mock service for the @Reference field
        context.registerService(ResourceResolverFactory.class, resourceResolverFactory);

        // Inject and activate the component
        context.registerInjectActivateService(eventHandler);
    }

    @Test
    void testHandleEvent_ResourceAdded() {
        // 1. Define the event properties
        Map<String, Object> eventProperties = new HashMap<>();
        eventProperties.put(SlingConstants.PROPERTY_PATH, "/content/tushar/us/en/test-page");

        // 2. Construct the OSGi Event
        // Topic matches one of the properties in the @Component annotation
        Event testEvent = new Event("org/apache/sling/api/resource/Resource/ADDED", eventProperties);

        // 3. Execute and verify no exceptions occur
        assertDoesNotThrow(() -> eventHandler.handleEvent(testEvent));
    }

    @Test
    void testHandleEvent_ResourceChanged() {
        Map<String, Object> eventProperties = new HashMap<>();
        eventProperties.put(SlingConstants.PROPERTY_PATH, "/content/tushar/us/en/home");

        Event testEvent = new Event("org/apache/sling/api/resource/Resource/CHANGED", eventProperties);

        assertDoesNotThrow(() -> eventHandler.handleEvent(testEvent));
    }
}