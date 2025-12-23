package com.tushar.aem.guides.core.listeners;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChange.ChangeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MySlingResourceChangeListenerTest {

    private final AemContext context = new AemContext();

    private MySlingResourceChangeListener listener;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Mock
    private ResourceChange change1;

    @Mock
    private ResourceChange change2;

    @BeforeEach
    void setUp() {
        listener = new MySlingResourceChangeListener();

        // Register the mock service for the @Reference field
        context.registerService(ResourceResolverFactory.class, resourceResolverFactory);

        // Inject and activate
        context.registerInjectActivateService(listener);
    }

    @Test
    void testOnChange() {
        // 1. Setup mock behaviors for the changes
        when(change1.getType()).thenReturn(ChangeType.ADDED);
        when(change1.getPath()).thenReturn("/content/tushar/us/en/page1");

        when(change2.getType()).thenReturn(ChangeType.CHANGED);
        when(change2.getPath()).thenReturn("/content/tushar/us/en/page2");

        List<ResourceChange> changes = Arrays.asList(change1, change2);

        // 2. Execute and ensure it processes the list safely
        assertDoesNotThrow(() -> listener.onChange(changes));
    }

    @Test
    void testOnChange_WithException() {
        // Simulate a scenario where rc.getPath() might throw an exception
        when(change1.getType()).thenReturn(ChangeType.REMOVED);
        when(change1.getPath()).thenThrow(new RuntimeException("Test Exception"));

        List<ResourceChange> changes = Arrays.asList(change1);

        // Your code has a try-catch inside the loop, so it should not crash
        assertDoesNotThrow(() -> listener.onChange(changes));
    }
}