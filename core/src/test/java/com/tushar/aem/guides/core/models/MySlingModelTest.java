//package com.tushar.aem.guides.core.models;
//
//import com.day.cq.wcm.api.Page;
//import io.wcm.testing.mock.aem.junit5.AemContext;
//import io.wcm.testing.mock.aem.junit5.AemContextExtension;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.scripting.SlingScript;
//import org.apache.sling.api.scripting.SlingScriptHelper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Answers;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith({AemContextExtension.class, MockitoExtension.class})
//class MySlingModelTest {
//
//    private final AemContext context = new AemContext();
//
//    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//    private SlingScriptHelper scriptHelper;
//
//    @BeforeEach
//    void setUp() {
//        context.addModelsForClasses(MySlingModel.class);
//
//        // 1. Setup the Page and Main Resource
//        Page currentPage = context.create().page("/content/tushar");
//        Resource resource = context.create().resource("/content/tushar/jcr:content/mycomponent",
//                "username", "Tushar",
//                "designation", "Developer",
//                "jcr:createdBy", "admin",
//                "Value", "custom-type",
//                "viewCount", 10,
//                "sling:resourceType", "tushar/components/mycomponent");
//
//        // 2. Setup Child Resources for @ChildResource and @Via("details")
//        context.create().resource("/content/tushar/jcr:content/mycomponent/details", "title", "Detail Title");
//        context.create().resource("/content/tushar/jcr:content/mycomponent/items/item1");
//        context.create().resource("/content/tushar/jcr:content/mycomponent/items/item2");
//
//        // 3. Setup Mock ScriptHelper for the deep call in @PostConstruct
//        // scriptHelper.getScript().getScriptResource().getPath()
//        when(scriptHelper.getScript().getScriptResource().getPath()).thenReturn("/apps/tushar/components/mycomponent/mycomponent.html");
//
//        // Register the mocked ScriptHelper into the context
//        context.registerService(SlingScriptHelper.class, scriptHelper);
//
//        // 4. Set Request Context
//        context.currentResource(resource);
//        context.currentPage(currentPage);
//        context.request().setAttribute("myAttribute", "RequestAttrValue");
//    }
//
//    @Test
//    void testGetDetails() {
//        // Adapt from Request to satisfy @RequestAttribute and @ScriptVariable
////        context.currentResource("/content/tushar/jcr:content/mycomponent");
//        MySlingModelInterface model = context.request().adaptTo(MySlingModelInterface.class);
//
//        assertNotNull(model, "Model failed to adapt");
//        Map<String, Object> details = model.getDetails();
//
//        // Assert ValueMapValues
//        assertEquals("Tushar", model.getUserName());
//        assertEquals("Developer", details.get("@ValueMapValue private String designation"));
//        assertEquals("admin", details.get("@ValueMapValue @Named(\"jcr:createdBy\") private String createdBy"));
//
//        // Assert Child Resources
//        assertEquals("/content/tushar/jcr:content/mycomponent/details",
//                details.get("@ChildResource(name = \"details\")\n    private Resource crdetails"));
//
//        List<String> items = (List<String>) details.get("@ChildResource(name = \"items\")\n    private List<Resource> items");
//        assertEquals(2, items.size());
//        assertTrue(items.contains("item1"));
//
//        // Assert Request Attribute
//        assertEquals("RequestAttrValue", details.get("@RequestAttribute(name = \"myAttribute\")\n    private String myAttribute"));
//
//        // Assert Deep ScriptHelper call
//        assertEquals("/apps/tushar/components/mycomponent/mycomponent.html",
//                details.get("@SlingObject\n    private SlingScriptHelper scriptHelper"));
//    }
//}