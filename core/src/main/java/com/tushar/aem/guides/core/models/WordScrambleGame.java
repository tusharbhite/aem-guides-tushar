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

import com.tushar.aem.guides.core.utils.WordScrambleUtil;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Model(adaptables = Resource.class)
public class WordScrambleGame {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private String message;

    List<WordScrambleUtil> lwsu=new ArrayList();


    @PostConstruct
    protected void init() {
        message="hello";
        Node anagramParentNode=resourceResolver.getResource("/etc/anagram/anagrams-nodes/jcr:content/list").adaptTo(Node.class);
        try{
            NodeIterator ni= anagramParentNode.getNodes();
            
            while (ni.hasNext()) {
                Node anagramNode=ni.nextNode();
                String mainWord=anagramNode.getProperty("main_word").getString();
                List<String> meaningfulAnagrams=new ArrayList<>();
                String anagramList=anagramNode.getProperty("meaningful_anagrams").getString();
                meaningfulAnagrams.add(anagramList);
                lwsu.add(new WordScrambleUtil(mainWord,meaningfulAnagrams));                                
            }
        }
        catch(Exception e){
        
        }
    }

    public String getMessage() {
        return message;
    }

    public List<WordScrambleUtil> getAnagramData() {
        return lwsu;
    }
    public Map<String,List<String>> getAnagramMap() {
        Node anagramParentNode=resourceResolver.getResource("/etc/anagram/anagrams-nodes/jcr:content/list").adaptTo(Node.class);
        Map<String,List<String>> wsm=new HashMap();

        try{
            NodeIterator ni= anagramParentNode.getNodes();
            int count=1;
            while (ni.hasNext()) {
                Node anagramNode=ni.nextNode();
                String mainWord=anagramNode.getProperty("main_word").getString();
                List<String> meaningfulAnagrams=new ArrayList<>();
                meaningfulAnagrams.add(mainWord);
                // String anagramList=anagramNode.getProperty("meaningful_anagrams").getString();

                Property property = anagramNode.getProperty("meaningful_anagrams");
                
                    // Process the values
                    Value[] values = property.getValues();
                    for (Value value : values) {
                        // System.out.println(value.getString());
                        meaningfulAnagrams.add(value.getString());
                    }
                    wsm.put(count+"_"+mainWord, meaningfulAnagrams);
                    count++;


            }
        }
        catch(Exception e){
        
        }
        return wsm;
    }
}
