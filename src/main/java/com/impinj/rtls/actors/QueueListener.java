package com.impinj.rtls.actors;

import akka.actor.UntypedActor;
import com.impinj.rtls.itemsense.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;

/**
 * Created by ralemy on 8/7/16.
 */
@Named("QueueListener")
@Scope("prototype")
public class QueueListener extends UntypedActor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    final QueueService tagConsumer;



    @Inject
    public QueueListener(@Named("QueueService") QueueService queueService){
        tagConsumer = queueService;
    }



    @Override
    public void postStop(){
        log.info("Post stop in AMQP Listener");
        processRequest(new Stop());
    }

    @Override
    public void onReceive(Object message){
        if(message instanceof Start)
            processRequest((Start) message);
        else if (message instanceof Stop)
            processRequest((Stop) message);
        else
            unhandled(message);
    }

    private void processRequest(Stop message) {
        tagConsumer.stopQueue();
    }

    private void processRequest(Start message) {
        try{
            tagConsumer.connectToItmeSense(message.itemsense,message.username,message.password);
        }catch(IOException e){
            log.error("Error connecting to itemsense: "+e.getMessage());
        }
    }


    public static class Start {
        public Api itemsense;
        public String username;
        public String password;

        public Start(Api itemsense, String user, String pass) {
            this.itemsense=itemsense;
            this.username = user;
            this.password = pass;
        }
    }

    public static class Stop {
    }
}
