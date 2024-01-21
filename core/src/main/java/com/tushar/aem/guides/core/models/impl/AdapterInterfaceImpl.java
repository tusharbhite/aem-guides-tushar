package com.tushar.aem.guides.core.models.impl;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;
import com.tushar.aem.guides.core.models.AdapterInterface;
import com.tushar.aem.guides.core.models.ModelUsingResourceClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Model(adaptables = {Resource.class}, adapters = {AdapterInterface.class})
//@Model(adaptables = Resource.class, adapters = AdapterInterface.class)
public class AdapterInterfaceImpl implements AdapterInterface {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    private String message;
    @ScriptVariable
    private Resource resource;

  // Injects the componentContext script variable
  @ScriptVariable(name = "componentContext")
  private ComponentContext componentContext;

  // Injects the currentNode script variable
  @ScriptVariable
  @Named("currentNode")
  private Node currentNode;

    @Inject 
    @Default(values="No String")
    private String temporary; 

    @Inject
    @Default(values = "noooo")
    private String listFrom;

    @Inject
    @Named("jcr:primaryType")
    @Default(values = "no primaryTypeString")
    private String primaryTypeString;


    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n";
    }

    @Override
    public String getMessage() {
        return message;
    }

     public String getString() {
        return temporary;
    }

    public String getListFrom(){
        return listFrom;
    }
    public String getPrimaryTypeString(){
        return primaryTypeString;
    }


    public String nonGetterMethod(){
        return "nongettermethodstring";
    }

    public String getCurrentClassName(){
        return this.getClass().getName();
    }

        public List<String> getListOfAllMethodsInCurrentClass(){
        List<String> result=new ArrayList<String>();
        Method[] methods = ModelUsingResourceClass.class.getDeclaredMethods();
        for (Method method : methods) {
          //System.out.println(method.getName());
          result.add(method.getName());
        }
        return result;
    }
}
