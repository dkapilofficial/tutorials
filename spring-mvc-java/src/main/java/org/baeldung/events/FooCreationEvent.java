package org.baeldung.events;

import org.springframework.context.ApplicationEvent;

public class FooCreationEvent extends ApplicationEvent {

    public FooCreationEvent(Object source) {
        super(source);
    }
}
