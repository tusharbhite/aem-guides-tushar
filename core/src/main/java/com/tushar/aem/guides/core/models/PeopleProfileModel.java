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

import com.tushar.aem.guides.core.utils.MovieAPIOPS; 
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.tushar.aem.guides.core.servlets.JsonDataDropdownServlet;
import com.tushar.aem.guides.core.servlets.MoviePosterServlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Model(adaptables = SlingHttpServletRequest.class)
public class PeopleProfileModel {
        private static final Logger LOGGER = LoggerFactory.getLogger(PeopleProfileModel.class);


    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private String message;
    private String peopleProfileCFPath;
    private ArrayList<Object> moviesList=new ArrayList<Object>();

    @Inject
    private SlingHttpServletRequest currentRequest;


    @Inject 
    @Default(values="No String")
    private String temporary; 

    
   /* @RequestAttribute
    private String requestatributeString; 
    */
    @Inject
    @Default(values = "noooo")
    private String listFrom;

    @Inject
    @Named("jcr:primaryType")
    @Default(values = "no primaryTypeString")
    private String primaryTypeString;


    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n"
            +"CurrentRequestURL"+currentRequest.getRequestURL().toString()+"\n"
            +"CurrentRequestURI"+currentRequest.getRequestURI().toString();
        String currentRequestCompleteURI=currentRequest.getRequestURI().toString();    
        peopleProfileCFPath=currentRequest.getRequestURI().toString().substring(currentRequestCompleteURI.indexOf(".html")+5, currentRequestCompleteURI.length());    
        getFragmentProperties(peopleProfileCFPath);
    }

    public String getMessage() {
        return message;
    }

    public String getPeopleProfileCFPath() {
        return peopleProfileCFPath;
    }

     public String getString() {
        return temporary;
    }

    public String getListFrom(){
        return listFrom;
    }
    public String getPrimaryTypeString(){
        return primaryTypeString;
    }


    public String nonGetterMethod(){
        return "nongettermethodstring";
    }

    public String getCurrentClassName(){
        return this.getClass().getName();
    }

    public List<String> getListOfAllMethodsInCurrentClass(){
        List<String> result=new ArrayList<String>();
        Method[] methods = ModelUsingSlingHttpServletRequestClass.class.getDeclaredMethods();
        for (Method method : methods) {
          //System.out.println(method.getName());
          result.add(method.getName());
        }
        return result;
    }

    private Map<String, Object> getFragmentProperties(String path) {
        Map<String, Object> map = new HashMap<>();
        try {
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                ValueMap valueMap = resource.getValueMap();
                for (String name : valueMap.keySet()) {
                    map.put(name, valueMap.get(name));
                    LOGGER.error("mapkey:"+name);
                    LOGGER.error("mapValue:"+valueMap.get(name));
                    if(name=="top_5_movies" || name.equalsIgnoreCase("top_5_movies") || name.indexOf("top_5_movies")>=0 ){
                        LOGGER.error("Inside top_5_movies");
                        moviesList.add(valueMap.get(name));
                    }


                }
            }
        } catch (Exception e) {
            // Handle potential exceptions during resource access
        }
        return map;
    }

    public Map<String, Object> getPeopleProfileCFPropertiesMap(){
        return  getFragmentProperties(peopleProfileCFPath+"/jcr:content/data/master");
    }

    // public Map<String, String> getMoviesData() {
    //     LOGGER.debug("Inside GetMoviesData");
    //     Map<String, String> map = new HashMap<>();
    //     for (Object o:moviesList){
    //         String posterURL="empty";
    //         try {
    //             posterURL = MovieAPIOPS.getPosterUrlFromMovieName(o.toString());
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //         map.put(o.toString(), posterURL);
    //         LOGGER.debug("Movie Name"+o.toString()+"posterURL "+posterURL);
    //     }
    //     return  map;
    // }

}
