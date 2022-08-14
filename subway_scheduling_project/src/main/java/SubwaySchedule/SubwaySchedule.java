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

public class SubwaySchedule {
    public static void main(String[] args) throws IOException {
        // read in string
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
        System.out.println("subwayNetwork populated is: " +  subwayNetwork.stationsList);

        System.out.println("subwayNetwork populated is: " +  subwayNetwork.stationsList);
        for (Map.Entry<String, Station> entry : subwayNetwork.stationsList.entrySet()) {
            String key = entry.getKey();
            Station value = entry.getValue();
            //System.out.println("For station: " + key + "the station Data is: " + value);
            HashMap<String, ArrayList<LocalTime>> scheduleMap = value.schedule;
            for (Map.Entry<String, ArrayList<LocalTime>> entry2 : scheduleMap.entrySet()) {
                String key2 = entry2.getKey();
                ArrayList<LocalTime> value2 = entry2.getValue();
                System.out.println("For station: " + key + " for direction " + key2 + "the Schedule Data is: " + value2);
            }

        }

        /* 32 to
        JSONArray stationsArrJson = jsonObject.getJSONObject("data").getJSONArray("stations");

        for(int i = 0; i < stationsArrJson.length(); i++){
            //subwayLines.add(subwayLinesArrJson.getString(i));
            //public Station(String name, HashMap<String, ArrayList< LocalTime >> schedule, ArrayList<String> subwayLines, ArrayList<String> directions, Boolean goTransit, Boolean viaRail, Boolean isInterchange){
            String name = stationsArrJson.getJSONObject(i).getString("name");
            //System.out.println("name: " + name);
            JSONArray subwayLinesJson = stationsArrJson.getJSONObject(i).getJSONArray("subwayLines");

            ArrayList<String> subwayLinesForStation = new ArrayList<String>();

            for(int j = 0; j < subwayLinesJson.length(); j++){
                subwayLinesForStation.add(subwayLinesJson.getString(j));
            }
            //System.out.println("subwayLines for station: " + name + ": are: " + subwayLinesForStation);

            JSONArray directionsJson = stationsArrJson.getJSONObject(i).getJSONArray("directions");

            ArrayList<String> directions = new ArrayList<String>(directionsJson.length());

            for(int l = 0; l < directionsJson.length(); l++){
                directions.add(directionsJson.getString(l));
            }

            //System.out.println("directions for station: " + name + ": are: " + directions);
            // Boolean goTransit, Boolean viaRail, Boolean isInterchange
            //String stationName = stationsArrJson.getJSONObject(i).getString("name");
            Boolean goTransit = stationsArrJson.getJSONObject(i).getBoolean("goTransit");
            Boolean viaRail = stationsArrJson.getJSONObject(i).getBoolean("viaRail");
            Boolean isInterchange = stationsArrJson.getJSONObject(i).getBoolean("isInterchange");

            //System.out.println("Booleans for  station: " + name + ": are: " + goTransit + " " + viaRail + "  " + isInterchange);

            JSONObject scheduleJson = stationsArrJson.getJSONObject(i).getJSONObject("schedule");
            HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();

            for (int k = 0; k < directions.size(); k++){
                ArrayList<LocalTime> scheduleForDirection = new ArrayList<LocalTime>();
                String currentDirection = directions.get(k);
                System.out.println("currentDirection is: for Station: " + name + " : " + currentDirection);
                schedule.put(currentDirection, scheduleForDirection);

                JSONArray scheduleForDirectionJson = scheduleJson.getJSONArray(currentDirection);
                for(int s = 0; s < scheduleForDirectionJson.length(); s++){
                    String timeInStringFormat = scheduleForDirectionJson.getString(s);
                    LocalTime time = LocalTime.parse(timeInStringFormat);
                    schedule.get(currentDirection).add(time);
                }

            }
            // System.out.println("schedule is: for station:  " +name + " : " + schedule);

            //stationsArrJson.getJSONObject(i).getJSONObject("schedule").get
            end 86 */
    }
}

