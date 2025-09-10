package com.tushar.aem.guides.core.listeners.learninig;


import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.List;
import java.util.Set;

@Component(
        immediate = true,
        service = ResourceChangeListener.class,
        property = {
                ResourceChangeListener.PATHS+"=/content/aemgeeks/us/en/card",
                ResourceChangeListener.CHANGES+"=ADDED",
                ResourceChangeListener.CHANGES+"=REMOVED",
                ResourceChangeListener.CHANGES+"=CHANGED"
        }
)
public class SlingResourceChangeListener implements ResourceChangeListener{

    private static final Logger LOG = LoggerFactory.getLogger(SlingResourceChangeListener.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> list) {
           for(ResourceChange rc : list){
               try {
                   LOG.info("\n Event : {} , Resource : {} ", rc.getType(), rc.getPath());
               }catch (Exception e){
                   LOG.info("\n Exception : {} ", e.getMessage());
               }
           }

    }
}
