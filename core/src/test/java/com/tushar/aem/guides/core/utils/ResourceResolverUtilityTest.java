package com.tushar.aem.guides.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceResolverUtilityTest {

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Mock
    private ResourceResolver resourceResolver;

    @Test
    @SuppressWarnings("unchecked")
    void testNewResolver_Success() throws LoginException {
        // 1. Setup mock behavior
        when(resourceResolverFactory.getServiceResourceResolver(anyMap())).thenReturn(resourceResolver);

        // 2. Execute utility method
        ResourceResolver result = ResourceResolverUtility.newResolver(resourceResolverFactory);

        // 3. Verify interactions and results
        assertNotNull(result);
        assertEquals(resourceResolver, result);

        // Verify that the factory was called with the correct subservice name
        verify(resourceResolverFactory).getServiceResourceResolver(argThat(map ->
                ResourceResolverFactory.SUBSERVICE.equals(ResourceResolverFactory.SUBSERVICE) &&
                        ResourceResolverUtility.TUSHAR_SERVICE_USER.equals(map.get(ResourceResolverFactory.SUBSERVICE))
        ));
    }

    @Test
    void testNewResolver_ThrowsLoginException() throws LoginException {
        // Simulate a LoginException from the factory
        when(resourceResolverFactory.getServiceResourceResolver(anyMap())).thenThrow(new LoginException("Mock Error"));

        // Verify the exception is propagated
        assertThrows(LoginException.class, () -> {
            ResourceResolverUtility.newResolver(resourceResolverFactory);
        });
    }

    @Test
    void testConstructorIsPrivate() throws Exception {
        // Coverage for the private constructor
        Constructor<ResourceResolverUtility> constructor = ResourceResolverUtility.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance());
    }
}