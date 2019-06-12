

package com.FoodTruck.FoodTruck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.*;
import java.text.*;

public class FoodTruckFinder {
    public static void main(String[] args) {

        // Function to call the API and get the data from API
        gettingTheFoodTrucksFromAPI();

    }

    public static void gettingTheFoodTrucksFromAPI(){
        // Code to call the API and get the data from API and Pass the input data to print the FoodTrucks List
        try {
            URL url = new URL("http://data.sfgov.org/resource/bbb8-hzi6.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String input_line;

            input_line = rd.readLine();
            if(input_line != null) {
                // Function to get and print the Top 10 Food Trucks available at the time of running the program.
                getTop10FoodTrucks(input_line, rd);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getTop10FoodTrucks(String input_line, BufferedReader rd) throws IOException {

        // Code to get and print the Top 10 Food Trucks available at the time of running the program.
        List<String> restaurantsList = new ArrayList<String>();
        int user_input = 1;

        do {
            Constants constants = new Constants();
            JSONObject myResponse = new JSONObject(input_line.substring(1));
            String startTime = myResponse.getString(constants.START_TIME_24);
            String endTime = myResponse.getString(constants.END_TIME_24);
            String day = myResponse.getString(constants.DAY_OF_WEEK);

            // Function to check whether the Food Truck time, date matches the current time of running the program
            if (checkTime(startTime, endTime, day)) {
                if (restaurantsList.size() < 10) {
                    restaurantsList.add(input_line);
                } else {
                    // Function to print the Top 10 Available FoodTrucks stores in the restaurantsList List
                    printTop10(restaurantsList);
                    restaurantsList = new ArrayList<String>();
                    restaurantsList.add(input_line);
                    Scanner reader = new Scanner(System.in);
                    System.out.println("\n");
                    System.out.println("Enter 1 if you want to view more or else Enter 0 to exit from system: ");
                    user_input = reader.nextInt();
                }
            }
        } while (user_input == 1 && ((input_line = rd.readLine()) != null));
        rd.close();
        if(user_input == 1)
            printTop10(restaurantsList);
    }


    public static boolean checkTime(String startTime , String endTime, String day){
        // Code to check whether the Food Truck time, date matches the current time of running the program
        String[] startTimeArray = startTime.split(":");
        int startHour = Integer.valueOf(startTimeArray[0]);
        int startMinutes = Integer.valueOf(startTimeArray[1]);


        String[] eTimeArray = endTime.split(":");
        int endHour = Integer.valueOf(eTimeArray[0]);
        int endMinutes = Integer.valueOf(eTimeArray[1]);

        Calendar cal = Calendar.getInstance();
        int current_hours = cal.getTime().getHours();
        int current_minutes = cal.getTime().getMinutes();
        Date date = cal.getTime();

        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());


        if(day.equals(currentDay)
                && (startHour <= current_hours && startMinutes <= current_minutes)
                || (endHour > current_hours && endMinutes > current_minutes)) {
            return true;
        }
        return false;
    }

    public static void printTop10(List<String> restaurantsList){
        // Code to print the Top 10 Available FoodTrucks stores in the restaurantsList List
        List<ApplicantAddress> applicantList = new ArrayList<ApplicantAddress>();

        Constants constants = new Constants();

        for(String restaurant : restaurantsList) {
            JSONObject myResponse = new JSONObject(restaurant.substring(1));
            String Location = myResponse.getString(constants.LOCATION);
            String Applicant = myResponse.getString(constants.APPLICANT);
            ApplicantAddress applicantAddressObject = new ApplicantAddress(Applicant, Location);
            applicantList.add(applicantAddressObject);
        }

        // Function to sort the Top 10 Available FoodTrucks by name and if both names are equal, sorting is done based on the address.
        PriorityQueue<ApplicantAddress> pq = sortTheTop10(applicantList);

        while(!pq.isEmpty()){
            ApplicantAddress pollObject = pq.poll();
            System.out.println(pollObject.Applicant + " " + pollObject.Address);
        }
    }

    public static PriorityQueue<ApplicantAddress> sortTheTop10(final List<ApplicantAddress> applicantList ){
        // Code to sort the Top 10 Available FoodTrucks by name and if both names are equal, sorting is done based on the address.
        // Here ApplicantAddress is seperate class that stores the applicant name and address.
        PriorityQueue<ApplicantAddress> pq = new PriorityQueue<ApplicantAddress>(10, getComparator());
        pq.addAll(applicantList);
        return pq;
    }

    private static Comparator<ApplicantAddress> getComparator() {
        // Custom comparator function to sort the ApplicantAddress objects.
        return new Comparator<ApplicantAddress>() {
            public int compare(ApplicantAddress o1, ApplicantAddress o2) {
                int whoIsFirst = o1.Applicant.compareTo(o2.Applicant);
                if(whoIsFirst < 0)
                    return -1;

                if(whoIsFirst == 0 && o1.Address.compareTo(o2.Address) < 0)
                    return -1;

                return 1;
            }
        };
    }


}


// to run:
// mvn clean compile assembly:single
// java -cp ~/FoodTruck/target/FoodTruck-1.0-SNAPSHOT-jar-with-dependencies.jar com.FoodTruck.FoodTruck.FoodTruckFinder


