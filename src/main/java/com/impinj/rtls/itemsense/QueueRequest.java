package com.impinj.rtls.itemsense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ralemy on 8/7/16.
 * Json object for registering to a queue;
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueRequest {
    private String epc;
    private String fromZone;
    private String toZone;
    private Double distance;
    private Boolean zoneTransitionOnly;
}
