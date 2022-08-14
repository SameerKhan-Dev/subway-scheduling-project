package SubwaySchedule;
import java.lang.reflect.Array;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.ArrayList; // import the ArrayList class
import java.time.LocalTime; // import the LocalTime class
import java.time.*;

public class SubwayNetwork {

    // Setting variables as private to not give access directly.
    // Strings can be accessible throughout this class
    public String region;
    public String totalNumStations;
    public String interchangeStationsList;
    public ArrayList<String> subwayLines = new ArrayList<String>(); // Create an ArrayList object
    HashMap<String, Station> stationsList = new HashMap<String, Station>();


    // Constructor to receive first name, last name. Constructor name has to be same as class Name.
    public SubwayNetwork(String region, ArrayList<String> subwayLines){
        this.region= region;
        this.subwayLines = subwayLines;
    }
    // add stations to the stationsList
    public void addStation(Station station){

        //this.stationsList[station.name] = station;
        this.stationsList.put(station.name, station);

    }

    public HashMap<String, ArrayList<LocalTime>> getStationSchedule(String stationName){
        if (this.stationsList.containsKey(stationName)){
            Station station = this.stationsList.get(stationName);
            return station.getSchedule();
        } else {
            // return an error message
            //String errorMessage = "Error: Station does not exist";
            //return errorMessage;
            System.out.println("Error invalid Station does not exist");
        }
        return null;
    }

    public LocalTime getNextArrivalTime(String stationName, String time, String direction){
        LocalTime localTime = null;

        if (time == null){
            localTime = LocalTime.now();
            localTime = localTime.truncatedTo(ChronoUnit.SECONDS);
        } else {
            localTime = LocalTime.parse(time);
        }

        if (this.stationsList.containsKey(stationName)){
            Station station = this.stationsList.get(stationName);
            return station.getNextArrivalTime(localTime, direction);
        } else {
            System.out.println("Error invalid Station does not exist");
        }
        return null;
    }
}

