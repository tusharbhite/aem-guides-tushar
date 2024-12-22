package com.tushar.aem.guides.core.configurations.learning;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

/*
Groovyscript
 def testService2 = getService("com.tushar.aem.guides.core.configurations.learning.StudentService")
println "Student Name : " + testService2.getStudentName();
 */

@Component(service = StudentService.class)
@Designate(ocd = StudentConfiguration.class)
public class StudentConfigurationMethodsImpl implements StudentService{
    


    private String studentName;
    private int rollNumber;
    private boolean regular;
    private String[] subjects;
    private String countries;
    
    @Activate()
    protected void start(StudentConfiguration config){
        studentName = config.getStudentName();
        rollNumber = config.getRollNumber();
        regular = config.getRegular();
        subjects = config.getSubjects();
        countries = config.getCountries();
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
