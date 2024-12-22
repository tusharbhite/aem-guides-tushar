package com.tushar.aem.guides.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class GenericGroupGridModeTest {

    private final AemContext context = new AemContext();
    private GenericGroupGridModel genericGroupGridModel;
    private Page page;
    private Resource resource;
    
    private String message;

    @BeforeEach
    void setup(){
       page= context.create().page("/cotent/tushar");
       resource= context.create().resource(page,"genericGroupGridComponent","sling:resourceType","tushar/components/genericgroupgridmodel");
       message = "Hello World!\n"
       + "Resource type is: " + resource.getResourceType() + "\n"
       + "Current page is:  " + page.getPath() + "\n";
       genericGroupGridModel=resource.adaptTo(GenericGroupGridModel.class);
    }




}
