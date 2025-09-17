/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tushar.aem.guides.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.tushar.aem.guides.core.testcontext.AppAemContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Map;

/**
 * Simple JUnit test verifying the HelloWorldModel
 */
@ExtendWith(MockitoExtension.class)
class HelloWorldModelTestUsingAEMMocks {

//    private final AemContext context = AppAemContext.newAemContext();
private final AemContext context = new AemContext();

    // private HelloWorldModel hello;

    private Page page;

    @Mock
    private Resource resource;

    @InjectMocks
    private HelloWorldModel hwmodel;

    @BeforeEach
    public void setup() throws Exception {

        // prepare a page with a test resource
        page = context.create().page("/content/mypage");
        resource = context.create().resource(page, "hello",
            "sling:resourceType", "tushar/components/helloworld");

        // create sling model
        hwmodel = resource.adaptTo(HelloWorldModel.class);
    }

    @Test
    void testGetMessage() throws Exception {
                assertEquals("Hello World!\r\n" + //
                                        "Resource type is: tushar/components/helloworld\r\n" + //
                                        "Current page is:  /content/mypage", hwmodel.getMessage());

        // when(resource.getValueMap()).thenReturn(new ValueMapDecorator(Map.of("key", "value")));
        // myComponent.setup();

        // assertEquals("value", myComponent.testGetMessage());

        // some very basic junit tests
//         String msg = hello.getMessage();
//         System.out.println("msg= "+msg);
//         System.out.println("msg2= "+resource.getResourceType()+" & "+page.getPath());

// //        assertNotNull(msg);
//         assertTrue(StringUtils.contains(msg, page.getPath()));
    }

    //  @Test
    // void testGetName() {
    //     fail("Not yet implemented");
    // }

    // @Test
    // void testGetOccupations() {
    //     fail("Not yet implemented");
    // }

    // @Test
    // void testIsEmpty() {
    //     fail("Not yet implemented");
    // }

}
