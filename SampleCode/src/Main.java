import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{

       public static void main(String[] args) {
        String filePath= "data/UK_waypoints100.csv"; //file within main project directory containing folders : src,out etc
        int waypointsRead = readFile(filePath);
        System.out.println("Read :"+ waypointsRead + " waypoints from file.");
       }


    //Read a CSV file, assuming 1 record, per row of the file. Each record is Name,Code,Longitude,Latitude,height
    public static int readFile(String filePath) {
        int count=0;
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Skip header line (if your file has one)
            if (scanner.hasNextLine()) {
                String header = scanner.nextLine();
                System.out.println("File found.");
                System.out.println("Headers: " + header);
            }
            String name;  //Variable for each Value in a row of the file
            String code;
            String xPosition;
            String yPosition;
            double height;
            while (scanner.hasNextLine()) {  //Loop until end of file
                String line = scanner.nextLine(); //read next row of file
                String[] parts = line.split(","); //split line into separate values using commas
                name = parts[0];
                code = parts[1];
                yPosition = parts[2]; //latitude
                xPosition = parts[3]; //longitude
                height = (double) HelperLibrary.convertElevationToMeters(parts[4]); //height in ft converted to mtrs
                System.out.println("Read Waypoint:"+ name +",Code:"+code+",Long:"+xPosition+",Lat:"+yPosition+",height:"+height);

                count = count+1;
            }
            scanner.close();
            return count;
        } //end of try
        catch (FileNotFoundException e) { //do this if try block does not work
            System.out.println("Error: " + filePath + " was not found!!");
            return 0;
        }
    }
}
