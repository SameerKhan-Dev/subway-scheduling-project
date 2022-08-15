package SubwaySchedule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

class HelperClassSubwayTest {
    public String data = new String(Files.readAllBytes(Paths.get("./subwayDataForTest.json")));
    public JSONObject jsonObject = new JSONObject(data);
    public JSONArray stationsArrJson = jsonObject.getJSONObject("data").getJSONArray("stations");

    HelperClassSubwayTest() throws IOException {
    }

    @Test
    void extractSubwayLines() throws IOException {

        ArrayList<String> subwayLines = HelperClassSubway.extractSubwayLines(jsonObject);
        List<String> x = new ArrayList<>(Arrays.asList("xyz", "abc"));
        ArrayList<String> subwayLinesExpected =  new ArrayList<>(Arrays.asList("Line 1 Yonge-University", "Line 2 Bloor-Danforth", "Line 3 Scarborough (RT)","Line 4 Sheppard"));
        assertEquals(subwayLinesExpected, subwayLines);
    }

    @Test
    void addStationsToSubwayNetwork() throws IOException {
        // read in string

        String region = jsonObject.getJSONObject("data").getString("region");

        ArrayList<String> subwayLines = HelperClassSubway.extractSubwayLines(jsonObject);
        SubwayNetwork subwayNetwork = new SubwayNetwork(region, subwayLines);
        ArrayList<String> stationsExpected =  new ArrayList<>(Arrays.asList("Osgoode", "Union", "Bloor-Yonge"));

        HelperClassSubway.addStationsToSubwayNetwork(jsonObject, subwayNetwork);
        //HashMap<String, Station> expectedStationsList =
        Boolean allKeysPresent = true;
        for (int i = 0; i < stationsExpected.size(); i++){

            if(subwayNetwork.stationsList.containsKey(stationsExpected.get(i)) == false){
                allKeysPresent = false;
                break;
            }
        }
        assertEquals(allKeysPresent, true);

    }

    @Test
    void extractStationSubwayLinesData() {
        JSONArray subwayLinesJson = stationsArrJson.getJSONObject(0).getJSONArray("subwayLines");

        ArrayList<String> expectedSubwayLines =  new ArrayList<>(Arrays.asList("Line 1 Yonge-University"));

        ArrayList<String> subwayLinesForStation = HelperClassSubway.extractStationSubwayLinesData(subwayLinesJson);

        assertEquals(expectedSubwayLines, subwayLinesForStation);

    }

    @Test
    void extractStationDirectionsData() {
        JSONArray directionsJson = stationsArrJson.getJSONObject(0).getJSONArray("directions");
        ArrayList<String> expectedStationDirections =  new ArrayList<>(Arrays.asList("North", "South"));
        ArrayList<String> directions = HelperClassSubway.extractStationDirectionsData(directionsJson);

        assertEquals(expectedStationDirections, directions);

    }

    @Test
    void extractStationScheduleData() {

        JSONObject scheduleJson = stationsArrJson.getJSONObject(0).getJSONObject("schedule");
        JSONArray directionsJson = stationsArrJson.getJSONObject(0).getJSONArray("directions");
        ArrayList<String> directions = HelperClassSubway.extractStationDirectionsData(directionsJson);
        ArrayList<String> expectedStationDirections =  new ArrayList<>(Arrays.asList("North", "South"));
        HashMap<String, ArrayList<LocalTime>> schedule = HelperClassSubway.extractStationScheduleData(scheduleJson, directions);
        Boolean correctScheduleDirectionsAndSize = true;
        int expectedSizeOfSchedule = 14;

        for (int i = 0; i < expectedStationDirections.size(); i++){
            if(schedule.containsKey(expectedStationDirections.get(i))){
                if(schedule.get(expectedStationDirections.get(i)).size() != expectedSizeOfSchedule) {
                    correctScheduleDirectionsAndSize = false;
                    break;
                }
            } else {
                correctScheduleDirectionsAndSize = false;
                break;
            }
        }

        assertEquals(correctScheduleDirectionsAndSize, true);
    }
}