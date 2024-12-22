package com.tushar.aem.guides.core.services;

import java.util.List;
import java.util.Map;

import javax.jcr.Session;

public interface QueryService {
     List<String> executeQuery(Map<String,String> map,String S, Session session );
     List<String> executeQuery(String queryString,String S, Session session );
} 


/*
 String queryString = "SELECT [cq:Page] FROM [nt:unstructured] AS node WHERE ISDESCENDANTNODE([/content/tushar]) AND CONTAINS(node.*, 'dhoom')";
def testService = getService("com.tushar.aem.guides.core.services.QueryService")
Session session = resourceResolver.adaptTo(Session.class)
println "Query Result : " + testService.executeQuery(queryString,"s",session);
*/

/*
Map<String, String> predicateMap=new HashMap()
predicateMap.put("path", "/content/tushar");
fulltextSearchTerm="iron"
predicateMap.put("type", "cq:Page");
// predicateMap.put("group.p.or", "true"); // combine this group with OR
predicateMap.put("group.1_fulltext", fulltextSearchTerm);
def testService = getService("com.tushar.aem.guides.core.services.QueryService")
Session session = resourceResolver.adaptTo(Session.class)
println "Query Result : " + testService.executeQuery(predicateMap,"s",session);
*/