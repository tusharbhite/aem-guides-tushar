package com.tushar.aem.guides.core.services.learning;

public interface Activities {
    String getRandomActivity();
    String getCurrentString();


}

/*
//Groovyscript to Invoke Service Method

    def activityService = getService("com.tushar.aem.guides.core.services.learning.Activities")
    println "Random Activity is: " + activityService.getRandomActivity(); 
*/