package com.tushar.aem.guides.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service= Runnable.class, immediate = true)
@Designate(ocd=PracticeSchedulerConfiguration.class)
public class PracticeScheduledTask  implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(final PracticeSchedulerConfiguration config) {

        logger.error(" PracticeScheduledTask activate method called");

        // Execute this method to add scheduler.
        addScheduler(config);

    }

    // Add all configurations to Schedule a scheduler depending on name and expression.
    public void addScheduler(PracticeSchedulerConfiguration config) {
        logger.error("Scheduler added successfully >>>>>>>   ");
        if (config.enable_scheduler()) {
            ScheduleOptions options = scheduler.EXPR(config.scheduler_expression());
            options.name(config.scheduler_name());
            options.canRunConcurrently(config.concurrent_scheduler());

            // Add scheduler to call depending on option passed.
            scheduler.schedule(this, options);
            logger.error("Scheduler added successfully name='{}'", config.scheduler_name());
        } else {
            logger.error("SimpleScheduledTask disabled");
        }
    }


    // Custom method to deactivate or unschedule scheduler
    public void removeScheduler(PracticeSchedulerConfiguration config) {
        scheduler.unschedule(config.scheduler_name());
    }

    // On deactivate component it will unschedule scheduler
    @Deactivate
    protected void deactivate(PracticeSchedulerConfiguration config) {
        removeScheduler(config);
    }

    // On component modification change status will remove and add scheduler
    @Modified
    protected void modified(PracticeSchedulerConfiguration config) {
        removeScheduler(config);
        addScheduler(config);
    }

    // run() method will get call every minute
    @Override
    public void run() {
        logger.error("PracticeScheduledTask running >>>>>>>>>>>");
    }
}