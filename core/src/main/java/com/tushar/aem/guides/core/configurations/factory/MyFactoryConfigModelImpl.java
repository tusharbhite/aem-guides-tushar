package com.tushar.aem.guides.core.configurations.factory;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyFactoryConfigModelImpl{

    /*--------Start Tutorial #33--------*/
    @OSGiService
    OSGiFactoryConfig oSGiFactoryConfig;

//    @Override
    public List<OSGiFactoryConfig> getAllOSGiConfigs() {
        return oSGiFactoryConfig.getAllConfigs();
    }
    /*--------End Tutorial #33--------*/

}