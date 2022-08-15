package SubwaySchedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class RequestDetails {
    private static final Logger logger = LoggerFactory.getLogger(SubwaySchedule.class);

    public String informationRequested;
    public String stationRequested;
    public String timeProvided;
    public String direction;
    public RequestDetails(){
    }
    // get user's command line input to build the request details (i.e. schedule or next arrival time?, station? time? direction?)
    public void getInputForRequestDetails(){
        try {
            // Using Scanner for Getting Input from User
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter (S) for getting Schedule, enter (A) for Next arrival Time, or enter (E) to exit the program.");
            Boolean validInformationRequested = false;

            while (validInformationRequested == false){
                String informationRequested = in.nextLine();
                if (informationRequested.equals("S")){
                    this.informationRequested = "schedule";
                    System.out.println("InformationRequested is: schedule ");
                    validInformationRequested = true;
                } else if (informationRequested.equals("A")) {
                    System.out.println("InformationRequested is: Next Arrival Time");
                    this.informationRequested = "nextArrival";
                    validInformationRequested = true;
                } else if (informationRequested.equals("E")){
                    validInformationRequested = true;
                    System.out.println("Selected Exit Option.");
                    System.exit(0);
                } else {
                    System.out.println("Invalid option specified.");
                    System.out.println("Please enter (S) for getting Schedule, enter (A) for Next arrival Time, or enter (E) to exit the program.");
                }
            }
            System.out.println("Please enter station name:");
            this.stationRequested = in.nextLine();
            if(this.informationRequested.equals("nextArrival")){
                System.out.println("Please enter reference time (to check next Arrival time) in format HH:MM (e.g 12:45) Or enter Default to use current time");
                String timeInputted = in.nextLine();
                // validate that time matches required format
                Boolean timeInputFormatCorrect = timeInputted.matches("^\\d\\d:\\d\\d$") || timeInputted.matches("^\\d\\d:\\d\\d:\\d\\d$");

                while (!timeInputFormatCorrect){
                    System.out.println("Error invalid time inputted. Input must be in HH:SS format example: 12:45");
                    System.out.println("Please enter reference time (to check next Arrival time) in format HH:MM (e.g 12:45) Or enter Default to use current time. Or Enter E to exit program.");
                    timeInputted = in.nextLine();
                    timeInputFormatCorrect = timeInputted.matches("^\\d\\d:\\d\\d$") || timeInputted.matches("^\\d\\d:\\d\\d:\\d\\d$");
                    if(timeInputted.equals("E")){
                        System.exit(0);
                    }
                }
                // assign timeProvided value of requestDetails based on user time input
                if (!timeInputted.equals("Default")){
                    this.timeProvided = timeInputted;
                } else {
                    this.timeProvided = null;
                }
                System.out.println("Please enter direction. E.g North, South, East, or West");
                this.direction = in.nextLine();
            }
        } catch(Exception e){
            logger.debug("Exception occurred inside getInputForRequestDetails method: " + e);
            logger.error("Error occurred in program.");
        }
    }
}
