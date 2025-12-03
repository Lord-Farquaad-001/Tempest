/*
* write results to a new file
* average cloud cover

This is the driver program for the WeatherAnalysis class. It will prompt the user to enter the locations whose weather
entries are to be analyzed. It will then give the user the option to analyze the whole range of data for each location,
or to specify the beginning and end of the range of dates they are interested in.

If the user enters dates that are out of range, they will be rounded to the next-closest dates.
The beginning date will be rounded forward in chronological time and the end date will be rounded
backward in order to avoid including values out of range.
 */
//----------------------------------------------------------------------------------------------------------------------

/*
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

class AnalysisDriver{ //start of class AnalysisDriver
    public static void main(String [] args) throws Exception{ //start of main

        //--------------------------------------------------------------------------------------------------------------
        //Section 1: location qty

        Scanner keyboard = new Scanner(System.in);
        Scanner strings = new Scanner(System.in);

        //needs input validation
        boolean valid = false; //run condition for while loops
        int locationCount = 0; //variable to store number of locations to be processed

        //input-validation loop for location quantity
        while(!valid) {
            try {
                System.out.print("Number of locations: ");
                locationCount = keyboard.nextInt();

                if(locationCount >= 1){
                    valid = true;
                } else{
                    System.out.println("Minimum locations: 1");
                }

            //catches exceptions arising from non-integer input
            } catch (InputMismatchException e){
                System.out.println("Integer value required!");
                keyboard.next();
            }

        }
        //--------------------------------------------------------------------------------------------------------------
        //Section 2: location names

        //initialization of the array that stores the names of the user's locations
        String [] locationArray = new String [locationCount];

        //loop to assign String values to the above array ^
        for (int i = 0; i < locationCount; i++) {
            valid = false;
            while(!valid) {
                if(locationCount == 1){
                    System.out.print("Location: ");
                } else {
                    System.out.print("Location " + (i + 1) + ": ");
                }
                //StringBuilder to convert location name to file name for reading
                StringBuilder storage = new StringBuilder();
                String locationName = strings.nextLine();
                storage.append("/Applications/Documents/TempestFiles/" + locationName + "Weather.txt"); //adds Weather.txt to location name
                locationArray[i] = storage.toString(); //converts SB to string
                File weatherFile = new File(locationArray[i]); //new file objects created for all locations
                if (weatherFile.exists()) {
                    valid = true; //loop terminates if a valid location is entered
                } else{
                    //otherwise, user is prompted to enter a location that has a file associated with it
                    System.out.print("No file found for location \"" + locationName + "\"\n");
                }
            } //end of while loop
        } //end of for loop

        System.out.println("-----------------------------"); //output divider

        //new WeatherAnalysis array created to analyze the weather data for each location
        WeatherAnalysis [] locations = new WeatherAnalysis[locationCount];
        for(int i = 0; i < locationCount; i++){
            locations[i] = new WeatherAnalysis(locationArray[i]); //each WA object assigned arguments from locationArray
        }

        //--------------------------------------------------------------------------------------------------------------
        //Section 3: range of analysis

        String result = "";
        //stringBuilder to store the analysis strings for each location
        StringBuilder storage = new StringBuilder();
        Scanner integers = new Scanner(System.in);
        Scanner dates = new Scanner(System.in);

        //--------------------------------------------------------------------------------------------------------------
        //loop to ask user for range
        boolean terminate = false; //run condition for following while loops
        while(!terminate){
            try {
                System.out.print("1. Analyze whole range\n2. Analyze specific range\nChoice: ");
                int userChoice = integers.nextInt();

                //----------------------------------------------------------------------------------------------------------
                //block analyzes whole range for each location

                //standard analysis option for whole range
                if (userChoice == 1) {
                    System.out.println("-----------------------------");
                    valid = false;
                    while (!valid) {
                        try {
                            System.out.print("1. Standard analysis\n2. Statistical analysis\n3. Custom analysis\nChoice: ");
                            int analysisChoice = integers.nextInt();


                            //standard analysis option for whole range
                            if (analysisChoice == 1) {
                                System.out.print("-----------------------------");
                                for (int i = 0; i < locationCount; i++) {
                                    storage.append(locations[i].analyzeWeather() + "\n-----------------------------");
                                }
                                valid = true;

                                //statistical analysis option for whole range
                            } else if (analysisChoice == 2) {
                                System.out.print("-----------------------------");
                                for (int i = 0; i < locationCount; i++) {
                                    storage.append(locations[i].statAnalysis() + "\n-----------------------------");
                                }
                                valid = true;

                                //custom analysis option for whole range
                            } else if (analysisChoice == 3) {
                                System.out.print("-----------------------------");
                                System.out.print("\nOptions:" +
                                        "\n1. Average high" +
                                        "\n2. Average low" +
                                        "\n3. Average variance" +
                                        "\n4. Average humidity" +
                                        "\n5. Quantity of humid days" +
                                        "\n6. Total rainfall" +
                                        "\n7. Quantity of rainy days" +
                                        "\n8. Average cloud cover" +
                                        "\n9. Percentage of cloudy days" +
                                        "\n10. Days above (temp)" +
                                        "\n11. Days below (temp)" +
                                        "\n12. Days with cloud cover above x %\n" +
                                        "-----------------------------\n");

                                //stores numbers as strings
                                StringBuilder numbers = new StringBuilder();
                                boolean run = true;
                                int count = 1;
                                int upperLimit = 12;
                                int lowerLimit = 0;
                                System.out.println("To end loop, enter 0 when prompted for choice"); //instructions
                                while (run && count <= upperLimit) {
                                    //input validation for choices
                                    try {
                                        System.out.print("Choice " + count + ": ");
                                        int num = integers.nextInt();
                                        if (num > lowerLimit && num <= 9) {
                                            numbers.append(num + " "); //adds the user's choice to the numbers SB object
                                            count++; //increments counter
                                        } else if (num == 10) {
                                            System.out.print("Upper limit temp: ");
                                            int limit = integers.nextInt();
                                            numbers.append(num);
                                            numbers.append(limit + " ");
                                            count++;
                                        } else if (num == 11) {
                                            System.out.print("Lower limit temp: ");
                                            int limit = integers.nextInt();
                                            numbers.append(num);
                                            numbers.append(limit + " ");
                                            count++;

                                        } else if (num == 12){
                                            System.out.print("Cloud cover % threshold: ");
                                            int limit = integers.nextInt();
                                            numbers.append(num);
                                            numbers.append(limit + " ");
                                            count++;

                                        } else if (num == lowerLimit) { //end condition of loop
                                            run = false;
                                        } else if (count == upperLimit){
                                            run = false;
                                        } else { //catches values out of range
                                            System.out.println("Enter a number 1 to 12");
                                        }
                                    } catch (InputMismatchException e) { //catches errors arising from non-integer input
                                        System.out.println("Enter an integer value 1 to 12");
                                        integers.next();
                                    }
                                }
                                System.out.println("-----------------------------"); //executes at the end of the above ^ loop

                                String numString = numbers.toString();
                                numString = numString.trim();
                                //array to store cells of user's input
                                String [] conversion = WeatherAnalysis.splitLine(numString);

                                //appends the custom analysis to the storage StringBuilder object
                                for (int i = 0; i < locations.length; i++) {
                                    storage.append(locations[i].customAnalysis(conversion) + "\n-----------------------------\n");
                                }

                                //terminates inner loop
                                valid = true;


                            } else {
                                System.out.println("Enter \"1\", \"2\", or \"3\"");
                            }

                        //catches exceptions arising from non-integer input
                        } catch(InputMismatchException e){
                            System.out.println("Integer values only!");
                            integers.next(); //clears buffer of "bad" value
                        }

                    }
                    terminate = true;

                    //----------------------------------------------------------------------------------------------------------
                    //block analyzes user-specified range for each location (range is the same for all locations)
                } else if (userChoice == 2) {
                    System.out.println("-----------------------------");

                    String start = "";
                    String end = "";

                    valid = false; //allows following loop to run at least once
                    //asks for start date
                    while (!valid) {
                        try {
                            System.out.print("Start date: ");
                            start = dates.nextLine();
                            if (WeatherAnalysis.correctDateFormat(start)) {//checks for proper date format
                                for (int i = 0; i < locationCount; i++) {
                                    //checks to see if user's start date is "less than" the last date in each location's file
                                    if (WeatherAnalysis.dateToInt(start) <= WeatherAnalysis.dateToInt(locations[i].getEndLine())) {
                                        valid = true; //if so, loop terminates and user's start date is accepted
                                        //otherwise, the error message is displayed and the loop repeats itself
                                    } else {
                                        System.out.println("Start date out of range! Last date in " + locations[i].getFileName() + ": " + locations[i].getEndLine());
                                    }
                                }
                                //deals with issues arising from improper date format
                            } else {
                                System.out.println("Enter a date with the form YYYY-MM-DD");
                            }
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }

                    valid = false; //allows following loop to run at least once
                    //asks for end date
                    while (!valid) {
                        try {
                            System.out.print("End date: ");
                            end = dates.nextLine();
                            if (WeatherAnalysis.correctDateFormat(end)) { //checks for proper date format
                                for (int i = 0; i < locationCount; i++) {
                                    //checks to see if user's end date is "greater than or equal to" the first date in each location's file
                                    if (WeatherAnalysis.dateToInt(end) >= WeatherAnalysis.dateToInt(locations[i].getStartLine())) {
                                        valid = true; //if so, loop terminates and user's start date is accepted
                                        //otherwise, the error message below is displayed and the loop repeats itself
                                    } else {
                                        System.out.println("End date out of range! First date in " + locations[i].getFileName() + ": " + locations[i].getStartLine());
                                    }
                                } //end of for loop
                                //deals with issues arising from improper date formatting
                            } else {
                                System.out.println("Enter a date with the form YYYY-MM-DD");
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }

                    //-------------------------------------------------------------------------------------------------------------
                    System.out.println("-----------------------------");
                    valid = false;
                    while (!valid) {
                        try {
                            System.out.print("1. Standard analysis\n2. Statistical analysis\n3. Custom analysis\nChoice: ");
                            int analysisChoice = integers.nextInt();

                            //standard analysis of restricted range option
                            if (analysisChoice == 1) {
                                storage.append("-----------------------------");
                                for (int i = 0; i < locationCount; i++) {
                                    //appends the analysis of each location to the storage StringBuilder
                                    storage.append(locations[i].analyzeRange(start, end) + "\n-----------------------------");
                                }

                                valid = true; //inner loop terminates
                                terminate = true; //outer loop terminates if all requirements are satisfied

                                //statistical analysis of restricted range option
                            } else if (analysisChoice == 2) {
                                storage.append("-----------------------------");
                                for (int i = 0; i < locationCount; i++) {
                                    //appends the analysis of each location to the storage StringBuilder
                                    storage.append(locations[i].statRangeAnalysis(start, end) + "\n-----------------------------");
                                }

                                valid = true; //inner loop terminates
                                terminate = true; //outer loop terminates if all requirements are satisfied

                                //input validation

                            } else if (analysisChoice == 3) {
                                System.out.print("-----------------------------");
                                System.out.print("\nOptions:" +
                                        "\n1. Average high" +
                                        "\n2. Average low" +
                                        "\n3. Average variance" +
                                        "\n4. Average humidity" +
                                        "\n5. Quantity of humid days" +
                                        "\n6. Total rainfall" +
                                        "\n7. Quantity of rainy days" +
                                        "\n8. Average cloud cover" +
                                        "\n9. Percentage of cloudy days" +
                                        "\n10. Days above x temperature" +
                                        "\n11. Days below x temperature" +
                                        "\n12. Cloud cover above x %\n" +
                                        "-----------------------------\n");

                                //stores numbers as strings
                                StringBuilder numbers = new StringBuilder();
                                boolean run = true;
                                int count = 1;
                                int upperLimit = 12;
                                int lowerLimit = 0;
                                System.out.println("To end loop, enter 0 when prompted for choice"); //instructions
                                while (run && count <= upperLimit) {
                                    //input validation for choices
                                    try {
                                        System.out.print("Choice " + count + ": ");
                                        int num = integers.nextInt();
                                        if (num > lowerLimit && num <= 9) {
                                            numbers.append(num + " "); //adds the user's choice to the numbers SB object
                                            count++; //increments counter

                                        } else if (num == 10) {
                                                System.out.print("Upper limit temp: ");
                                                int limit = integers.nextInt();
                                                numbers.append(num);
                                                numbers.append(limit + " ");
                                                count++;

                                        } else if (num == 11) {
                                            System.out.print("Lower limit temp: ");
                                            int limit = integers.nextInt();
                                            numbers.append(num);
                                            numbers.append(limit + " ");
                                            count++;

                                        } else if (num == 12){
                                            System.out.print("Cloud cover % threshold: ");
                                            int limit = integers.nextInt();
                                            numbers.append(num);
                                            numbers.append(limit + " ");
                                            count++;

                                        } else if (num == lowerLimit) { //end condition of loop
                                            run = false;
                                        } else if (count == upperLimit){
                                            run = false;
                                        } else { //catches values out of range
                                            System.out.println("Enter a number 1 to 11");
                                        }
                                    } catch (InputMismatchException e) { //catches errors arising from non-integer input
                                        System.out.println("Enter an integer value 1 to 11");
                                        integers.next();
                                    }
                                }
                                System.out.println("-----------------------------"); //executes at the end of the above ^ loop

                                String numString = numbers.toString();
                                numString = numString.trim();
                                //array to store cells of user's input
                                String [] conversion = WeatherAnalysis.splitLine(numString);

                                //appends the custom analysis to the storage StringBuilder object
                                for (int i = 0; i < locations.length; i++) {
                                    storage.append(locations[i].customRangeAnalysis(start, end, conversion) + "\n-----------------------------\n");
                                }

                                //terminates inner loop
                                valid = true;


                            } else {
                                System.out.println("Enter \"1\", \"2\", or \"3\"");
                            }

                        } catch (InputMismatchException e){
                            System.out.println("Integer input only!");
                            integers.next(); //clears buffer of "bad" value to prevent infinite loop
                        }

                    }
                    terminate = true; //terminates the outer loop

                    //catches error of entering integer value out of range
                } else {
                    System.out.println("Enter \"1\" or \"2\"");//end of inner loop (analysisChoice)
                }
                //catch block for outer loop (catches non-integer input for analysis choice)
            } catch (InputMismatchException e){
                System.out.println("Integer values only!");
                integers.next(); //clears buffer to prevent infinite loop situation
            }


        }//end of outer loop
        //--------------------------------------------------------------------------------------------------------------
        //Section 4: final output

        result = storage.toString(); //converts the storage StringBuilder to a String object
        System.out.print(result); //prints the above ^

        //--------------------------------------------------------------------------------------------------------------


    } //end of main
} //end of class AnalysisDriver

 */

