//package com.tushar.aem.guides.core.renderconditions;
//
//import com.adobe.granite.ui.components.rendercondition.RenderCondition;
//import com.adobe.granite.ui.components.rendercondition.SimpleRenderCondition;
//import io.wcm.testing.mock.aem.junit5.AemContext;
//import io.wcm.testing.mock.aem.junit5.AemContextExtension;
//import org.apache.sling.api.resource.Resource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import javax.servlet.ServletException;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(AemContextExtension.class)
//class MyRenderConditionsServletTest {
//
//    private final AemContext context = new AemContext();
//    private MyRenderConditionsServlet servlet;
//
//    @BeforeEach
//    void setUp() {
//        servlet = new MyRenderConditionsServlet();
//    }
//
//    @Test
//    void testDoGet_ShouldRenderWhenNoPatternsMatch() throws ServletException, IOException {
//        // 1. Mock the RenderCondition config properties on a resource
//        Resource configResource = context.create().resource("/apps/rendercondition",
//                "hiddenSitePaths", new String[]{"/content/other/.*"},
//                "sling:resourceType", "utils/granite/rendercondition/simple/sites-apps");
//
//        context.currentResource(configResource);
//
//        // 2. Mock the suffix (the site path being edited)
//        context.requestPathInfo().setSuffix("/content/tushar/us/en");
//
//        // 3. Execute
//        servlet.doGet(context.request(), context.response());
//
//        // 4. Verify attribute
//        RenderCondition rc = (RenderCondition) context.request().getAttribute(RenderCondition.class.getName());
//        assertNotNull(rc);
//        assertTrue(rc.check(), "Should render because suffix does not match hiddenSitePaths regex");
//    }
//
//    @Test
//    void testDoGet_ShouldHideWhenSitePathMatches() throws ServletException, IOException {
//        // 1. Mock properties where suffix matches the regex
//        Resource configResource = context.create().resource("/apps/rendercondition-hide",
//                "hiddenSitePaths", new String[]{"/content/tushar/.*"});
//
//        context.currentResource(configResource);
//        context.requestPathInfo().setSuffix("/content/tushar/us/en");
//
//        servlet.doGet(context.request(), context.response());
//
//        RenderCondition rc = (RenderCondition) context.request().getAttribute(RenderCondition.class.getName());
//        assertNotNull(rc);
//        assertFalse(rc.check(), "Should NOT render because suffix matches the hidden regex");
//    }
//
//    @Test
//    void testDoGet_DefaultRenderTrueWhenNoConfig() throws ServletException, IOException {
//        // Create resource with NO properties
//        Resource configResource = context.create().resource("/apps/empty-config");
//        context.currentResource(configResource);
//
//        servlet.doGet(context.request(), context.response());
//
//        RenderCondition rc = (RenderCondition) context.request().getAttribute(RenderCondition.class.getName());
//        assertTrue(rc.check(), "Should render true by default if no patterns are provided");
//    }
//}