package com.tushar.aem.guides.core.configurations.learning;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "Student Details", description = "Enter your Students details here" ) 
public @interface StudentConfiguration {
    
    
    @AttributeDefinition(name =  "Student Name", type = AttributeType.STRING,
                           description = "Enter Student Name here")
    public String getStudentName() default "Satyam";    
              
    @AttributeDefinition(name =  "Roll Number", type = AttributeType.INTEGER,
                         description = "Enter Roll Number here")
    public int getRollNumber() default 3;   

    @AttributeDefinition(name =  "Regular", type = AttributeType.BOOLEAN,
                        description = "Is student is regular")
    public boolean getRegular() default true;   

    @AttributeDefinition(name =  "Subjects", type = AttributeType.STRING,
                        description = "See Your Subjects")
    public String[] getSubjects() default {"maths,english,sanskrit"};   

    @AttributeDefinition(name =  "Countries", type = AttributeType.STRING,
                        description = "Select your Countries",
                        options = {
                            @Option(label = "India", value = "india"),
                            @Option(label = "Russia", value = "russia"),
                            @Option(label = "France", value = "france"),
                            @Option(label = "America", value = "america")
                        })
    public String getCountries() default "India";   
}