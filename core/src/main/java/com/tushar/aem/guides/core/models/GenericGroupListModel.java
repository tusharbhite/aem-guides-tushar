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
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.tushar.aem.guides.core.utils.MovieSongs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Model(adaptables = Resource.class)
public class GenericGroupListModel {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private String message;

 

    @ValueMapValue
    String movieparentpath;

    Session session;

    @PostConstruct
    protected void init() {
        session =resourceResolver.adaptTo(Session.class);

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n";
    }

    public String getMessage() {
        return message;
    }

    public String getMovieparentpath() {
        return movieparentpath;
    }

    public List<String> getMoviesList(){
        List<String> moviePagesList=new ArrayList();
        Page page=resourceResolver.getResource(movieparentpath).adaptTo(Page.class); 
        Iterator<Page> pageIterator= page.listChildren();
        while (pageIterator.hasNext()) {
            moviePagesList.add(pageIterator.next().getPath());            
        }
        return moviePagesList;
    }

    public List<String> getMoviePosterList() throws Exception{
        List<String> moviePagesPosterList=new ArrayList();
        Page page=resourceResolver.getResource(movieparentpath).adaptTo(Page.class); 
        Iterator<Page> pageIterator= page.listChildren();
        while (pageIterator.hasNext()) {
            String pagePath=pageIterator.next().getPath();
            Node pageNode=resourceResolver.getResource(pagePath).adaptTo(Node.class);
            Node movieHeroNode=pageNode.getNode("jcr:content/root/container/movies_hero");
            String posterurl=movieHeroNode.getProperty("image").getString();
            moviePagesPosterList.add(posterurl);
        }
        return moviePagesPosterList;
    }

    public List<MovieSongs> getMovieSongs(){
        List<MovieSongs> objectList=new ArrayList<>(); 
        PageManager pagemanager=resourceResolver.adaptTo(PageManager.class);
        Page page=resourceResolver.getResource(movieparentpath).adaptTo(Page.class); 

        Iterator<Page> pageIterator= page.listChildren();
        try{

        
            while (pageIterator.hasNext()) {
                List<Page> songsPages = new ArrayList<>();
                Page currentPage=pageIterator.next();
                String moviePagePath=currentPage.getPath();
                String movieTitle=currentPage.getTitle();
                Node currentPageContentNode=resourceResolver.getResource(moviePagePath+"/jcr:content").adaptTo(Node.class);
            
                
                Node pageNode=resourceResolver.getResource(moviePagePath).adaptTo(Node.class);
                Node movieHeroNode=pageNode.getNode("jcr:content/root/container/movies_hero");
                String posterurl=movieHeroNode.getProperty("image").getString();
                Property property=currentPageContentNode.getProperty("songPagePaths");

                if (property != null) {
                    Value[] values = property.getValues(); // Get the array of values

                    String[] stringValues = new String[values.length];
                    for (int i = 0; i < values.length; i++) {
                        stringValues[i] = values[i].getString();  // Convert each Value to a String
                        songsPages.add(pagemanager.getPage(values[i].getString()));
                    }
                    //System.out.println("Array values: " + Arrays.toString(stringValues));

                } else {
                }
                if(songsPages.size()>0){
                    MovieSongs m=new MovieSongs(moviePagePath, movieTitle, posterurl, songsPages);
                    objectList.add(m);
                    m=null;
                }
                songsPages=null;

                    
            }
        }catch(Exception e){
            
        }    

        return objectList;
    }

}
