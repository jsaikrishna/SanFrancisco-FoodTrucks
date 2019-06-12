# SanFrancisco-FoodTrucks

* This Application lists out the Open Food Trucks in San Francisco at the time of running the Application.
For Example, if you are running the Application on November, 20th 11:12 am, then the application lists out the open Food Trucks at 11:12 am, November, 20th.

* This Application is a command line based Application, in which the Available FoodTrucks will list out in command line from where the Application runs.


* Follow the command below to build the project and also to generate the jar file which contains all dependencies.

```
mvn clean compile assembly:single

```

* Once the jar is generated, then run the command below to execute the application.

```
java -cp FoodTruck-1.0-SNAPSHOT-jar-with-dependencies.jar com.FoodTruck.FoodTruck.FoodTruckFinder

```

* The FoodTruck Application generates the available FoodTrucks in the pages of 10, where initially the Application outputs Page of 10 and asks the user input to see the next Page of 10.

* The image below show the sample command line output of the Application where initially it generates the open FoodTrucks in the page of 10, and asks the user input if the user wants to see next page of 10 or not, if yes the user needs to enter 1, else to exit the program the user needs to enter 0.

# Sample Output
![Sample Output](https://github.com/jsaikrishna/SanFrancisco-FoodTrucks/blob/master/SampleOutput.png)
