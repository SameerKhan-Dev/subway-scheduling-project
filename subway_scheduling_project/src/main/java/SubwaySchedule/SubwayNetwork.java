package SubwaySchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalTime;


public class SubwayNetwork {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);
    public String region;
    public ArrayList<String> subwayLines = new ArrayList<String>(); // Create an ArrayList object
    public HashMap<String, Station> stations = new HashMap<String, Station>();


    public SubwayNetwork(String region, ArrayList<String> subwayLines){
        this.region= region;
        this.subwayLines = subwayLines;
    }
    // add stations to the current subway network
    public void addStation(Station station){
        try{
            this.stations.put(station.name, station);
        }catch(Exception e){
            logger.debug("Exception occurred inside addStation method: " + e);
            logger.error("Error occurred in program.");
        }
    }
    // return a map of the station schedule based on station specified by the user.
    public HashMap<String, ArrayList<LocalTime>> getStationSchedule(String stationName){
        try{
            if (this.stations.containsKey(stationName)){
                Station station = this.stations.get(stationName);
                return station.getSchedule();
            } else {
                System.out.println("Error invalid Station does not exist");
            }
            return null;
        }catch(Exception e){
            logger.debug("Exception occurred inside getStationSchedule method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }

    // return the next arrival time for a given station, based on direction and time reference
    public LocalTime getNextArrivalTime(String stationName, String time, String direction){
        try{
            LocalTime localTime = null;

            if (time == null){
                localTime = LocalTime.now();
                localTime = localTime.truncatedTo(ChronoUnit.SECONDS);
            } else {
                if(time.matches("^\\d\\d:\\d\\d$") || time.matches("^\\d\\d:\\d\\d:\\d\\d$")){
                    localTime = LocalTime.parse(time);
                    System.out.println("localTime inputted is: " + localTime);
                } else {
                    System.out.println("Error invalid time inputted. Input must be in HH:SS format example: 12:45");
                    return null;
                }
            }

            if (this.stations.containsKey(stationName)){
                Station station = this.stations.get(stationName);
                return station.getNextArrivalTime(localTime, direction);
            } else {
                System.out.println("Error invalid Station inputted, does not exist");
            }
            return null;
        }catch(Exception e){
            logger.debug("Exception occurred inside getNextArrivalTime method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }
}

