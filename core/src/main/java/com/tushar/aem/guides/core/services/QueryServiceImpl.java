package com.tushar.aem.guides.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(
    service = { QueryService.class }
)
public class QueryServiceImpl implements QueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryServiceImpl.class);
    private static final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Reference
    QueryBuilder queryBuilder;

    public List<String> executeQuery(String queryString, String queryType, Session session) {
        List<String> resultPaths = new ArrayList<>();
        try {
            QueryManager queryManager=session.getWorkspace().getQueryManager();
           Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
            QueryResult queryResult = query.execute();
            NodeIterator nodes = queryResult.getNodes();
            while (nodes.hasNext()) {
                resultPaths.add(nodes.nextNode().getPath());
            }
        } catch (Exception i) {
            LOGGER.error("Exception in : QueryManagerImpl" + i.getMessage());
        }
        return resultPaths;
    }

    public List<String> executeQuery(Map<String, String> predicateMap, String queryType, Session session) {
        List<String> resultPaths = new ArrayList<>();
        // QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class)
        try {
            com.day.cq.search.Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
            SearchResult result = query.getResult();
            System.out.println(result.getTotalMatches());
            for (Hit hit : result.getHits()) {
                resultPaths.add(hit.getPath());
            }

        } catch (Exception i) {
            LOGGER.error("Exception in : QueryServiceImpl" + i.getMessage());
        }
        return resultPaths;
    }

    
    @Activate
    protected void activate() {
        log.info("Activated QueryServiceImpl with activities ");
    }

    @Deactivate
    protected void deactivate() {
        log.info("QueryServiceImpl has been deactivated!");
    }
}