import static java.lang.Math.*;

public class HelperLibrary {
    //call functions using HelperLibrary.nameOfFunction( );
    //used to remove characters from end of strings, before converting to numbers, e.g. "ft", "M", "m", "mtrs"
    static String removeLastChars(String str, int numberToRemove)
    {
        //if (str.length()<numberToRemove)
          //  return str; //leave as is
        return str.substring(0,str.length()-numberToRemove);
    }

    static double getDegrees(String input, int degreesLength) //degreesLength lat:2, long:3
    {
        //Long,Lat are in DDMM.mmm format
        String output = input.substring(0,degreesLength); //get first 2 chars - Degrees
        return Double.valueOf(output);
    }

    //UKWaypoints and UKWaypoints100 csv files contain lat/long positional data in "degs,mins" format.
    //This function converts to the more commonly used decimal degrees format, for use with haversine distance measure
    static double convertlocationStrToDecimalDegrees(String input)
    {
        char lastLetter = input.charAt(input.length()-1);
        String DegreesMinutesStr = removeLastChars(input,1); //remove last Letter
        double degrees=0.0, mins=0.0;
        if (lastLetter=='W' || lastLetter=='E') //longitude in form DDDMM.mmm
        {
            double DDDMMmmm = Double.valueOf(DegreesMinutesStr);
            degrees = getDegrees(input,3); //get first 2 chars as a double
            mins = DDDMMmmm - (degrees*100.0);

        } else  //latitude in form : DDMM.mmm
        {
            double DDMMmmm = Double.valueOf(DegreesMinutesStr);
            degrees = getDegrees(input,2); //get first 2 chars as a double
            mins = DDMMmmm - (degrees*100.0);
        }
        degrees = degrees + mins/60.0;
        if (lastLetter=='W' || lastLetter=='S')
            degrees = degrees * -1.0; //negate it
        return degrees;
    }

    /* distances between points described by longitude and latitude are calculated using the haversine function and the angle between the two points.
    To use haversine the long and lat values in UKWaypoints files should first be converted to decimal degrees. If using pin positions from
    google maps etc - longitude,latitude values will already be in decimal degrees*/
    static double haversineDistanceKm(String startLat, String startLong, String endLat, String endLong)
    {
       // if (startLat==null || endLat==null ||startLong==null || endLong==null)
        //    return -9999.9999;  //indicates a null position in inputs
        double lat1 = convertlocationStrToDecimalDegrees(startLat);
        double lat2 = convertlocationStrToDecimalDegrees(endLat);
        double long1 = convertlocationStrToDecimalDegrees(startLong);
        double long2 = convertlocationStrToDecimalDegrees(endLong);
        //https://powerappsguide.com/blog/post/formulas-calculate-the-distance-between-2-points-longitude-latitude
        double diffLat = lat2-lat1;
        double diffLong = long2-long1;
        final double rad = 3.1416/180.0; //calculation uses radians rather degrees
        final double earthRadius = 6371.0;
        double angle = 0.5-cos(diffLat * rad)/2 + cos(lat1*rad)*cos(lat2*rad) * (1.0-cos(diffLong*rad))/2.0;
        return 2.0 * earthRadius * asin(sqrt(angle));
    }
    //convert heights to meters
    static int convertElevationToMeters(String elevationStr)
    {
        char[] unitChars = { 'f', 't','M','m' };
        if (elevationStr.toLowerCase().endsWith("m"))
        {
            // return (int)Double.Parse(elevationStr.TrimEnd(unitChars));
            double heightMtrs = Double.valueOf(elevationStr.substring(0,elevationStr.length()-1));
            return (int) heightMtrs;
        }
        else if (elevationStr.toLowerCase().endsWith("ft")) {
            double elevationFeet = Double.valueOf(elevationStr.substring(0, elevationStr.length() - 2));
            return (int) (elevationFeet * 0.3048); //constant for ft to m
        }
        //if no units stated - assume mtrs
        return Integer.valueOf(elevationStr);
    }

    static String buildDescription(String [] featuresInLine)
    {
        StringBuilder description = new StringBuilder();
        int arrayPosition = 10; //position of start of Description
        String descriptionPart;
        while (arrayPosition < featuresInLine.length)
        {
            descriptionPart = featuresInLine[arrayPosition];
            if (isText(descriptionPart))
            {
                description.append(descriptionPart + ",");
            }
            arrayPosition++;
        }
        String result = description.toString();                ;
        return removeLastChars(result,1); //remove trailing ',
    }

    static Boolean isOrdSurveyPosition(String xStr,String yStr)
    {
        String regexPattern = "^\\d{8}$"; //regular expression for exactly 8 digits
        return xStr.matches(regexPattern)  &&  yStr.matches(regexPattern);
    }

    static Boolean isDegMinsPosition(String xStr,String yStr)
    {
        String regexPattern = "^\\d+\\.\\d+[a-zA-Z]$"; //regular expression for a floating point number followed by a letter
        return xStr.matches(regexPattern)  &&  yStr.matches(regexPattern);
    }

    static Boolean isText(String str)
    {
        if (str == "" || str == " ")
            return false;
        return true;
    }

}
