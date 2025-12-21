package com.tushar.aem.guides.core.configurations.contextaware;


import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.caconfig.ConfigurationResolver;

import javax.annotation.PostConstruct;

/*#############################################
This model is used in mycomponent component,
Configurations are stored under
(Root Config) /conf/tushar/sling:configs/com.tushar.aem.guides.core.configurations.contextaware.MyCAConfig
(Path Specific Config) /conf/tushar/in
(Path Specific Config) /conf/tushar/us

Configurations references are stored in property sling:configRef under paths
/content/tushar/jcr:content
/content/tushar/in/jcr:content
/content/tushar/us/jcr:content
* ############################################*/
@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {MyCAConfigInterface.class},
        resourceType = {MyCAConfigImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MyCAConfigImpl implements MyCAConfigInterface {
    private static final Logger LOG = LoggerFactory.getLogger(MyCAConfigImpl.class);
    protected static final String RESOURCE_TYPE = "tushar/components/mycomponent";

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    @OSGiService
    ConfigurationResolver configurationResolver;

    private String siteCountry;
    private String siteLocale;
    private String siteAdmin;
    private String siteSection;
    private MyCAConfig geeksCAConfig;

    @Override
    public String getSiteCountry() {
        return siteCountry;
    }

    @Override
    public String getSiteLocale() {
        return siteLocale;
    }

    @Override
    public String getSiteAdmin() {
        return siteAdmin;
    }

    @Override
    public String getSiteSection() {
        return siteSection;
    }

    private static final Logger log = LoggerFactory.getLogger(MyCAConfigImpl.class);

    @PostConstruct
    public void postConstruct() {
    MyCAConfig caConfig=getContextAwareConfig(currentPage.getPath(),resourceResolver);
        siteCountry=caConfig.siteCountry();
        siteLocale=caConfig.siteLocale();
        siteAdmin=caConfig.siteAdmin();
        siteSection=caConfig.siteSection();
        log.info("CAConfigImpl "+" siteCountry: "+siteCountry+" siteAdmin: "+siteAdmin+" siteSection: "+siteSection);
    }

    public MyCAConfig getContextAwareConfig(String currentPage, ResourceResolver resourceResolver) {
        String currentPath = StringUtils.isNotBlank(currentPage) ? currentPage : StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if (contentResource != null) {
           ConfigurationBuilder configurationBuilder = contentResource.adaptTo(ConfigurationBuilder.class);
            if (configurationBuilder != null) {
                return configurationBuilder.as(MyCAConfig.class);
            }
        }
        return null;
    }
}