package SubwaySchedule;

import java.util.Scanner;

public class RequestDetails {
    public String informationRequested;
    public String stationRequested;
    public String timeProvided;
    public String direction;
    public RequestDetails(){
    }
    public void getInputForRequestDetails(){
        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter (S) for getting Schedule, enter (A) for Next arrival Time, or enter (E) to exit the program.");
        Boolean validInformationRequested = false;

        while (validInformationRequested == false){
            String informationRequested = in.nextLine();
            System.out.println("InforationRequested is: " + informationRequested);
            if (informationRequested.equals("S")){
                this.informationRequested = "schedule";
                validInformationRequested = true;
            } else if (informationRequested.equals("A")) {
                this.informationRequested = "nextArrival";
                validInformationRequested = true;
            } else if (informationRequested.equals("E")){
                validInformationRequested = true;
                System.exit(0);
            } else {
                System.out.println("Invalid option specified.");
                System.out.println("Please enter 1 for getting Schedule, enter 2 for Next arrival Time, or enter 3 to exit the program.");
            }
        }
        System.out.println("Please enter station name:");
        this.stationRequested = in.nextLine();
        if(this.informationRequested.equals("nextArrival")){
            System.out.println("Please enter reference time (to check next Arrival time) in format HH:MM (e.g 12:45) Or enter Default to use current time");
            String timeInputted = in.nextLine();
            if (!timeInputted.equals("Default")){
                this.timeProvided = timeInputted;
            } else {
                this.timeProvided = null;
            }
            System.out.println("Please enter direction. E.g North, South, East, or West");
            this.direction = in.nextLine();
        }
    }
}
