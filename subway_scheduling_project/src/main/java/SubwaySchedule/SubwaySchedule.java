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


        /*
        String stationRequested = null;
        // extract args arguments

        if (args[0] == "schedule"){
            if (args[1]){

            } else {

            }
        } else if (args[0] == "nextArrival"){
            if (args[1] && args[2]){

            } else {

            }
        } else {
            System.out.println("Incorrect Input: Please run program with command line arguments in the following format:");
            System.out.println("Input 1: Specify information type: schedule or nextArrival");
            System.out.println("Input 2: Specify station name: e.g type: Bloor-Yonge");
            System.out.println("Input 3: If ");
        }
            */
        /*
          Def get_nextArrival_time(station_name, direction, time): {
		// Confirm time is in valid format:

		Return subwayNetwork.get_next_arrival_time_for_station (station_name, time, direction)

        }
        Def get_Station_Schedules(station_name): {
            Return subwayNetwork.getSchedules(station_name)

        }




         */


    }
    public static void getSchedule(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        HashMap<String, ArrayList<LocalTime>> stationSchedule = subwayNetwork.getStationSchedule(requestDetails.stationRequested);

        if (stationSchedule != null){
            for (Map.Entry<String, ArrayList<LocalTime>> entry2 : stationSchedule.entrySet()) {
                String key2 = entry2.getKey();
                ArrayList<LocalTime> value2 = entry2.getValue();
                System.out.println("For station: " + key2 + " for direction " + key2 + "the Schedule Data is: " + value2);
            }
        }
    }
    public static void getNextArrivalTime(SubwayNetwork subwayNetwork, RequestDetails requestDetails){

    }
}

