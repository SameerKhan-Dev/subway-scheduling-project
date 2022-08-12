package SubwaySchedule;
import java.util.HashMap;

public class SubwayNetwork {

    // Setting variables as private to not give access directly.
    // Strings can be accessible throughout this class
    private String region;
    private String totalNumStations;
    private String interchangeStationsList;
    private String availableSubwayLines;
    private String password;
    HashMap<String, Station> stationsList = new HashMap<String, Station>();

    // add stations to the stationsList
    public void addStation(Station station){

        //this.stationsList[station.name] = station;
        this.stationsList.put(station.name, station);

    }

    public void getStationSchedule(String stationName){
        if (this.stationsList.containsKey(stationName)){
            Station station = this.stationsList.get(stationName);
            return station.getSchedules();
        } else {
            // return an error message
            //String errorMessage = "Error: Station does not exist";
            //return errorMessage;
        }
    }

    public void getNextArrivalTime(String stationName, String time, String direction){
        if (this.stationsList.containsKey(stationName)){
            Station station = this.stationsList.get(stationName);
            return station.determineNextArrivalTime(time, direction);
        } else {

        }
    }
}

