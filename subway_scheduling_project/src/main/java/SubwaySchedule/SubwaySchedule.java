package SubwaySchedule;


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


public class SubwaySchedule {
    public static void main(String[] args) throws IOException {
        // read in string
        String data = new String(Files.readAllBytes(Paths.get("./subwayData.json")));
        JSONObject jsonObject = new JSONObject(data);
        System.out.println("data is: " + data);
        //String region = String.valueOf(jsonObject.getJSONObject("region"));

        //String region = String.valueOf(jsonObject.getString("region"));
        String region = jsonObject.getJSONObject("data").getString("region");

        //jsonObject.getString()
        //System.out.println("region is: " + region);
        JSONArray subwayLinesArrJson = jsonObject.getJSONObject("data").getJSONArray("subwayLines");

        ArrayList<String> subwayLines = new ArrayList<String>(subwayLinesArrJson.length());

        for(int i = 0; i < subwayLinesArrJson.length(); i++){
            subwayLines.add(subwayLinesArrJson.getString(i));
        }

       // System.out.println("subwayLines is: " + subwayLines);

        SubwayNetwork subwayNetwork = new SubwayNetwork(region, subwayLines);

        JSONArray stationsArrJson = jsonObject.getJSONObject("data").getJSONArray("stations");

        for(int i = 0; i < stationsArrJson.length(); i++){
            //subwayLines.add(subwayLinesArrJson.getString(i));
            //public Station(String name, HashMap<String, ArrayList< LocalTime >> schedule, ArrayList<String> subwayLines, ArrayList<String> directions, Boolean goTransit, Boolean viaRail, Boolean isInterchange){
            String name = stationsArrJson.getJSONObject(i).getString("name");
            //System.out.println("name: " + name);
            JSONArray subwayLinesJson = stationsArrJson.getJSONObject(i).getJSONArray("subwayLines");

            ArrayList<String> subwayLinesForStation = new ArrayList<String>(subwayLinesArrJson.length());

            for(int j = 0; j < subwayLinesJson.length(); j++){
                subwayLinesForStation.add(subwayLinesJson.getString(j));
            }
            //System.out.println("subwayLines for station: " + name + ": are: " + subwayLinesForStation);

            JSONArray directionsJson = stationsArrJson.getJSONObject(i).getJSONArray("directions");

            ArrayList<String> directions = new ArrayList<String>(directionsJson.length());

            for(int l = 0; l < directionsJson.length(); l++){
                directions.add(directionsJson.getString(l));
            }

            System.out.println("directions for station: " + name + ": are: " + directions);

            //HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();
        }






        // ArrayList<String> subwayLines = jsonObject.getJSONArray("subwayLines");


        //read as json array
        /*
        JSONArray jsonArray = new JSONArray(data);
        for(int i = 0; i < jsonArray.length(); i++){
            // parse in json object
//            JSONObject object = jsonArray.getJSONObject(i);
//            or
            String str = jsonArray.get(i).toString();
            JSONObject object1 = new JSONObject(str);

            String name = object1.getString("name");
            int age = object1.getInt("age");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);

        }
        */
    }
}

