package com.tushar.aem.guides.core.models;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MySlingModel3 {

    @ValueMapValue
    private String userName;

    @ValueMapValue
    private String designation;

    @Self
    private SlingHttpServletRequest request;

    public String path="NULL";

    @ScriptVariable
    private Page svcurrentPage;

    @ScriptVariable
    protected ComponentContext componentContext;

    @ScriptVariable
    private Resource svresource;

    @ScriptVariable
    private ResourceResolver svresourceResolver;

    public Map<String,Object> details=new HashMap<>();

    @PostConstruct
    public void init() {
//        path=resource.getPath();
        int i=0;
    }

    public String getUserName() {
       return userName;
    }

    public String getDesignation() {
        return designation;
     }

     public String getRespath() {
        return request.getRequestPathInfo().getExtension();
    }


}