package com.tushar.aem.guides.core.listeners;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyJCREventListenerTest {

    private final AemContext context = new AemContext();

    private MyJCREventListener listener;

    @Mock
    private SlingRepository slingRepository;

    @Mock
    private Session session;

    @Mock
    private Workspace workspace;

    @Mock
    private ObservationManager observationManager;

    @Mock
    private EventIterator eventIterator;

    @Mock
    private Event jcrEvent;

    @BeforeEach
    void setUp() throws RepositoryException {
        // Mock the JCR chain: Repository -> Session -> Workspace -> ObservationManager
        when(slingRepository.loginService(anyString(), any())).thenReturn(session);
        when(session.getWorkspace()).thenReturn(workspace);
        when(workspace.getObservationManager()).thenReturn(observationManager);

        // Register the mocked repository as an OSGi service
        context.registerService(SlingRepository.class, slingRepository);

        // Instantiate and inject
        listener = context.registerInjectActivateService(new MyJCREventListener());
    }

    @Test
    void testActivate() throws RepositoryException {
        // Verify that addEventListener was called with the correct parameters during activation
        verify(observationManager, times(1)).addEventListener(
                eq(listener),
                eq(Event.NODE_ADDED | Event.PROPERTY_ADDED),
                eq("/content/tushar/us/en"),
                eq(true),
                any(),
                any(),
                eq(true)
        );
    }

    @Test
    void testOnEvent() throws RepositoryException {
        // Setup the EventIterator to have one event
        when(eventIterator.hasNext()).thenReturn(true, false); // true the first time, false the second
        when(eventIterator.nextEvent()).thenReturn(jcrEvent);
        when(jcrEvent.getPath()).thenReturn("/content/tushar/us/en/jcr:content");

        // Manually trigger the onEvent method
        listener.onEvent(eventIterator);

        // Verify the event was processed
        verify(jcrEvent, times(1)).getPath();
    }
}