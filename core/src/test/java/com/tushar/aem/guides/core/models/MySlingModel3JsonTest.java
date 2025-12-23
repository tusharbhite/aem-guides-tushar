package com.tushar.aem.guides.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
class MySlingModel3JsonTest {

    private final AemContext context = new AemContext();

    private static final String COMPONENT_PATH = "/content/test/jcr:content/mycomponent";
    private MySlingModel3 model;

    @BeforeEach
    void setUp() {
        // 1. Register the model
        context.addModelsForClasses(MySlingModel3.class);

        // 2. Create a mock resource with properties
//        Resource resource = context.create().resource(COMPONENT_PATH,
//                "userName", "tushar_user",
//                "designation", "AEM Developer");
        context.load().json("/MySlingModelTest.json","/content/test/jcr:content");
        Resource resource = context.resourceResolver().getResource("/content/test/jcr:content/mycomponent");

        // 3. Set the context to this resource and set a mock extension
        context.currentResource(resource);
        context.requestPathInfo().setExtension("html");

        // 4. Adapt from Request (as defined in your @Model annotation)
        model = context.request().adaptTo(MySlingModel3.class);
    }

    @Test
    void testModelNotNull() {
        assertNotNull(model, "Model should adapt correctly from Request");
    }

    @Test
    void testGetUserName() {
        assertEquals("testuser", model.getUserName());
    }

    @Test
    void testGetDesignation() {
        assertEquals("developer", model.getDesignation());
    }

    @Test
    void testGetRespath() {
        // The method getRespath returns request.getRequestPathInfo().getExtension()
        assertEquals("html", model.getRespath());
    }
}