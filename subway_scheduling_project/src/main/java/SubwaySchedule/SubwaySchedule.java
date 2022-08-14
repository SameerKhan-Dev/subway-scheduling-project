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
import java.util.logging.Level;
//import java.util.logging.Logger;
import java.util.logging.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
//import java.util.logging.Logger;

public class SubwaySchedule {
    //private static final Logger LOGGER = Logger.getLogger(SubwaySchedule.class.getName());



    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    public static void main(String[] args) throws IOException {
        logger.info("Example log from {}");
        logger.error("Error Message!");
        //System.out.println("LOGGER.getHandlers()" + logger.  );
        /*Handler consoleHandler = null;
        consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
        */
        // - LOGGER.setLevel(Level.ALL);


        // - LOGGER.config("Configuration done.");
        // - LOGGER.log(Level.OFF,"Hello from Logger");
        // - LOGGER.setUseParentHandlers(false);

        System.out.println("Hello World!");
        try{
            // read in string
            String data = new String(Files.readAllBytes(Paths.get("./subwayData.json")));
            JSONObject jsonObject = new JSONObject(data);

            String region = jsonObject.getJSONObject("data").getString("region");

            ArrayList<String> subwayLines = HelperClassSubway.extractSubwayLines(jsonObject);
            SubwayNetwork subwayNetwork = new SubwayNetwork(region, subwayLines);

            HelperClassSubway.addStationsToSubwayNetwork(jsonObject, subwayNetwork);

            RequestDetails requestDetails = new RequestDetails();
            requestDetails.getInputForRequestDetails();

            if (requestDetails.informationRequested.equals("schedule")){
                getSchedule(subwayNetwork, requestDetails);
            } else if (requestDetails.informationRequested.equals("nextArrival")){
                getNextArrivalTime(subwayNetwork, requestDetails);
            }
            Boolean continueProgram = true;

            while (continueProgram){
                System.out.println("Do you want to continue getting schedules or next arrival times? Enter Y for yes, or N for no (to exit program");
                Scanner in = new Scanner(System.in);
                String userInput = in.nextLine();
                if (userInput.equals("N")) {
                    continueProgram = false;
                } else {
                    requestDetails.getInputForRequestDetails();

                    if (requestDetails.informationRequested.equals("schedule")){
                        getSchedule(subwayNetwork, requestDetails);
                    } else if (requestDetails.informationRequested.equals("nextArrival")){
                        getNextArrivalTime(subwayNetwork, requestDetails);
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Error in processing program. Exiting Program");
        }
    }
    public static void getSchedule(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        HashMap<String, ArrayList<LocalTime>> stationSchedule = subwayNetwork.getStationSchedule(requestDetails.stationRequested);

        if (stationSchedule != null){
            for (Map.Entry<String, ArrayList<LocalTime>> entry : stationSchedule.entrySet()) {
                String key = entry.getKey();
                ArrayList<LocalTime> value = entry.getValue();
                System.out.println("For station: " + key + " for direction " + key + "the Schedule Data is: " + value);
            }
        }
    }
    public static void getNextArrivalTime(SubwayNetwork subwayNetwork, RequestDetails requestDetails){
        LocalTime nextArrivalTime = subwayNetwork.getNextArrivalTime(requestDetails.stationRequested, requestDetails.timeProvided, requestDetails.direction);
        if (nextArrivalTime != null){
            System.out.println("For station: " + requestDetails.stationRequested + " the next arrival time is: " + nextArrivalTime + " for direction: " + requestDetails.direction);
        } else {
            System.out.println("Invalid Input provided.");
        }
    }
}

