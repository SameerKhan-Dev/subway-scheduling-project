package SubwaySchedule;
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

public class SubwaySchedule {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    public static void main(String[] args) throws IOException {

        try{
            // read in JSON subway network data from JSON file get schedule and station data
            String data = new String(Files.readAllBytes(Paths.get("./subwayData.json")));
            JSONObject jsonObject = new JSONObject(data);

            String region = jsonObject.getJSONObject("data").getString("region");

            ArrayList<String> subwayLines = HelperClassSubway.extractSubwayLines(jsonObject);
            SubwayNetwork subwayNetwork = new SubwayNetwork(region, subwayLines);

            // populate subway network
            HelperClassSubway.addStationsToSubwayNetwork(jsonObject, subwayNetwork);

            /* Iniitate Command-line request details questionnaire to ask user what information they are
             looking for such as schedule or next arrival time, and for what station etc.
            */
            RequestDetails requestDetails = new RequestDetails();

            requestDetails.getInputForRequestDetails();

            if (requestDetails.informationRequested.equals("schedule")){
                getSchedule(subwayNetwork, requestDetails);
            } else if (requestDetails.informationRequested.equals("nextArrival")){
                getNextArrivalTime(subwayNetwork, requestDetails);
            }
            Boolean continueProgram = true;

            // Establish a continuous questionnaire and ask use if they would like to continue getting schedules or next arrival times
            while (continueProgram){
                System.out.println("Do you want to continue getting schedules or next arrival times? Enter Y for yes, or N for no (to exit program)");
                Scanner in = new Scanner(System.in);
                String userInput = in.nextLine();
                if (userInput.equals("N")) {
                    continueProgram = false;
                } else if(userInput.equals("Y")){
                    requestDetails.getInputForRequestDetails();

                    if (requestDetails.informationRequested.equals("schedule")){
                        getSchedule(subwayNetwork, requestDetails);
                    } else if (requestDetails.informationRequested.equals("nextArrival")){
                        getNextArrivalTime(subwayNetwork, requestDetails);
                    }
                } else {
                    System.out.println("Invalid option entered.");
                }
            }
        }catch (Exception e) {
            logger.debug("Exception occurred inside main method: " + e);
            logger.error("Error occurred in program.");
        }
    }
    // obtains schedule from the subway network and prints out the result
    public static void getSchedule(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        try{
            HashMap<String, ArrayList<LocalTime>> stationSchedule = subwayNetwork.getStationSchedule(requestDetails.stationRequested);

            if (stationSchedule != null){
                for (Map.Entry<String, ArrayList<LocalTime>> entry : stationSchedule.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<LocalTime> value = entry.getValue();
                    System.out.println("For station: " + requestDetails.stationRequested + " for direction " + key + "the Schedule Data is: " + value);
                }
            }
        } catch (Exception e){
            logger.debug("Exception occurred inside getSchedule method: " + e);
            logger.error("Error occurred in program.");
        }
    }

    // obtains next arrival time from the subway network and prints out the result
    public static void getNextArrivalTime(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        try {
            LocalTime nextArrivalTime = subwayNetwork.getNextArrivalTime(requestDetails.stationRequested, requestDetails.timeProvided, requestDetails.direction);
            if (nextArrivalTime != null){
                System.out.println("For station: " + requestDetails.stationRequested + " the next arrival time is: " + nextArrivalTime + " for direction: " + requestDetails.direction);
            } else {
                System.out.println("Invalid Input provided.");
            }
        } catch (Exception e){
            logger.debug("Exception occurred inside getNextArrivalTime method: " + e);
            logger.error("Error occurred in program.");
        }
    }
}

