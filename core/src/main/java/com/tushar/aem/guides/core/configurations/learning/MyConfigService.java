package com.tushar.aem.guides.core.configurations.learning;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.AttributeDefinition;


/*
Groovyscript
def testService = getService("com.tushar.aem.guides.core.configurations.learning.MyConfigService")
println "env var is: " + testService.getEnvVar();
*/ 
/**
 * This is an OSGi component service for an AEM environment.
 * It is designed to fetch and use configuration values defined in OSGi.
 */
@Component(service = MyConfigService.class, immediate = true)
@Designate(ocd = Config.class)
public class MyConfigService {



    // Private member to hold the value of the environment variable
    private String envVar;

    /**
     * The Activate method is called when the OSGi component is activated.
     * 
     * @param config Configuration object containing the property values.
     * 
     * @Activate: Marks the method to be called on component activation.
     */
    @Activate
    protected void activate(Config config) {
        // Assign the environment variable value from the configuration to the class member
        this.envVar = config.envVar();
    }

    // Public getter method to access the environment variable value
    public String getEnvVar() {
        return envVar;
    }
}

    /**
     * Configuration interface for OSGi configuration.
     * 
     * @ObjectClassDefinition: Defines a configuration class.
     * @AttributeDefinition: Defines individual configuration properties.
     */
    @ObjectClassDefinition(name = "My Configuration Service", description = "Service to configure and fetch environment variables.")
    @interface Config {
        
        /**
         * Defines a configuration property.
         * 
         * @return the value of the environment variable
         */
        @AttributeDefinition(name = "Environment Variable", description = "Sample environment variable value.")
        String envVar() default "defaultValue";
    }