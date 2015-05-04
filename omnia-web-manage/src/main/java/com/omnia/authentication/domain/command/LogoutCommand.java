package com.omnia.authentication.domain.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Administrator on 2015/5/4.
 */
public final class LogoutCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String description;

    public LogoutCommand(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
