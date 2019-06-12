# SanFrancisco-FoodTrucks

* This Project lists out the Open Food Trucks in the San Francisco at the time of running the application.
For Example, if you are running the application on November, 20th 11:12 am then the application lists out the open Food Trucks at 11:12 am, November, 20th.

* This Application is a command line based application, in which the Available FoodTrucks will list out in command line from where the application runs.


* Follow the command below to build the project and also to generate the jar file whhich contains all dependencies.

```
mvn clean compile assembly:single

```

* Once the jar is generated, then run the command below to execute the application.

```
java -cp FoodTruck-1.0-SNAPSHOT-jar-with-dependencies.jar com.FoodTruck.FoodTruck.FoodTruckFinder

```

* The FoodTruck Application generates the available FoodTrucks in the pages of 10, where initially the application generates Page of 10 and asks the user input inorder to see the next page of 10.
