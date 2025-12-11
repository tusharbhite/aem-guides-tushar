package com.tushar.aem.guides.core.models;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.day.cq.wcm.api.designer.Style;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MySlingModel2 {

    @ValueMapValue
    private String userName;

    @ValueMapValue
    private String designation;


    @Self
    private Resource resource;

    public String path="NULL";

    @PostConstruct
    public void init() {
        path=resource.getPath();
    }

    public String getUserName() {
       return userName;
    }

    public String getDesignation() {
        return designation;
     }


     public boolean getRespath() {
        return resource.hasChildren();
    }


}