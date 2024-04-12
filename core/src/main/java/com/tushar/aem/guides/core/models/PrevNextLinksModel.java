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
import javax.inject.Inject;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.request.RequestPathInfo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class PrevNextLinksModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrevNextLinksModel.class);

    
    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;

    @Inject
    SlingHttpServletRequest request;

    @SlingObject
    private ResourceResolver resourceResolver;
    

    private String message;

    @Inject
    String pathOfCurrentPage;
    
    String currentPagePath;

    List<String> childPagePaths = new ArrayList<>();
    
    @Inject @org.apache.sling.models.annotations.Optional
    String parentPagePath; 

    

    @PostConstruct
    protected void init() {
        if(pathOfCurrentPage!=null){
            currentPagePath=pathOfCurrentPage;
        }

        Page currentPage=resourceResolver.getResource(currentPagePath).adaptTo(Page.class);


        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n";
        Resource parentPage = resourceResolver.getResource(currentPage.getParent().getPath());
    
        java.util.Iterator<Resource> ls=resourceResolver.getResource(currentPagePath).listChildren();
        java.util.Iterator<Page> children = parentPage.adaptTo(Page.class).listChildren(null, false);
        while (children.hasNext()) {
            Page childPage = children.next();
            childPagePaths.add(childPage.getPath());
        }    
    } 

    public String getMessage() {
        return message;
    }

    public String getPrevPagePath(){
        int currentPageIndex=childPagePaths.indexOf(currentPagePath);
        if(currentPageIndex>0){
            LOGGER.error("Prevpage "+childPagePaths.get(currentPageIndex-1));
            return childPagePaths.get(currentPageIndex-1);
        }
        else return null;
    }

    public String getNextPagePath(){
        int currentPageIndex=childPagePaths.indexOf(currentPagePath);
        if(currentPageIndex<childPagePaths.size()-1){
            LOGGER.error("Nextpage "+childPagePaths.get(currentPageIndex+1));

            return childPagePaths.get(currentPageIndex+1);
        }
        else return null;
    }

}
