package com.tushar.aem.guides.core.elastic;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.tushar.aem.guides.core.services.ResourceResolverService;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;


@Component(service = JobConsumer.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "= Simple Sling Job",
        JobConsumer.PROPERTY_TOPICS + "=page/replication/job"
})
public class PageReplicationJobConsumer implements JobConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private ElasticsearchService elasticsearchService;

    @Reference
    ResourceResolverService resourceResolverService;

    @Override
    public JobResult process(Job job) {
        // try {
            /*
             * If you have any service that will perform the operation on the moves asset you can call it here
             * Or you can write some code 
             * Use the pathBeforeMove & pathAfterMove, retrieved from the props passed during the consumption of the job
             */
            // String apiKey = "TXlSaV9wWUJDWDZhZEMxTThBckU6Y3FTR2JtLXlEX2tMenEyTzVsaFhhUQ==";
            String apiKey = elasticsearchService.getSearchAPIKey();
            String endpointString=elasticsearchService.getSearchAPIEndPoint();
            String index = elasticsearchService.getSearchIndex();
            String replicationAction = job.getProperty("replicationAction").toString();
            String payload = job.getProperty("payload").toString();
            logger.error("PageReplicationJobConsumer Before Getting RR");
            ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
            logger.error("PageReplicationJobConsumer After Getting RR");
            if (resourceResolver == null) {
                logger.error("PageReplicationJobConsumer ResourceResolver is null, cannot proceed.");
            }
            if(replicationAction=="ACTIVATE"){
                // Create Document
                String createUrl = endpointString+"/"+index+"/_doc";
                //get Actual Page Info here
                // Document pageInfo = new Document("API-test-java", "API-test-java-path", "uid","LU","PT","PI","TL");
                logger.error("PageReplicationJobConsumer Before Getting Resource"+payload);
                Resource node=resourceResolver.getResource(payload+"/jcr:content");
                if (node == null) {
                    logger.error("PageReplicationJobConsumer Resource not found at path: " + payload + "/jcr:content");
                    return JobConsumer.JobResult.FAILED;
                }
                logger.error("PageReplicationJobConsumer Before Getting Node");
                // Node node=res.adaptTo(Node.class);
                // node=node.getNode("jcr:content");
                logger.error("PageReplicationJobConsumer Before Getting document");
                logger.error("PageReplicationJobConsumer Path"+node.getPath());
                logger.error("PageReplicationJobConsumer Title"+getProps(node,"jcr:title"));
                Document pageInfo = new Document( getProps(node,"jcr:title"),node.getPath(),getProps(node,"unique_id"),getProps(node,"liveURL"),getProps(node,"pageType"),getProps(node,"primaryimage"),getProps(node,"tagLine"));
                Gson gson = new Gson();
                String createJson = gson.toJson(pageInfo);
                // println createJson
                // println sendHttpRequest(createUrl, "POST", createJson, apiKey);
                logger.error("PageReplicationJobConsumer Before Making Request");
                String response=ElasticAPIRequestUtil.sendHttpRequest(createUrl, "POST", createJson, apiKey);
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response.toString());
                                // Extract _id value
                                String id = jsonObject.getString("_id");
                                logger.info( "PageReplicationJobConsumer Id is "+id);

                                if(id!=null){
                                    Session session = resourceResolver.adaptTo(Session.class);
                                    // resourceResolver.getResource(payload+"/jcr:content").getValueMap().put("documentId", id);
                                    Node pnode = resourceResolver.getResource(payload+"/jcr:content").adaptTo(Node.class);
                                    if (pnode != null) {
                                        pnode.setProperty("documentId", id);
                                        session.save();  // Persist changes
                                    } else {
                                        logger.error("PageReplicationJobConsumer Failed to adapt resource to JCR Node.");
                                    }

                                    session.save();
                                }else{
                                    logger.info( "PageReplicationJobConsumer Id is null");
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                logger.info( "PageReplicationJobConsumer Id erroe "+e.getStackTrace());
                            }

     


            }
            if(replicationAction=="DEACTIVATE"){
                // Delete Document
                //Get id of the document to be deleted
                // String id="ecCXApcB5WieeMKnnjNx";
                Resource node=resourceResolver.getResource(payload+"/jcr:content");
                String id=getProps(node,"documentId");
                String deleteUrl = endpointString+"/"+index+"/_doc/"+id;
                System.out.println(ElasticAPIRequestUtil.sendHttpRequest(deleteUrl, "DELETE", "", apiKey));
            }

            logger.info("PageReplicationJobConsumer Job is executed!! with action as: "+replicationAction);
            return JobConsumer.JobResult.OK;
        // } catch (Exception e) {
        //     logger.error("Exception in simple sling job, replicationAction cause {} message {}", e.getCause(), e.getMessage());
        //     return JobResult.FAILED;
        // }
    }

        
    String getProp(Node curNode,String propName) throws PathNotFoundException, RepositoryException{
        // curNode=getNode(path)
        if(curNode.hasProperty(propName))
        return curNode.getProperty(propName).getString();
        else return "";
    }

    String getProps(Resource curResource,String propName) 
    // throws PathNotFoundException, RepositoryException
    {
        // curNode=getNode(path)
        // if(curResource.getValueMap().get(propName);
        // return curNode.getPropserty(propName).getString();
        // else return "";
        return curResource.getValueMap().get(propName, String.class);
    }
    
}