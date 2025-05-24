package com.tushar.aem.guides.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ResourceResolverService.class, immediate = true)
public class ResourceResolverServiceImpl implements ResourceResolverService {

    @Reference
    ResourceResolverFactory resolverFactory;

    @Override
    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        Map<String, Object> param = getServiceParams();

        try {
            resolver = resolverFactory.getServiceResourceResolver(param);
            // resolver=resolverFactory.getResourceResolver(param)

        } catch (LoginException e) {
            e.printStackTrace();
        }
        return resolver;
    }

    public static Map<String, Object> getServiceParams() {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "practiceService");
        return param;
    }

}