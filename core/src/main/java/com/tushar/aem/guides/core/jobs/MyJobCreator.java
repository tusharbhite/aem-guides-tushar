package com.tushar.aem.guides.core.jobs;


import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = {EventHandler.class},
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_FILTER +"=(path=/content/tushar/us/en/*)"
        })
public class MyJobCreator implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyJobCreator.class);

    @Reference
    JobManager jobManager;

    public void handleEvent(final Event event) {
        try {
                Map<String, Object> jobProperties = new HashMap<String, Object>();
                jobProperties.put("event", event.getTopic());
                jobProperties.put("path", event.getProperty(SlingConstants.PROPERTY_PATH));
                jobProperties.put("heropage","heroPage");
                jobProperties.put("test","heroPage");
                Job job=jobManager.addJob("geeks/job",jobProperties);

        }catch (Exception e){
            LOG.error("\n Exception is : {} " , e.getMessage());
        }
    }
}