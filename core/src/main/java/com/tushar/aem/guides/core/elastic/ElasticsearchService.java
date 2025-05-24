package com.tushar.aem.guides.core.elastic;

import org.apache.sling.api.resource.Resource;

import java.io.IOException;

/**
 * ElasticsearchService - Define methods.
 */
public interface ElasticsearchService {

 public String getSearchAPIEndPoint();
 public String getSearchAPIKey();  
 public String getSearchIndex();   
 
 // This method sends the JSON response
 public int sendEvent(String jsonBody, String topic, String action, Resource resource, String actionType) throws IOException;
}