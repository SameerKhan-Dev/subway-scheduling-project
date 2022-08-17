package SubwaySchedule;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SubwayNetworkTest {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    @Test
    void addStation() {
        // Setting up requirements for building a new Subway Network

        ArrayList<String> subwayLinesForStation = new ArrayList<>(Arrays.asList("Line 1 Yonge-University"));
        ArrayList<String> directions = new ArrayList<>(Arrays.asList("North", "South"));
        HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();
        LocalTime localTimeIdx0 = LocalTime.parse("01:23");
        LocalTime localTimeIdx1 = LocalTime.parse("02:22");
        ArrayList <LocalTime> arrayOne = new ArrayList<>(Arrays.asList(localTimeIdx1, localTimeIdx1));
        ArrayList <LocalTime> arrayTwo = new ArrayList<>(Arrays.asList(localTimeIdx1, localTimeIdx1));
        schedule.put("North", arrayOne);
        schedule.put("South", arrayTwo);

        Station station = new Station("Osgoode", schedule, subwayLinesForStation, directions, false, false, false);
        ArrayList<String> subwayLinesForNetwork = new ArrayList<>(Arrays.asList("Line 1 Yonge-University"));
        SubwayNetwork subwayNetwork = new SubwayNetwork("Toronto",subwayLinesForNetwork);

        subwayNetwork.addStation(station);

        Boolean stationAdded = subwayNetwork.stations.containsKey("Osgoode");
        Boolean stationMappedCorrectly = subwayNetwork.stations.get("Osgoode") instanceof Station;

        assertEquals(true, stationAdded && stationMappedCorrectly);
    }

    @Test
    void getStationSchedule() {
        // Setting up requirements for building a new Subway Network
        SubwayNetwork subwayNetwork = buildSubwayNetworkWithStation();

        String stationName = "Osgoode";
        HashMap<String, ArrayList<LocalTime>> stationSchedule = subwayNetwork.getStationSchedule(stationName);
        ArrayList<String> expectedDirections = new ArrayList<>(Arrays.asList("North", "South"));

        int expectedSizeOfSchedule = 2;

        assertEquals(true, stationSchedule.containsKey("North"));
        assertEquals(true, stationSchedule.containsKey("South"));
        assertEquals(expectedSizeOfSchedule, stationSchedule.get(expectedDirections.get(0)).size());
        assertEquals(expectedSizeOfSchedule, stationSchedule.get(expectedDirections.get(1)).size());
    }


    @Test
    void getNextArrivalTime() {

        SubwayNetwork subwayNetwork = buildSubwayNetworkWithStation();

        LocalTime nextArrivalTimeOne = subwayNetwork.getNextArrivalTime("Osgoode", "02:00", "South");
        LocalTime expectedArrivalTimeOne = LocalTime.parse("02:22");
        LocalTime nextArrivalTimeWithInvalidStation = subwayNetwork.getNextArrivalTime("random-abc", "02:00", "South");
        LocalTime expectedNextArrivalTimeInvalidStation = null;
        LocalTime nextArrivalTimeWithInvalidTime = subwayNetwork.getNextArrivalTime("Osgoode", "02:00:80abc", "South");
        LocalTime expectedNextArrivalTimeInvalidTime = null;
        LocalTime nextArrivalTimeWithInvalidDirection = subwayNetwork.getNextArrivalTime("Osgoode", "02:00", "Eastabc");
        LocalTime expectedNextArrivalTimeInvalidDirection = null;

        assertEquals(expectedArrivalTimeOne, nextArrivalTimeOne);
        assertEquals(expectedNextArrivalTimeInvalidStation, nextArrivalTimeWithInvalidStation);
        assertEquals(expectedNextArrivalTimeInvalidTime, nextArrivalTimeWithInvalidTime);
        assertEquals(expectedNextArrivalTimeInvalidDirection, nextArrivalTimeWithInvalidDirection);


    }
    public static SubwayNetwork buildSubwayNetworkWithStation(){
        try{
            ArrayList<String> subwayLinesForStation = new ArrayList<>(Arrays.asList("Line 1 Yonge-University"));
            ArrayList<String> directions = new ArrayList<>(Arrays.asList("North", "South"));
            HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();
            LocalTime localTimeIdx0 = LocalTime.parse("01:23");
            LocalTime localTimeIdx1 = LocalTime.parse("02:22");
            ArrayList <LocalTime> arrayOne = new ArrayList<>(Arrays.asList(localTimeIdx1, localTimeIdx1));
            ArrayList <LocalTime> arrayTwo = new ArrayList<>(Arrays.asList(localTimeIdx1, localTimeIdx1));
            schedule.put("North", arrayOne);
            schedule.put("South", arrayTwo);

            Station station = new Station("Osgoode", schedule, subwayLinesForStation, directions, false, false, false);
            ArrayList<String> subwayLinesForNetwork = new ArrayList<>(Arrays.asList("Line 1 Yonge-University"));
            SubwayNetwork subwayNetwork = new SubwayNetwork("Toronto",subwayLinesForNetwork);

            subwayNetwork.addStation(station);

            return subwayNetwork;
        }catch (Exception e){
            logger.error("Error occurred in program.");
            return null;
        }

    }
}

