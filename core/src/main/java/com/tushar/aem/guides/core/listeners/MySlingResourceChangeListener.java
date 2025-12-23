package com.tushar.aem.guides.core.listeners;


import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component(
        immediate = true,
        service = ResourceChangeListener.class,
        property = {
                ResourceChangeListener.PATHS+"=/content/tushar/us/en",
                ResourceChangeListener.CHANGES+"=ADDED",
                ResourceChangeListener.CHANGES+"=REMOVED",
                ResourceChangeListener.CHANGES+"=CHANGED"
        }
)
public class MySlingResourceChangeListener implements ResourceChangeListener{

    private static final Logger LOG = LoggerFactory.getLogger(MySlingResourceChangeListener.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> list) {
           for(ResourceChange rc : list){
               try {
                   LOG.info("\n Sling Resource Change Event Detected : {} , Resource : {} ", rc.getType(), rc.getPath());
               }catch (Exception e){
                   LOG.info("\n Exception : {} ", e.getMessage());
               }
           }

    }
}
