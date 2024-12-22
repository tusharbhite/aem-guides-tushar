//package com.tushar.aem.guides.core.models;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import javax.jcr.Node;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.sling.api.resource.Resource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import com.day.cq.wcm.api.Page;
//
//import io.wcm.testing.mock.aem.junit5.AemContext;
//import io.wcm.testing.mock.aem.junit5.AemContextExtension;
//
//@ExtendWith(AemContextExtension.class)
//public class ModelUsingResourceClassTest {
//
//    private final AemContext ctx = new AemContext();
//    private Node node;
//    private Page page;
//    private Resource resource;
//    private ModelUsingResourceClass hello;
//
//    @BeforeEach
//    void setUp() throws Exception {
//       page= ctx.create().page("/content/tushar");
//       resource = ctx.create().resource(page, "hello",
//            "sling:resourceType", "tushar/components/helloworld");
//            hello = resource.adaptTo(ModelUsingResourceClass.class);
//
//    }
//
//     @Test
//    void testGetName() {
//        String msg = hello.getMessage();
//        assertNotNull(msg);
//        assertTrue(StringUtils.contains(msg, resource.getResourceType()));
//        assertTrue(StringUtils.contains(msg, page.getPath()));
//    }
//
//
//
//
//}
