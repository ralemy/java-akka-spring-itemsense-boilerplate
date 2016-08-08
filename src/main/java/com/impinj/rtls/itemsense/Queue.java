package com.impinj.rtls.itemsense;


/**
 * Created by ralemy on 8/6/16.
 * calls Itemsense to register to a queue;
 */
public class Queue {
    public static QueueResponse register(Api api,QueueRequest request){
        return api.post(Api.REGISTER_QUEUE,QueueResponse.class,request);
    }
}
