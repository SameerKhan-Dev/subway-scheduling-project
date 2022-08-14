package SubwaySchedule;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalTime; // import the LocalTime class
import java.util.ArrayList;
import java.util.HashMap;

public class HelperClassSubway {
    private HelperClassSubway() {}

    public static ArrayList<String> extractSubwayLines(JSONObject jsonObject){
        JSONArray subwayLinesArrJson = jsonObject.getJSONObject("data").getJSONArray("subwayLines");

        ArrayList<String> subwayLines = new ArrayList<String>(subwayLinesArrJson.length());

        for(int i = 0; i < subwayLinesArrJson.length(); i++){
            subwayLines.add(subwayLinesArrJson.getString(i));
        }
        return subwayLines;
    }
    public static void addStationsToSubwayNetwork(JSONObject jsonObject, SubwayNetwork subwayNetwork){
        JSONArray stationsArrJson = jsonObject.getJSONObject("data").getJSONArray("stations");
        for(int i = 0; i < stationsArrJson.length(); i++) {
            // extract relevant station data such as name, subwayLines details, directions, schedule etc.
            String stationName = stationsArrJson.getJSONObject(i).getString("name");

            JSONArray subwayLinesJson = stationsArrJson.getJSONObject(i).getJSONArray("subwayLines");
            ArrayList<String> subwayLinesForStation = extractStationSubwayLinesData(subwayLinesJson);

            JSONArray directionsJson = stationsArrJson.getJSONObject(i).getJSONArray("directions");
            ArrayList<String> directions = extractStationDirectionsData(directionsJson);

            Boolean goTransit = stationsArrJson.getJSONObject(i).getBoolean("goTransit");
            Boolean viaRail = stationsArrJson.getJSONObject(i).getBoolean("viaRail");
            Boolean isInterchange = stationsArrJson.getJSONObject(i).getBoolean("isInterchange");

            JSONObject scheduleJson = stationsArrJson.getJSONObject(i).getJSONObject("schedule");
            HashMap<String, ArrayList<LocalTime>> schedule = extractStationScheduleData(scheduleJson, directions);

            Station station = new Station(stationName, schedule, subwayLinesForStation, directions, goTransit, viaRail, isInterchange);
            subwayNetwork.addStation(station);
        }
    }

    public static  ArrayList<String> extractStationSubwayLinesData(JSONArray subwayLinesJson){
        ArrayList<String> subwayLinesForStation = new ArrayList<String>(subwayLinesJson.length());

        for(int j = 0; j < subwayLinesJson.length(); j++){
            subwayLinesForStation.add(subwayLinesJson.getString(j));
        }
        return subwayLinesForStation;
    }

    public static  ArrayList<String> extractStationDirectionsData(JSONArray directionsJson){
        ArrayList<String> directions = new ArrayList<String>(directionsJson.length());

        for(int l = 0; l < directionsJson.length(); l++){
            directions.add(directionsJson.getString(l));
        }
        return directions;
    }

    public static HashMap<String, ArrayList<LocalTime>> extractStationScheduleData(JSONObject scheduleJson,ArrayList<String> directions ){
        HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();

        for (int k = 0; k < directions.size(); k++){
            ArrayList<LocalTime> scheduleForDirection = new ArrayList<LocalTime>();
            String currentDirection = directions.get(k);
            //System.out.println("currentDirection is: for Station: " + name + " : " + currentDirection);
            schedule.put(currentDirection, scheduleForDirection);

            JSONArray scheduleForDirectionJson = scheduleJson.getJSONArray(currentDirection);
            for(int s = 0; s < scheduleForDirectionJson.length(); s++){
                String timeInStringFormat = scheduleForDirectionJson.getString(s);
                LocalTime time = LocalTime.parse(timeInStringFormat);
                schedule.get(currentDirection).add(time);
            }

        }
        return schedule;
    }
}
