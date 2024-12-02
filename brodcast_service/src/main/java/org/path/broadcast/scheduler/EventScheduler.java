package org.path.broadcast.scheduler;

import org.path.broadcast.manager.EventLogManager;
import org.path.broadcast.model.EventLog;
import org.path.broadcast.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventScheduler {

    private final EventLogManager eventLogManager;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    public EventScheduler(EventLogManager eventLogManager) {
        this.eventLogManager = eventLogManager;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60) // 15 minutes in milliseconds
    public void checkEvents() {
        // Fetch events from the database and process them
        List<EventLog> events = eventLogManager.getRetryEvents();
        for (EventLog event : events) {
            // Call your method with each event
            processEvent(event);
        }
    }

    private void processEvent(EventLog event) {
        // Your processing logic here
        consumerService.consumeMessage(event.getEventString(), 0, event.getId());
        System.out.println("Processing event: " + event);
    }
}
