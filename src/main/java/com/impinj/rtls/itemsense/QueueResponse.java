package com.impinj.rtls.itemsense;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ralemy on 8/6/16.
 * Jackson marshaller for Itemsense queue registration response.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueResponse {

    private String serverUrl;
    private String queue;

    public QueueResponse() {

    }


    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String value) {
        serverUrl = value;
    }


    public String getQueue() {
        return queue;
    }

    public void setQueue(String value) {
        queue = value;
    }

    @Override
    public String toString() {
        return "Itemsense Queue (serverUrl = " + serverUrl + ", queue = " + queue + ")";
    }
}
