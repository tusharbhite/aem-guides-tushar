package com.tushar.aem.guides.core.models;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.tushar.aem.guides.core.services.QueryService;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
adapters=MySlingModelInterface.class,
defaultInjectionStrategy =DefaultInjectionStrategy.OPTIONAL,
resourceType = "tushar/components/mycomponent")
public class MySlingModel implements MySlingModelInterface {

    @Inject
    @Optional
    private Page currentPage;

    @Inject
    @Via("resource")
    private String username ;

    @ValueMapValue
    private String designation;

    @ValueMapValue
    @Named("jcr:createdBy")
    private String createdBy;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private SlingScriptHelper scriptHelper; // Injects the SlingScriptHelper

    @ValueMapValue(name="Value", injectionStrategy= InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;


    @ValueMapValue
    @Default(intValues = 0)
    private int viewCount;

    @Self
    private SlingHttpServletRequest request;

    @Self
    private Resource resource;

    @ScriptVariable
    private Page svcurrentPage;

    @ScriptVariable
    protected ComponentContext componentContext;

    @ScriptVariable
    private Resource svresource;

    @ScriptVariable
    private ResourceResolver svresourceResolver;

    @ChildResource(name = "details")//Resource must be present
    private Resource crdetails; // Injects the 'details' child resource

    @ChildResource(name = "items")
    private List<Resource> items; // Injects the 'items' child resources

    @ResourcePath(path = "/content/tushar")
    private Resource referencedResource; // Injects the resource at the specified path

    @RequestAttribute(name = "myAttribute")
    private String myAttribute; // Injects the request attribute


    @ValueMapValue
    @Via("details") // Specifies that the value should be taken from the "details" child resource
    private String title;

    @OSGiService
    UserManager us;


    public Map<String,Object> details=new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {

        details.put("@Inject private String userName",currentPage.getPath());
        details.put("@Inject @Via(\"resource\") private String username",username);
        details.put("@ValueMapValue private String designation",designation);
        details.put("@ValueMapValue @Named(\"jcr:createdBy\") private String createdBy",createdBy);
        details.put("@SlingObject\n" +
                "    private Resource currentResource;",currentResource.getPath());
        details.put("@SlingObject\n" +
                "    private ResourceResolver resourceResolver;",resourceResolver.getUserID());
        details.put("@SlingObject\n" +
                "    private SlingScriptHelper scriptHelper",scriptHelper.getScript().getScriptResource().getPath());
        details.put("@ValueMapValue(name=\"Value\", injectionStrategy= InjectionStrategy.OPTIONAL)\n" +
                "    @Default(values=\"No resourceType\")\n" +
                "    protected String resourceType",resourceType);
        details.put(" @ValueMapValue\n" +
                "    @Default(intValues = 0)\n" +
                "    private int viewCount;",viewCount);
        details.put("    @Self\n" +
                "    private SlingHttpServletRequest request;",request.getRequestPathInfo().getResourcePath());
//        details.put("@Self\n" +
//                "     private Resource resource;",resource.getPath());
//        details.put("@ScriptVariable\n" +
//                "    private Page svcurrentPage;",svcurrentPage.getName());
//        details.put("@ScriptVariable\n" +
//                "    protected ComponentContext componentContext;",componentContext.getDecorationTagName());
//        details.put("@ScriptVariable\n" +
//                "    private Resource svresource;",svresource.getPath());
//        details.put("@ScriptVariable\n" +
//                "    private ResourceResolver svresourceResolver;",svresourceResolver.getUserID());
        details.put("@ChildResource(name = \"details\")\n" +
                "    private Resource crdetails",crdetails.getPath());
        details.put("@ChildResource(name = \"items\")\n" +
                "    private List<Resource> items",items.stream()
                .map(Resource::getName)
                .collect(Collectors.toList()));

        details.put("@ResourcePath(path = \"/content/tushar\")\n" +
                "    private Resource referencedResource",referencedResource.getPath());
        details.put("@RequestAttribute(name = \"myAttribute\")\n" +
                "    private String myAttribute",myAttribute);
        details.put("@ValueMapValue\n" +
                "    @Via(\"details\") private String title",title);
//        details.put(" @OSGiService\n" +
//                "    UserManager us",us.toString());

        logger.error("Postconstruct Finished");


    }


//    @Override
    public Map<String,Object> getDetails(){
        return details;
    }

}