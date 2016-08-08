package connector;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.impinj.rtls.actors.QueueListener;
import com.impinj.rtls.actors.QueueService;
import com.impinj.rtls.itemsense.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.impinj.rtls.actors.SpringExtension.SpringExtProvider;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private Api itemsense;
    private ActorRef amqp;

    @Autowired
    private ItemsenseProperties config;

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    public void run(String... args) throws Exception {
        itemsense = new Api(config.baseUrl,config.username,config.password);
        amqp = startAMQPActor();
        amqp.tell(new QueueListener.Start(itemsense,config.username,config.password),null);
    }
    private ActorRef startAMQPActor(){
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ApplicationConfiguration.class,QueueListener.class, QueueService.class);
        ActorSystem system = ctx.getBean(ActorSystem.class);
        return system.actorOf(
                SpringExtProvider.get(system).props("QueueListener"), "QueueListener");

    }
}