package com.example.hubspot.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactCreationPayload {
    private long objectId;
    private String propertyName;
    private String propertyValue;
    private String changeSource;
    private long eventId;
    private long subscriptionId;
    private long portalId;
    private long occurredAt;
    private String subscriptionType;


}