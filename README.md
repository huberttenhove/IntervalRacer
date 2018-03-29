# IntervalRacer
Very basic browser racing game

Installation manual

Required Software
	Maven
	Glassfish
	
In the project directory run the maven command "mvn clean install"
This will build the project and produce a war file.

deploy the war file in you glassfish server using the glassfish admin console (localhost:4848)
The default settings can be used when deploying.

Goto the following URL on your machine or press the launch button from the admin console of glassfish
	localhost:8080/IntervalRacer
	
Enjoy the race
 
Racing is based on a user session, you need at least two users to start a game. 
