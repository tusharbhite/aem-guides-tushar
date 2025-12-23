package com.tushar.aem.guides.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class) // MockitoExtension removed as no mocks are currently used
class MySlingModel2JsonTest {

    private final AemContext context = new AemContext();

    private static final String RESOURCE_PATH = "/content/test/jcr:content/mycomponent";
    private MySlingModel2 model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(MySlingModel2.class);

        context.load().json("/MySlingModelTest.json","/content/test/jcr:content");
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
        assertTrue(model.getRespath(), "ResPath should be false based on setup");
    }
}