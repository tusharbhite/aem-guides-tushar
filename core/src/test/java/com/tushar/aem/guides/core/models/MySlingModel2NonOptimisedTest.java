package com.tushar.aem.guides.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MySlingModel2NonOptimisedTest {

    private final AemContext context = new AemContext();

    @Mock
    private SlingScriptHelper mockScriptHelper;

    @Mock
    private Resource mockScriptResource;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(MySlingModel2.class);
    }

    @Test
    void testGetUserName(){
        final String expected = "testuser";
        context.create().resource("/content/test/jcr:content/mycomponent",
                "userName", expected,
                "sling:resourceType", "tushar/components/mycomponent");
        Resource resource = context.resourceResolver().getResource("/content/test/jcr:content/mycomponent");
        MySlingModel2 model = resource.adaptTo(MySlingModel2.class);
        assertNotNull(model, "Model should not be null");
        assertEquals(expected, model.getUserName());
    }

    @Test
    void testGetDesignation(){
        final String expected = "developer";
        context.create().resource("/content/test/jcr:content/mycomponent",
                "designation", expected,
                "sling:resourceType", "tushar/components/mycomponent");
        Resource resource = context.resourceResolver().getResource("/content/test/jcr:content/mycomponent");
        MySlingModel2 model = resource.adaptTo(MySlingModel2.class);
        assertNotNull(model, "Model should not be null");
        assertEquals(expected, model.getDesignation());
    }

    @Test
    void testGetResPath(){
        final boolean expected = false;
        context.create().resource("/content/test/jcr:content/mycomponent",
                "userName", expected,
                "sling:resourceType", "tushar/components/mycomponent");
        Resource resource = context.resourceResolver().getResource("/content/test/jcr:content/mycomponent");
        MySlingModel2 model = resource.adaptTo(MySlingModel2.class);
        assertNotNull(model, "Model should not be null");
        assertEquals(expected, model.getRespath());
    }
}