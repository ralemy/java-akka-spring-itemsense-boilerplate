# java-akka-spring-itemsense-boilerplate
This is a boilerplate project to demonstrate how to use Spring Boot 
framework to connect to Impinj Itemsense and register to its AMQP message
broker.

It uses Akka actors to spawn a new thread for handling messages.

To configure, put a file called application.properties in classpath which 
has a key family of itemsense containing baseUrl, username, and password
keys.

Forexample: `src/main/resources/application.properties`

```
itemsense.baseUrl: http://<example,com>/
itemsense.username: myusername
itemsense.password: mypassword
```


other configuration keys can be specified in the same file for other families: 
```
server.port:8080
```