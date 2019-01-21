package com.crater.api.event.event.listener;

import com.crater.api.event.event.OwnerRegistrationEvent;
import com.crater.api.utils.Emailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEventListener {

    private final Emailer emailer;

    @Autowired
    public RegistrationEventListener(Emailer emailer) {
        this.emailer = emailer;
    }

    @Async
    @EventListener
    public void handleOwnerRegistrationEvent(OwnerRegistrationEvent ownerRegistrationEvent) {
        emailer.sendVerificationEmail(ownerRegistrationEvent.getEmail(), ownerRegistrationEvent.getToken());
    }
}
