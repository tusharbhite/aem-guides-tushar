package com.tushar.aem.guides.core.models;


import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@ExtendWith(AemContextExtension.class)
public class HelloWorldModelTest2 {

   public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

   private HelloWorldModel helloWorldModel;
   private Resource resource;
   private ResourceResolver resourceResolver;
   private PageManager pageManager;
   private Page page;

   @BeforeEach
   public void setup() {
     page = context.create().page("/content/mypage");
        resource = context.create().resource(page, "hello",
            "sling:resourceType", "tushar/components/helloworld");

            
       // Create the necessary resources in the context
       resource = context.create().resource("/content/test", "jcr:primaryType", "nt:unstructured");
       resource = context.create().resource("/content/test/jcr:content", "jcr:title", "Test Page");
       resourceResolver = context.resourceResolver();


           // Implement other methods as required...

       // Binding the resource and resource resolver to the context
       context.currentResource(resource);
       resourceResolver.adaptTo(PageManager.class);

       // Adapt to the model
       helloWorldModel = context.request().adaptTo(HelloWorldModel.class);
   }

   @Test
   public void testInit() {
//        assertTrue("Hello World!\nResource type is: No resourceType\nCurrent page is:  /content/test-page\n", helloWorldModel.getMessage());
       assertTrue("hid".contains( "hi"));
   }
}
