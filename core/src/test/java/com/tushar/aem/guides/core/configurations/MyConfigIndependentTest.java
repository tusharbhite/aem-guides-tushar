package com.tushar.aem.guides.core.configurations;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MyConfigIndependentTest {

    private final AemContext context = new AemContext();
    private MyConfigIndependent configService;

    @BeforeEach
    void setUp() {
        configService = new MyConfigIndependent();
    }

    @Test
    void testActivateWithCustomConfiguration() {
        // 1. Define custom properties in a Map
        Map<String, Object> properties = new HashMap<>();
        properties.put("envVar", "Production-Env");

        // 2. Register and Activate the service with properties
        // AemContext handles the mapping from Map to the @interface Config
        context.registerInjectActivateService(configService, properties);

        // 3. Verify the value was correctly assigned
        assertEquals("Production-Env", configService.getEnvVar(),
                "The environment variable should match the configured value.");
    }

    @Test
    void testActivateWithDefaultConfiguration() {
        // Registering without a property map triggers the default values defined in @AttributeDefinition
        context.registerInjectActivateService(configService);

        // Verify the default value from the @interface is used
        assertEquals("defaultValue", configService.getEnvVar(),
                "The environment variable should return the default value defined in the @interface.");
    }

    @Test
    void testManualActivationWithMock() {
        // Alternative approach: Mock the Config annotation directly
        Config mockConfig = mock(Config.class);
        when(mockConfig.envVar()).thenReturn("Mocked-Value");

        // Call activate manually
        configService.activate(mockConfig);

        assertEquals("Mocked-Value", configService.getEnvVar());
    }
}