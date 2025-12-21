package com.tushar.aem.guides.core.configurations;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = MyServiceInterface.class, immediate = true)
@Designate(ocd = MyConfigServiceInterface.class)
public class MyConfigServiceImpl implements MyServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(MyConfigServiceImpl.class);


    private String studentName;
    private int rollNumber;
    private boolean regular;
    private String[] subjects;
    private String countries;
    
    @Activate()
    protected void start(MyConfigServiceInterface config){
        studentName = config.getStudentName();
        rollNumber = config.getRollNumber();
        regular = config.getRegular();
        subjects = config.getSubjects();
        countries = config.getCountries();
        log.info("MyConfigServiceImpl Activate Method Started "+ "studentName: "+studentName+" rollNumber: "+rollNumber);
    }

    @Override
    public String getStudentName() {
        return studentName;
    }

    @Override
    public int getRollNumber() {
        return rollNumber;
        }

    @Override
    public String[] getSubjects() {
       return subjects;  
    }

    @Override
    public String getCountries() {
        return countries;
    }

    @Override
    public boolean getregular() {
        return regular;
    }

}
/*
Groovyscript
 def testService2 = getService("com.tushar.aem.guides.core.configurations.learning.StudentService")
println "Student Name : " + testService2.getStudentName();
 */
