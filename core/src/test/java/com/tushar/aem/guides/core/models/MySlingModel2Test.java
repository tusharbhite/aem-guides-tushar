package com.tushar.aem.guides.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class) // MockitoExtension removed as no mocks are currently used
class MySlingModel2Test {

    private final AemContext context = new AemContext();

    private static final String RESOURCE_PATH = "/content/test/jcr:content/mycomponent";
    private MySlingModel2 model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(MySlingModel2.class);

        // Pre-create the resource with all necessary properties for the tests
        context.create().resource(RESOURCE_PATH,
                "userName", "testuser",
                "designation", "developer",
                "respath", false, // Assuming this is the boolean property for getRespath
                "sling:resourceType", "tushar/components/mycomponent");

        // Set the current resource context
        context.currentResource(RESOURCE_PATH);

        // Adapt once if the model is stateless and used across all tests
        model = context.currentResource().adaptTo(MySlingModel2.class);
    }

    @Test
    void testModelAdaptation() {
        assertNotNull(model, "Sling Model failed to adapt from Resource");
    }

    @Test
    void testGetUserName() {
        assertEquals("testuser", model.getUserName(), "Username mismatch");
    }

    @Test
    void testGetDesignation() {
        assertEquals("developer", model.getDesignation(), "Designation mismatch");
    }

    @Test
    void testGetResPath() {
        assertFalse(model.getRespath(), "ResPath should be false based on setup");
    }
}