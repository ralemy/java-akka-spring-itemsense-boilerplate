# java-akka-spring-itemsense-boilerplate
This is a boilerplate project to demonstrate how to use Spring Boot 
framework to connect to Impinj Itemsense and register to its AMQP message
broker.

It uses Akka actors to spawn a new thread for handling messages.

To configure, put a file called application.properties in classpath which 
has a key family of itemsense containing baseUrl, username, and password
keys:


'''
itemsense.baseUrl: http://<example,com>/
itemsense.username: myusername
itemsense.password: mypassword
'''