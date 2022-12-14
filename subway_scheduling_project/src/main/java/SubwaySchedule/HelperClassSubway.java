package SubwaySchedule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

// this class is used as a helper class to help extract json data from file, format it and populate SubwayNetwork object/
public class HelperClassSubway {
    private HelperClassSubway() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    public static ArrayList<String> extractSubwayLines(JSONObject jsonObject) {
        // iterate through json and extract, build and return subwayLines data
        try {
            JSONArray subwayLinesArrJson = jsonObject.getJSONObject("data").getJSONArray("subwayLines");

            ArrayList<String> subwayLines = new ArrayList<String>(subwayLinesArrJson.length());

            for (int i = 0; i < subwayLinesArrJson.length(); i++) {
                subwayLines.add(subwayLinesArrJson.getString(i));
            }
            return subwayLines;
        } catch (Exception e) {
            logger.debug("Exception occurred inside extractSubwayLines method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }
    // helper method to add stations to SubwayNetwork object
    public static void addStationsToSubwayNetwork(JSONObject jsonObject, SubwayNetwork subwayNetwork) {
        // iterate through json to obtain all stations data, and to populate subway network with all stations.
        try {
            JSONArray stationsArrJson = jsonObject.getJSONObject("data").getJSONArray("stations");
            for (int i = 0; i < stationsArrJson.length(); i++) {
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
        } catch (Exception e) {
            logger.debug("Exception occurred inside addStationsToSubwayNetwork method: " + e);
            logger.error("Error occurred in program.");
        }
    }
    // iterate through json data for particular station, and extract the subwayLines data for the station.
    public static ArrayList<String> extractStationSubwayLinesData(JSONArray subwayLinesJson) {
        try {
            ArrayList<String> subwayLinesForStation = new ArrayList<String>(subwayLinesJson.length());

            for (int j = 0; j < subwayLinesJson.length(); j++) {
                subwayLinesForStation.add(subwayLinesJson.getString(j));
            }
            return subwayLinesForStation;
        } catch (Exception e) {
            logger.debug("Exception occurred inside extractStationSubwayLinesData method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }

    // iterate through json data for particular station, and extract the directions data for the station.
    public static ArrayList<String> extractStationDirectionsData(JSONArray directionsJson) {
        try {
            ArrayList<String> directions = new ArrayList<String>(directionsJson.length());

            for (int l = 0; l < directionsJson.length(); l++) {
                directions.add(directionsJson.getString(l));
            }
            return directions;
        } catch (Exception e) {
            logger.debug("Exception occurred inside extractStationSubwayLinesData method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }

    /*
    *  iterate through json data for particular station, and extract the schedule data for the station.
    */
    public static HashMap<String, ArrayList<LocalTime>> extractStationScheduleData(JSONObject scheduleJson, ArrayList<String> directions) {
        try {
            HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();

            for (int k = 0; k < directions.size(); k++) {
                ArrayList<LocalTime> scheduleForDirection = new ArrayList<LocalTime>();
                String currentDirection = directions.get(k);
                schedule.put(currentDirection, scheduleForDirection);

                JSONArray scheduleForDirectionJson = scheduleJson.getJSONArray(currentDirection);
                for (int s = 0; s < scheduleForDirectionJson.length(); s++) {
                    String timeInStringFormat = scheduleForDirectionJson.getString(s);
                    LocalTime time = LocalTime.parse(timeInStringFormat);
                    schedule.get(currentDirection).add(time);
                }
            }
            return schedule;
        } catch (Exception e) {
            logger.debug("Exception occurred inside extractStationScheduleData method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }
}