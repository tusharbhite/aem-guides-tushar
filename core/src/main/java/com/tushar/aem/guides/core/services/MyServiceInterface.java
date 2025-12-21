package com.tushar.aem.guides.core.services;

public interface MyServiceInterface {
    public String getRandomActivity();
    public String getCurrentString();

}

/*
//Groovyscript to Invoke Service Method

    def activityService = getService("com.tushar.aem.guides.core.services.learning.Activities")
    println "Random Activity is: " + activityService.getRandomActivity(); 
*/