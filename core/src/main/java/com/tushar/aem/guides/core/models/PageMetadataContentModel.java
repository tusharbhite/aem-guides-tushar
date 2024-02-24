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

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.tushar.aem.guides.core.servlets.JsonDataDropdownServlet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Model(adaptables = Resource.class)
public class PageMetadataContentModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageMetadataContentModel.class);

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentComponentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    private String message;

    private String currentPagePath;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentComponentResource))
                .map(Page::getPath).orElse("");

        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n";
    }

    public String getMessage(){
        return currentComponentResource.getPath()+message;
    }

    // public Map<String,List<String>> getMetaData() {
    //     Map<String,List<String>> resultMap=new HashMap<>(); 
    //     ValueMap valueMap=resourceResolver.getResource(currentPagePath).getChild("jcr:content").getValueMap();
    //     List<String> list=new ArrayList<>();

    //     // Iterate over key-value pairs using entrySet()
    //     for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
    //         String key = entry.getKey();
    //         Object value = entry.getValue();
    //         // Access and process key and value
    //         LOGGER.error("Key: " + key + ", Value: " + value);
    //     }
    //     return null;
    // }

    public Map<String,String> getMetaData() {
        Map<String,String> resultMap=new HashMap<>(); 
        ValueMap valueMap=resourceResolver.getResource(currentPagePath).getChild("jcr:content").getValueMap();
        List<String> list=new ArrayList<>();

        //Iterate over key-value pairs using entrySet()
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
          
            // Check if value is an array or list
            String valueString="";
            if (value instanceof Collection) {
              Collection<?> collection = (Collection<?>) value;
              StringBuilder sb = new StringBuilder();
              boolean first = true;
              for (Object item : collection) {
                if (first) {
                  first = false;
                } else {
                  sb.append(",");
                }
                sb.append(item.toString());
                LOGGER.error("Item="+item.toString());
              }
              valueString = sb.toString();
            }
            if (value.getClass().isArray()) {
              int length = Array.getLength(value);
               valueString = ""; // Initialize with an empty string
              boolean first = true;
          
              for (int i = 0; i < length; i++) {
                Object item = Array.get(value, i);
          
                if (first) {
                  first = false;
                } else {
                  valueString += ", ";
                }
          
                valueString += item.toString(); // Convert item to string before appending
          
                // Process each item in the array
                System.out.println("Key: " + key + ", Value (array item): " + item);
              }
            }  else {
                  // Handle single string directly
                  valueString=value.toString();
                  System.out.println("Key: " + key + ", Value (string): " + value);
                }
            
              
          
            resultMap.put(key, valueString);
            
            LOGGER.error("Key: " + key + ", Value: " + valueString);
            valueString="";
          }

        
          
        return resultMap;
    }

}
