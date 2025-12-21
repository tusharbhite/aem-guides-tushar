package com.tushar.aem.guides.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "Student Details", description = "Enter your Students details here" ) 
public @interface MyConfigServiceInterface {
    
    
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

    @AttributeDefinition(name = "CGPA", type = AttributeType.DOUBLE)
    public String getCGPA() default "3.5";

    @AttributeDefinition(name = "Grade", type = AttributeType.CHARACTER)
    public String getGrade() default "C";

    @AttributeDefinition(name = "Password", type = AttributeType.PASSWORD)
    public String getPassword() default "flkn2398u4r^%$^&E*%^5";


    /* FLOAT - float data type is a single-precision floating-point type,
     used for numbers with fractional components (decimals).
     By default, decimal numbers are treated as double in Java, so the F or f
    Size: 4 bytes (32 bits).
Precision: Up to 7 decimal digits. F or f suffix*/
    @AttributeDefinition(name = "Percentages", type = AttributeType.FLOAT)
    public float getPercentages() default 19.99f;

    /* BYTE - data type is the smallest integer type, using 8 bits of memory.
     , it is suitable for saving memory in large arrays where values are within its limited range
    Size: 1 byte (8 bits) and Range: -128 to 127.*/


    @AttributeDefinition(name = "AVG Marks", type = AttributeType.BYTE)
    public  byte getAvgMarks() default 30;


/* LONG- long data type is used for large integer values
that the default int type (32-bit) cannot accommodate.
 A suffix of L or l
 Size: 8 bytes (64 bits).
Range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807.  Prefix L or l */
    @AttributeDefinition(name = "Phone Number", type = AttributeType.LONG)
    public long getPhoneNumber() default 8000000000l;

/*SGORT , data type uses twice the memory of a byte and is also used for memory optimization
in specific scenarios where the int range is not needed,
Size: 2 bytes (16 bits).
Range: -32,768 to 32,767.*/
    @AttributeDefinition(name = "University Registration Number", type = AttributeType.SHORT)
    public short getURN() default -150;


}