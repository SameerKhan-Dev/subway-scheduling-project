package SubwaySchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalTime;


public class Station {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    // Setting variables as private to not give access directly.
    // Strings can be accessible throughout this class
    public String name;
    public ArrayList<String> subwayLines;
    public ArrayList<String> directions = new ArrayList<String>(); // Create an ArrayList object
    public HashMap<String, ArrayList<LocalTime>> schedule = new HashMap<String, ArrayList<LocalTime>>();
    public Boolean goTransit;
    public Boolean viaRail;
    public Boolean isInterchange;

    // Constructor to receive first name, last name. Constructor name has to be same as class Name.
    public Station(String name, HashMap<String, ArrayList<LocalTime>> schedule,   ArrayList<String> subwayLines, ArrayList<String> directions, Boolean goTransit, Boolean viaRail, Boolean isInterchange){
        this.name = name;
        this.subwayLines = subwayLines;
        this.directions = directions;
        this.schedule = schedule;
        this.goTransit = goTransit;
        this.viaRail = viaRail;
        this.isInterchange = isInterchange;
    }
    // return the next arrival time for the current station based on direction and time reference
    public LocalTime getNextArrivalTime(LocalTime time, String direction){
        try{
            Boolean validDirection = false;
            LocalTime nextArrivalTime = null;

            if (this.schedule.containsKey(direction)) {
                ArrayList<LocalTime> scheduleForDirection = this.schedule.get(direction);
                if(this.schedule.get(direction).isEmpty()){
                    System.out.println("Error: No schedule found for Station: " + this.name + " for direction: " + direction);
                } else {
                    nextArrivalTime = scheduleForDirection.get(0);

                    // determine next arrival time (assumes schedule will be in sorted order.
                    for (int i = 0; i < scheduleForDirection.size() ; i++){
                        LocalTime currentArrivalTime = scheduleForDirection.get(i);
                        int value = currentArrivalTime.compareTo(time);
                        if (value >= 0){
                            nextArrivalTime = currentArrivalTime;
                            break;
                        }
                    }
                }

            } else {
                System.out.println("Error: Invalid direction inputted for this Station. Valid directions for station: " + this.name + " are: " + this.directions);
            }
            return nextArrivalTime;
        }catch(Exception e){
            logger.debug("Exception occurred inside getNextArrivalTime method here: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }
    // return the schedule for current station
    public HashMap<String, ArrayList<LocalTime>> getSchedule(){
        try{
            return this.schedule;
        }catch(Exception e){
            logger.debug("Exception occurred inside getSchedule method: " + e);
            logger.error("Error occurred in program.");
            return null;
        }
    }
}






