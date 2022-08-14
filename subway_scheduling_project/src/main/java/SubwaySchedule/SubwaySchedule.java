package SubwaySchedule;


import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Scanner;
public class SubwaySchedule {
    public static void main(String[] args) throws IOException {
        // read in string

        //String dataRequested = args[0];
        //String stationName = args[1];
        //String currentTime = args[2];

        String data = new String(Files.readAllBytes(Paths.get("./subwayData.json")));
        JSONObject jsonObject = new JSONObject(data);
        // System.out.println("data is: " + data);

        String region = jsonObject.getJSONObject("data").getString("region");

        ArrayList<String> subwayLines = HelperClassSubway.extractSubwayLines(jsonObject);
        SubwayNetwork subwayNetwork = new SubwayNetwork(region, subwayLines);

        //System.out.println("subwayLines is: " + subwayLines);
        HelperClassSubway.addStationsToSubwayNetwork(jsonObject, subwayNetwork);
        //Gson gson = new Gson();
        //System.out.println("subwayNetwork populated is: " +  gson.toJson(subwayNetwork));
        //System.out.println("subwayNetwork populated is: " +  subwayNetwork.stationsList);

        //System.out.println("subwayNetwork populated is: " +  subwayNetwork.stationsList);
        for (Map.Entry<String, Station> entry : subwayNetwork.stationsList.entrySet()) {
            String key = entry.getKey();
            Station value = entry.getValue();
            //System.out.println("For station: " + key + "the station Data is: " + value);
            HashMap<String, ArrayList<LocalTime>> scheduleMap = value.schedule;
            for (Map.Entry<String, ArrayList<LocalTime>> entry2 : scheduleMap.entrySet()) {
                String key2 = entry2.getKey();
                ArrayList<LocalTime> value2 = entry2.getValue();
               // System.out.println("For station: " + key + " for direction " + key2 + "the Schedule Data is: " + value2);
            }
        }

        RequestDetails requestDetails = new RequestDetails();
        requestDetails.getInputForRequestDetails();

        if (requestDetails.informationRequested.equals("schedule")){
            getSchedule(subwayNetwork, requestDetails);
        } else if (requestDetails.informationRequested.equals("nextArrival")){
            getNextArrivalTime(subwayNetwork, requestDetails);
        }
        Boolean continueProgram = true;

        while (continueProgram){
            System.out.println("Do you want to continue getting schedules or next arrival times? Enter Y for yes, or N for no (to exit program");
            Scanner in = new Scanner(System.in);
            String userInput = in.nextLine();
            if (userInput.equals("N")) {
                continueProgram = false;
            } else {
                requestDetails.getInputForRequestDetails();

                if (requestDetails.informationRequested.equals("schedule")){
                    getSchedule(subwayNetwork, requestDetails);
                } else if (requestDetails.informationRequested.equals("nextArrival")){
                    getNextArrivalTime(subwayNetwork, requestDetails);
                }
            }
        }
    }
    public static void getSchedule(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        HashMap<String, ArrayList<LocalTime>> stationSchedule = subwayNetwork.getStationSchedule(requestDetails.stationRequested);

        if (stationSchedule != null){
            for (Map.Entry<String, ArrayList<LocalTime>> entry : stationSchedule.entrySet()) {
                String key = entry.getKey();
                ArrayList<LocalTime> value = entry.getValue();
                System.out.println("For station: " + key + " for direction " + key + "the Schedule Data is: " + value);
            }
        }
    }
    public static void getNextArrivalTime(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        LocalTime nextArrivalTime = subwayNetwork.getNextArrivalTime(requestDetails.stationRequested, requestDetails.timeProvided, requestDetails.direction);
        if (nextArrivalTime != null){
            System.out.println("For station: " + requestDetails.stationRequested + " the next arrival time is: " + nextArrivalTime + " for direction: " + requestDetails.direction);
        } else {
            System.out.println("Invalid Input provided.");
        }
    }
}

