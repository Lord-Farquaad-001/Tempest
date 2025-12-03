/*
23 46 19 29
* prevent repeat entries (if today's date is present in location file, do not allow a second entry)
* daysAbove(temp) daysBelow

------------------------------------------------------------------------------------------------------------------------
Nathan Dann
23 May 2025

61 81 60 2

This program is the driver for the Weather class. The purpose of this program is to keep a log of each day's weather in
a given span of time. The entries include the following items:

    * High: expects a double value for the day's high temperature
    * Low: expects a double value for the day's low temperature
    * precipitation: expects Y / N and converts to boolean. Did it rain/snow/hail/sleet for more than a few moments?
    * humid: expects Y /N and converts to boolean. Was it humid for the majority of the day?
    * cloudy: expects Y / N and converts to boolean. Was it cloudy/overcast for the majority of the day?
    * sunny: expects Y / N and converts to boolean. Was it sunny for the majority of the day?
------------------------------------------------------------------------------------------------------------------------
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

class WeatherDriver { //start of class WeatherDriver
    public static void main(String[] args) throws Exception{ //start of main

        try { //attempts the following

            String locationName = ""; //initialization of location name | this will be used to name the file

            boolean terminate = false; //run condition for following while loop

            //----------------------------------------------------------------------------------------------------------
            //Section 1: main loop

            Scanner numbers = new Scanner(System.in); //scanner to read number values
            Scanner strings = new Scanner(System.in); //scanner to read string values
            Scanner booleans = new Scanner(System.in);

            boolean valid = false;

            while (!terminate) { //input validation loop

                System.out.print("Location: ");
                String location = strings.nextLine(); //variable used to write file name
                StringBuilder nameBuilder = new StringBuilder();
                nameBuilder.append(location.toLowerCase() + "Weather.txt"); //adds Weather.txt to above location name ^
                locationName = nameBuilder.toString(); //converts above ^ to String

                //file that is written to | named according to String location
                File outFile = new File(locationName);

                //------------------------------------------------------------------------------------------------------
                //Section 1a: calls to Weather set methods

                //establishes day as an object of the weather class
                Weather day = new Weather();


                //sets high value
                int high = askHigh(numbers);
                day.setHigh(high);

                //sets low value
                int low = askLow(numbers);
                day.setLow(low);

                //sets precipitation value
                double rainResponse = askPrecip(numbers);
                day.setPrecipitation(rainResponse);

                //sets humid value
                int humidResponse = askHumid(numbers);
                day.setHumid(humidResponse);

                //sets cloudy value
                int cloudyResponse = askCloudy(numbers);
                day.setCloudCover(cloudyResponse);


                //------------------------------------------------------------------------------------------------------
                //Section 1b: format and output

                //converts weather object contents to string format
                String message = day.toString();

                if(!outFile.exists()){
                    outFile.createNewFile();
                }

                //output stream enables appending, opens file in append mode
                FileOutputStream outStream = new FileOutputStream(outFile, true);
                PrintWriter outWriter = new PrintWriter(outStream); //writes text to external files

                //prints location name if file is new / empty
                Scanner readFile = new Scanner(outFile);
                boolean flag = true; //true = file is blank
                while (readFile.hasNextLine()){
                    String line = readFile.nextLine();
                    if(!line.isBlank());
                    flag = false; //false = file has contents
                }

                //prints the location name if the entry is the first in the file
                if(flag){
                    outWriter.print(toTitle(location) + "\n\n"); //prints location name
                }

                outWriter.print(message); //prints the weather entry

                outWriter.close(); //closes outWriter to execute writing / prevent data corruption

                //message for first file entry
                if(flag){
                    System.out.println(outFile + " was successfully created and written to." +
                            "\n———————————————————————————————————————————————————————————");
                //message for all other file entries
                } else {
                    System.out.println(outFile + " was successfully written to." +
                            "\n———————————————————————————————————————————————————————————");
                }

                //------------------------------------------------------------------------------------------------------
                //Section 1c: option for loop repetition

                //option to make another entry
                boolean invalid = true; //run condition for input-validation loop
                System.out.print("Make another entry? ");

                //loop to validate Y / N input
                while (invalid) {
                    String answer = booleans.nextLine().toLowerCase();
                    if (answer.equals("y") || answer.equals("yes")) {
                        invalid = false;
                    } else if (answer.equals("n") || answer.equals("no")) {
                        terminate = true;
                        invalid = false;
                    } else {
                        //error message
                        System.out.print("Enter \"Y\" or \"N\": ");
                    }
                }
            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
        } //end of catch

        System.out.println("———————————————————————————————————————————————————————————\n—Process complete—");

    } //end of main


    //------------------------------------------------------------------------------------------------------------------

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to ask for high
    public static int askHigh(Scanner scan){
        int highTemp = 0;
        boolean valid = false;

        while(!valid) {
            try {
                System.out.print("High: ");
                highTemp = scan.nextInt();
                valid = true;
            } catch (InputMismatchException e){
                System.out.println("Numeric values only!");
                scan.next(); //clears buffer of "bad" value
            }
        }
        return highTemp;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to ask for low
    public static int askLow(Scanner scan){
        int lowTemp = 0;
        boolean valid = false;

        while(!valid) {
            try {
                System.out.print("Low: ");
                lowTemp = scan.nextInt();
                valid = true;
            } catch (InputMismatchException e){
                System.out.println("Numeric values only!");
                scan.next(); //clears buffer of "bad" value
            }
        }
        return lowTemp;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to ask for precipitation
    public static double askPrecip(Scanner scan) {
        double precipResponse = 0;
        boolean valid = false;

        while(!valid) {
            try {
                System.out.print("Precipitation (inches): ");
                precipResponse = scan.nextDouble();
                if (precipResponse >= 0) {
                    valid = true;
                } else {
                    System.out.println("Negative values prohibited!");
                }
            } catch(InputMismatchException e){
                System.out.println("Numeric values only!");
                scan.next(); //clears buffer of "bad" value
            }
        }
        return precipResponse;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to ask for humid and validate input
    public static int askHumid(Scanner scan){
        boolean valid = false;
        int humidResponse = 0;
        while(!valid) {
            try {
                System.out.print("Humidity (%): ");
                humidResponse = scan.nextInt();
                if (humidResponse > 0 && humidResponse <= 100) {
                    valid = true;
                } else {
                    System.out.println("Enter a percentage between 1 and 100");
                }
            } catch (InputMismatchException e){
                System.out.println("Numeric values only!");
                scan.next(); //clears buffer of "bad" value
            }
        }
        return humidResponse;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to ask for cloudy
    public static int askCloudy(Scanner scan) {
        int cloudyResponse = 0;
        boolean terminate = false;

        while (!terminate) {
            try {
                System.out.print("Average cloud cover (%): ");
                cloudyResponse = scan.nextInt();
                terminate = true;
            } catch (InputMismatchException e) {
                System.out.print("Integer input only!");
                scan.next(); //clears buffer of "bad" value
            }
        }
        return cloudyResponse;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to capitalize the first letter of a string
    public static String toTitle(String word){
        String returnString = "";
        char [] charArray = new char[word.length()];
        for(int i = 0; i < charArray.length; i++){
            charArray[i] = word.charAt(i);
        }
        charArray[0] = Character.toUpperCase(charArray[0]);
        StringBuilder storage = new StringBuilder();

        for(int i = 0; i < charArray.length; i++){
            storage.append(charArray[i]);
        }
        returnString = storage.toString();
        return returnString;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————

} //end of class WeatherDriver




