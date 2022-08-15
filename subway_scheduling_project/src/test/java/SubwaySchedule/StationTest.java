package SubwaySchedule;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    @Test
    void getNextArrivalTime() {
        Station station = buildStation();

        LocalTime nextArrivalTimeOne = station.getNextArrivalTime(LocalTime.parse("02:00"), "South");
        LocalTime expectedArrivalTimeOne = LocalTime.parse("02:22");
        LocalTime nextArrivalTimeWithInvalidDirection = station.getNextArrivalTime(LocalTime.parse("02:00"), "Eastabc");
        LocalTime expectedNextArrivalTimeInvalidDirection = null;

        assertEquals(expectedArrivalTimeOne, nextArrivalTimeOne);
        assertEquals(null, nextArrivalTimeWithInvalidDirection);

    }

    @Test
    void getSchedule() {
        Station station = buildStation();

        HashMap<String, ArrayList<LocalTime>>  schedule = station.getSchedule();

        ArrayList<String> expectedDirections = new ArrayList<>(Arrays.asList("North", "South"));

        int expectedSizeOfSchedule = 2;
        // ask do i need to do a loop here or can i just have separate assertEquals
        assertEquals(true, schedule.containsKey("North"));
        assertEquals(true, schedule.containsKey("North"));
        assertEquals(expectedSizeOfSchedule, schedule.get(expectedDirections.get(0)).size());
        assertEquals(expectedSizeOfSchedule, schedule.get(expectedDirections.get(1)).size());
    }

    public static Station buildStation(){
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

            return station;
        }catch (Exception e){
            logger.error("Error occurred in program.");
            return null;
        }

    }
}