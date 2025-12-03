
/*
* close scanners in methods
* instance method to search for days with humidity above x %
* count excessively hot and excessively cold days (daysAbove / below methods)

* organize methods by type
* use dateToInt arrays for restricted domain analyses
* why does getHighs(start, end) throw Exceptions?

------------------------------------------------------------------------------------------------------------------------
Nathan Dann
30 May 2025
The WeatherAnalysis class is used to evaluate the contents of a .txt file storing a location's weather data.
It will output the following for the range of dates within the file:
    * Average high temperature
    * Average low temperature
    * Average humidity
    * Number of days with rain
    * Number of cloudy days
    * Number of sunny days

The WeatherAnalysis object is directly tied to a weather file's contents and seeks to calculate the analysis data
for the file it is tied to. When a new WeatherAnalysis object is created, it must have a String object that corresponds
to a file path passed into it as a parameter. It is easiest for the .txt file to be located in the general project
folder rather than the source folder to avoid needing to use the absolute file path. The class then links to this
file and analyzes it. The analyzeWeather and analyzeRange are the two most general (and probably useful) methods in this
class. The WeatherAnalysis class is not used to get individual data points (such as a particular day's high) for any
location.
------------------------------------------------------------------------------------------------------------------------
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

class WeatherAnalysis { //start of class WeatherAnalysis
    //------------------------------------------------------------------------------------------------------------------
    //establishment of WeatherAnalysis variables

    private final String fileName; //file name
    private String location; //location name

    private int dayCount; //number of day entries in file
    private int [] dateIntegers; //array of all dates in file converted to integers

    private int [] highs; //array of all high values in file
    private int [] lows; //array of all low values in file
    private int [] varianceVals; //array of all variance values in file
    private double [] precipitationVals; //array of all precipitation values in file
    private int [] humidVals; //array of all humidity values in file
    private int [] cloudVals; //array of all cloud cover values in file
    //------------------------------------------------------------------------------------------------------------------
    //WeatherAnalysis constructor

    public WeatherAnalysis(String sourceFileName) throws Exception{
        //calls to setter methods

        fileName = sourceFileName;

        this.setLocation();
        this.setDayCount();
        this.setDateIntegers();

        this.setHighs();
        this.setLows();
        this.setVarianceVals();
        this.setPrecipVals();
        this.setHumidVals();
        this.setCloudValues();


    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //WeatherAnalysis get instance methods

    //------------------------------------------------------------------------------------------------------------------
    //returns the file name that was passed in as a parameter
    public String getFileName(){
        return fileName;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getDayCount(){
        return dayCount;
    }

    //------------------------------------------------------------------------------------------------------------------
    //returns the location name of the file passed into the WeatherAnalysis object as a parameter
    public String getLocation(){
        return location;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to return array of all dates in file in integer form (e.g. 2025-07-01 --> 20250701)
    public int [] getDateIntegers(){
        return dateIntegers;
    }
    //------------------------------------------------------------------------------------------------------------------

    public int getStartInt(){
        return dateIntegers[0]; //first value in array = first date in file
    }
    //------------------------------------------------------------------------------------------------------------------

    public int getEndInt(){
        return dateIntegers[dateIntegers.length-1]; //last value in array = last date for file
    }
    //------------------------------------------------------------------------------------------------------------------
    //returns array of high values for location
    public int [] getHighs(){
        return highs;
    }

    //------------------------------------------------------------------------------------------------------------------
    //returns array of high values in specified date range for location
    public int [] getHighs(String start, String end) throws Exception{

        int userFirst = dateToInt(start);
        int userEnd = dateToInt(end);

        //gets all date ints for file
        int [] fileDates = getDateIntegers();

        //all highs for location (same length as fileDates)
        int [] allHighs = this.getHighs();

        int [] restrictedHighs = new int[sizeArray(start, end)];

        int index = 0;
        for(int i = 0; i < fileDates.length; i++){
            if(fileDates[i]>= userFirst && fileDates[i] < userEnd && index < restrictedHighs.length){
                restrictedHighs[index] = allHighs[i];
                index++;
            }
        }
        return restrictedHighs;
    }

    //------------------------------------------------------------------------------------------------------------------
    //returns array of low values for location
    public int [] getLows(){
        return lows;
    }

    //------------------------------------------------------------------------------------------------------------------
    //returns array of low values in specified date range for location
    public int [] getLows(String start, String end) throws Exception{

        int userFirst = dateToInt(start);
        int userEnd = dateToInt(end);

        //gets all date ints for file
        int [] fileDates = getDateIntegers();

        //all lows for location (same length as fileDates)
        int [] allLows = this.getLows();

        int [] restrictedLows = new int[sizeArray(start, end)];

        int index = 0;
        for(int i = 0; i < fileDates.length; i++){
            if(fileDates[i]>= userFirst && fileDates[i] < userEnd && index < restrictedLows.length){
                restrictedLows[index] = allLows[i];
                index++;
            }
        }
        return restrictedLows;
    }

    //------------------------------------------------------------------------------------------------------------------

    public int [] getVarianceVals(){
        return varianceVals;
    }
    //------------------------------------------------------------------------------------------------------------------
    //returns array of variance values in a specified date range for location
    public int [] getVarianceVals(String start, String end) throws Exception{

        int [] highs = this.getHighs(start, end);
        int [] lows = this.getLows(start, end);

        int [] variances = new int[highs.length];

        for(int i = 0; i < variances.length; i++){
            variances[i] = highs[i] - lows[i];
        }
        return variances;
    }

    //------------------------------------------------------------------------------------------------------------------
    public double [] getRainVals(){
        return precipitationVals;
    }
    //------------------------------------------------------------------------------------------------------------------
    //returns array of precipitation values in a specified date range for location
    public double [] getRainVals(String start, String end) throws Exception{
        int userFirst = dateToInt(start);
        int userEnd = dateToInt(end);

        //gets all date integers for file
        int [] fileDates = getDateIntegers();

        //all precipitation values for location (same length as fileDates)
        double [] allRain = this.getRainVals();

        double [] restrictedRain = new double[sizeArray(start, end)];

        int index = 0;
        for(int i = 0; i < fileDates.length; i++){
            if(fileDates[i]>= userFirst && fileDates[i] < userEnd && index < restrictedRain.length){
                restrictedRain[index] = allRain[i];
                index++;
            }
        }
        return restrictedRain;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int [] getHumidVals(){
        return humidVals;
    }
    //------------------------------------------------------------------------------------------------------------------
    //returns array of humidity values in a specified date range for location
    public int [] getHumidVals(String start, String end) throws Exception{

        int userFirst = dateToInt(start);
        int userEnd = dateToInt(end);

        //gets all date integers for file
        int [] fileDates = getDateIntegers();

        //all humid values for location (same length as fileDates)
        int [] allHumidVals = this.getHumidVals();

        int [] restrictedHumidVals = new int[sizeArray(start, end)];

        int index = 0;
        for(int i = 0; i < fileDates.length; i++){
            if(fileDates[i]>= userFirst && fileDates[i] < userEnd && index < restrictedHumidVals.length){
                restrictedHumidVals[index] = allHumidVals[i];
                index++;
            }
        }
        return restrictedHumidVals;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int [] getCloudVals(){
        return cloudVals;
    }

    //------------------------------------------------------------------------------------------------------------------
    //returns array of cloud cover values in a specified date range for location
    public int [] getCloudVals(String start, String end) throws Exception{

        int userFirst = dateToInt(start);
        int userEnd = dateToInt(end);

        //gets all date integers for file
        int [] fileDates = getDateIntegers();

        //all cloud cover values for location (same length as fileDates)
        int [] allCloudVals = this.getCloudVals();

        int [] restrictedCloudVals = new int[sizeArray(start, end)];

        int index = 0;
        for(int i = 0; i < fileDates.length; i++){
            if(fileDates[i]>= userFirst && fileDates[i] < userEnd && index < restrictedCloudVals.length){
                restrictedCloudVals[index] = allCloudVals[i];
                index++;
            }
        }
        return restrictedCloudVals;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to find the first date in file
    public String getStartDate(){

        return intToDate(dateIntegers[0]);
    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public String getStartDate(String start) throws Exception{
        return intToDate(roundStartToClosest(start));
    }
    //------------------------------------------------------------------------------------------------------------------
    //method to find the last date in file
    public String getEndDate(){

        return intToDate(dateIntegers[dateIntegers.length-1]);
    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public String getEndDate(String end) throws Exception{
        return intToDate(roundEndToClosest(end));
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //WeatherAnalysis set instance methods

    //------------------------------------------------------------------------------------------------------------------
    //method to set the location of WeatherAnalysis objects
    public void setLocation() throws Exception{
        File weatherFile = new File(fileName);
        Scanner readFile = new Scanner(weatherFile);

        int count = 0;
        while(count < 1){ //ends after first line is read
            String line = readFile.nextLine();
            if(count == 0){
                location = line; //the first line in the file is the location name
            }
            count++;
        }
    } //end of method setLocation
    //------------------------------------------------------------------------------------------------------------------
    //method to count number of entries in file
    public void setDayCount() throws Exception{

        File f = new File(fileName);
        Scanner readFile = new Scanner(f);
        int count = 0; //counts number of days
        while(readFile.hasNextLine()){
            String line = eliminateNonAlpha(readFile.nextLine());
            if(stringContains("High", line)){
                count++;
            }
        }
        dayCount = count;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets date integers for whole file
    public void setDateIntegers() throws Exception{

        int [] dateArray = new int[this.getDayCount()];

        File dataFile = new File(fileName);
        Scanner readFile = new Scanner(dataFile);

        int index = 0;
        while (readFile.hasNextLine()){
            String line = readFile.nextLine();
            if (correctDateFormat(line)) {
                dateArray[index] = dateToInt(line);
                index++;
            }
        }
        dateIntegers = dateArray;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of highs
    public void setHighs() throws Exception{

        int [] highArray = new int[this.getDayCount()];

        try {
            File weather = new File(fileName);
            Scanner readFile = new Scanner(weather);

            int index = 0;

            while (readFile.hasNextLine()) {
                String line = eliminateNonAlpha(readFile.nextLine());
                if (stringContains("High", line)) {
                    int temp = extractInt(line);
                    highArray[index] = temp;
                    index++;
                }

            }

        } catch (Exception e){
            System.out.println("setHighs");
        }
        highs = highArray;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of lows
    public void setLows() throws Exception{

        int [] lowArray = new int[this.getDayCount()];

        try {
            File weather = new File(fileName);
            Scanner readFile = new Scanner(weather);

            int index = 0;

            while (readFile.hasNextLine()) {
                String line = eliminateNonAlpha(readFile.nextLine());
                if (stringContains("Low", line)) {
                    lowArray[index] = extractInt(line);
                    index++;
                }

            }

        } catch (Exception e){
            System.out.println("setLows");
        }
        lows = lowArray;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of variances
    public void setVarianceVals() throws Exception{

        int [] variances = new int[this.getDayCount()]; //array of variance values
        int [] highs = this.getHighs(); //array of high values
        int [] lows = this.getLows(); //array of low values

        for(int i = 0; i < variances.length; i++){
            variances[i] = highs[i] - lows[i];
        }
        varianceVals = variances;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of precipitationVals
    public void setPrecipVals() throws Exception{

        double [] rainArray = new double[this.getDayCount()];

        try {
            File dataFile = new File(fileName);
            Scanner readFile = new Scanner(dataFile);

            int index = 0;
            while (readFile.hasNextLine()) {
                String line = eliminateNonAlpha(readFile.nextLine());

                if (stringContains("Precipitation", line)) {
                    rainArray[index] = extractDouble(line);
                    index++;
                }
            }
        } catch (Exception e){
            System.out.println("setPrecipVals");
        }
        precipitationVals = rainArray;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of humidVals
    public void setHumidVals() throws Exception{

        int [] humidArray = new int[this.getDayCount()];

        try {
            File weather = new File(fileName);
            Scanner readFile = new Scanner(weather);

            int index = 0;

            while (readFile.hasNextLine()) {
                String line = eliminateNonAlpha(readFile.nextLine());
                if (stringContains("Humidity", line)) {
                    int val = extractInt(line);
                    humidArray[index] = val;
                    index++;
                }

            }

        } catch (Exception e){
            System.out.println("setHumidVals");
        }
        humidVals = humidArray;
    }
    //------------------------------------------------------------------------------------------------------------------
    //sets values of cloudVals
    public void setCloudValues() throws Exception{
        int [] cloudArray = new int[this.getDayCount()];

        try {
            File weather = new File(fileName);
            Scanner readFile = new Scanner(weather);

            int index = 0;

            while (readFile.hasNextLine()) {
                String line = eliminateNonAlpha(readFile.nextLine());
                if (stringContains("Cloud", line)) {
                    int val = extractInt(line);
                    cloudArray[index] = val;
                    index++;
                }

            }

        } catch (Exception e){
            System.out.print("setCloudValues");
        }
        cloudVals = cloudArray;
    }
    //------------------------------------------------------------------------------------------------------------------


    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //find methods for average values

    //------------------------------------------------------------------------------------------------------------------
    //calculates average high
    public int findAvgHigh(){

        int [] highs = this.getHighs();

        int sum = 0;

        for(int i = 0; i < highs.length; i++){
            sum += highs[i];
        }

        return sum / highs.length;

    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findAvgHigh(String start, String end) throws Exception{

        int [] highs = this.getHighs(start, end);

        int sum = 0;
        for(int i = 0; i < highs.length; i++){
            sum += highs[i];
        }
        return sum / highs.length; //mean value of restricted dataset
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates average low
    public int findAvgLow() {

        int [] lows = this.getLows();

        int sum = 0;

        for(int i = 0; i < lows.length; i++){
            sum += lows[i];
        }

        return sum / lows.length;


    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findAvgLow(String start, String end) throws Exception{

        int [] lows = this.getLows(start, end);

        int sum = 0;
        for(int i = 0; i < lows.length; i++){
            sum += lows[i];
        }
        return sum / lows.length; //mean value of restricted dataset
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates average variance
    public int findAvgVariance(){

        return (findAvgHigh() - findAvgLow());

    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findAvgVariance(String start, String end) throws Exception{

        int [] variances = this.getVarianceVals(start, end);

        int sum = 0;
        for(int i = 0; i < variances.length; i++){
            sum += variances[i];
        }
        return sum / variances.length; //mean value of restricted dataset
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates total quantity of rain
    public double findRainQty() {

        double [] rainVals = getRainVals();

        double sum = 0;

        for(int i = 0; i < rainVals.length; i++){
            sum += rainVals[i];
        }
        int placeholder = (int)Math.round(sum * 100);
        return (double) placeholder / 100;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public double findRainQty(String start, String end) throws Exception{

        double [] rain = this.getRainVals(start, end);

        double sum = 0;
        for(int i = 0; i < rain.length; i++){
            sum += rain[i];
        }
        int placeholder = (int)Math.round(sum * 100);
        return (double) placeholder / 100;
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates average humidity
    public int findAvgHumidity(){

        int [] humidVals = getHumidVals();

        int sum = 0;

        for(int i = 0; i < humidVals.length; i++){
            sum += humidVals[i];
        }
        return sum / humidVals.length;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findAvgHumidity(String start, String end) throws Exception{

        int [] humidVals = this.getHumidVals(start, end);

        int sum = 0;
        for(int i = 0; i < humidVals.length; i++){
            sum += humidVals[i];
        }
        return sum / humidVals.length; //mean value of restricted dataset
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates average cloud cover
    public int findAvgCloudCover(){

        int [] cloudVals = getCloudVals();

        int sum = 0;

        for(int i = 0; i < cloudVals.length; i++){
            sum += cloudVals[i];
        }
        return sum / cloudVals.length;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findAvgCloudCover(String start, String end) throws Exception{

        int [] cloudVals = this.getCloudVals(start, end);

        int sum = 0;
        for(int i = 0; i < cloudVals.length; i++){
            sum += cloudVals[i];
        }
        return sum / cloudVals.length; //mean value of restricted dataset
    }
    //------------------------------------------------------------------------------------------------------------------
    //method to calculate the percentage of days with rain
    public int findRainPercentage() throws Exception{

        double [] precipitation = this.getRainVals();

        int sum = 0;
        for(int i = 0; i < precipitation.length; i++){
            if(precipitation[i] > 0){
                sum++;
            }
        }
        return sum * 100 / precipitation.length;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findRainPercentage(String start, String end) throws Exception{

        double [] precipitation = this.getRainVals(start, end);

        int sum = 0;
        for(int i = 0; i < precipitation.length; i++){
            if (precipitation[i] > 0) {
                sum++;
            }
        }
        return sum * 100 / precipitation.length;
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates the percentage of days with humidity >= 60%
    public int findHumidPercentage() throws Exception {

        int [] humidVals = this.getHumidVals();

        int humidCount = 0;

        for(int i = 0; i < humidVals.length; i++){
            if(humidVals[i] >= 60){
                humidCount++;
            }
        }
        return (humidCount * 100 / humidVals.length);
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findHumidPercentage(String start, String end) throws Exception{

        int [] humidVals = this.getHumidVals(start, end);

        int humidCount = 0;

        for(int i = 0; i < humidVals.length; i++){
            if(humidVals[i] >= 60){
                humidCount++;
            }
        }
        return (humidCount * 100 / humidVals.length);
    }
    //------------------------------------------------------------------------------------------------------------------
    //calculates the percentage of cloudy days
    public int findCloudPercentage() throws Exception{

        int [] cloudVals = this.getCloudVals();

        int cloudCount = 0;

        //if cloud value is greater than 62, it is counted as cloudy
        for(int i = 0; i < cloudVals.length; i++){
            if(cloudVals[i] >= 62){
                cloudCount++;
            }
        }


        return (cloudCount * 100 / cloudVals.length);
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int findCloudPercentage(String start, String end) throws Exception{

        int [] cloudVals = this.getCloudVals(start, end);

        int cloudCount = 0;
        //if cloud value is greater than 62, it is counted as cloudy
        for(int i = 0; i < cloudVals.length; i++){
            if(cloudVals[i] >= 62){
                cloudCount++;
            }
        }
        return (cloudCount * 100 / cloudVals.length);
    }
    //------------------------------------------------------------------------------------------------------------------
    //method to find number of days with a high temperature exceeding a specific value
    public int daysAbove(int userTemp){

        int [] highs = this.getHighs();

        int sum = 0;
        for(int i = 0; i < highs.length; i++){
            if(highs[i] > userTemp){
                sum++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public int daysAbove(String start, String end, int userTemp) throws Exception{

        int [] highs = this.getHighs(start, end);

        int sum = 0;
        for(int i = 0; i < highs.length; i++){
            if(highs[i] > userTemp){
                sum++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------
    //counts number of days below a user-specified value
    public int daysBelow(int userTemp){

        int [] lows = this.getLows();

        int sum = 0;
        for(int i = 0; i < lows.length; i++){
            if(lows[i] < userTemp){
                sum++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int daysBelow(String start, String end, int userTemp) throws Exception{

        int [] lows = this.getLows(start, end);

        int sum = 0;
        for(int i = 0; i < lows.length; i++){
            if(lows[i] < userTemp){
                sum++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------
    //counts number of days with cloud cover exceeding a user-defined value
    public int cloudCoverAbove(int userNum) throws Exception{

        int [] cover = this.getCloudVals();

        int sum = 0;
        for(int i = 0; i < cover.length; i++){
            if(cover[i] > userNum){
                sum ++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int cloudCoverAbove(String start, String end, int userNum) throws Exception{

        int [] cover = this.getCloudVals(start, end);

        int sum = 0;
        for(int i = 0; i < cover.length; i++){
            if(cover[i] > userNum){
                sum ++;
            }
        }
        return sum;
    }
    //------------------------------------------------------------------------------------------------------------------


    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //Utility instance methods

    //------------------------------------------------------------------------------------------------------------------
    public int [] getData(int num) throws Exception{

        int [] returnArray = new int[this.getDayCount()];

        switch (num){
            case 0:
                returnArray = this.getHighs();
                break;

            case 1:
                returnArray = this.getLows();
                break;

            case 2:
                returnArray = this.getVarianceVals();
                break;

            case 3:
                returnArray = this.getHumidVals();
                break;

            case 4:
                returnArray = this.getCloudVals();
                break;
        }
        return returnArray;

    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specified range
    public int [] getData(String start, String end, int num) throws Exception{


        int [] returnArray = new int[sizeArray(start, end)];

        switch (num){
            case 0:
                returnArray = this.getHighs(start, end);
                break;

            case 1:
                returnArray = this.getLows(start, end);
                break;

            case 2:
                returnArray = this.getVarianceVals(start, end);
                break;

            case 3:
                returnArray = this.getHumidVals(start, end);
                break;

            case 4:
                returnArray = this.getCloudVals(start, end);
                break;
        }
        return returnArray;
    }

    //------------------------------------------------------------------------------------------------------------------
    //rounds a user-entered start date to the closest entry date in the file if exact date is not present (rounds up)
    public int roundStartToClosest(String startDate) throws Exception {

        int startInt = dateToInt(startDate);

        //filters start dates that are chronologically after the last date in file
        if(startInt > getEndInt()){
            throw new IllegalArgumentException("Start date out of range! Last date in file: " + intToDate(getEndInt()));
        } else {

            int[] fileDates = getDateIntegers();

            int placeholder = fileDates[0]; //arbitrary placeholder value

            int returnInt = 0;
            for (int i = 0; i < fileDates.length; i++) {
                //fileDates[i] - startInt >= rounds up (no dates out of range)
                if (fileDates[i] - startInt >= 0 && fileDates[i] - startInt < placeholder) {
                    placeholder = fileDates[i] - startInt;
                    returnInt = fileDates[i];

                    //executes if start date given is after end date
                } else if (i == fileDates.length - 1 && fileDates[i] - startInt < 0) {
                    returnInt = fileDates[i];
                }
            }

            return returnInt;
        }

    } //end of method

    //------------------------------------------------------------------------------------------------------------------
    //rounds a user-entered end date to the closest entry date in the file if exact date is not present (rounds down)
    public int roundEndToClosest(String endDate) throws Exception{

        int endInt = dateToInt(endDate);

        //if end date is before start of file range, exception is thrown
        if(endInt < getStartInt()){
            throw new IllegalArgumentException("End date out of range! First date in file: " + intToDate(getStartInt()));
        } else {

            int[] fileDates = getDateIntegers();

            int placeholder = fileDates[0] * -1; //arbitrary placeholder value (very negative)

            int returnInt = 0;
            for (int i = 0; i < fileDates.length; i++) {
                //fileDates - endInt <= 0 rounds down (no dates out of range)
                if (fileDates[i] - endInt <= 0 && fileDates[i] - endInt > placeholder) { //finds number closest to 0
                    placeholder = fileDates[i] - endInt;
                    returnInt = fileDates[i];

                }
            }
            return returnInt;
        }

    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //Utility static methods

    //------------------------------------------------------------------------------------------------------------------
    public int sizeArray(String start, String end) throws Exception{

        start = intToDate(roundStartToClosest(start));
        end = intToDate(roundEndToClosest(end));

        //2025 10 01
        //2025 09 01

        int [] dates = this.getDateIntegers();

        int startIndex = 0;
        int endIndex = 0;
        for(int i = 0; i < dates.length; i++){
            if(dates[i] == dateToInt(start)){
                startIndex = i;
            } else if(dates[i] == dateToInt(end)){
                endIndex = i;
            }
        }
        return endIndex - startIndex;
    }
    //------------------------------------------------------------------------------------------------------------------
    //method to split a String into separate words
    public static String[] splitLine(String line) { //start of method splitLine
        line = line.trim(); //eliminates padded space characters
        line = line + " "; //adds one space character to the end of the String argument passed in

        //splits string up into individual characters
        char[] lineChars = new char[line.length()]; //array that holds each character in the original string
        for (int i = 0; i < line.length(); i++) {
            lineChars[i] = line.charAt(i);
        }

        //loop to count spaces in string
        int spaceCount = 0;
        for (int i = 0; i < lineChars.length; i++) {
            if (lineChars[i] == ' ') {
                spaceCount++;
            }
        }

        //finds indexes of spaces, end to beginning
        int[] spaceIndex = new int[spaceCount];
        for (int i = 0; i < spaceIndex.length; i++) {
            for (int j = 0; j < lineChars.length; j++) {
                if (lineChars[j] == ' ') {
                    spaceIndex[i] = j; //sets each i (array index) to the j (original space index) value

                }
            }
            lineChars[spaceIndex[i]] = '-'; //turns spaces to '-' characters to prevent infinite loop / double counting
        }

        //puts indexes in proper order (reverses from above ^ )
        int[] properOrder = new int[spaceIndex.length];
        for (int i = 0; i < spaceIndex.length; i++) {
            properOrder[i] = spaceIndex[spaceIndex.length - (i + 1)];
        }

        //loop to put individual words in string into a new String array
        String[] splitWords = new String[properOrder.length]; //array to hold the separated words
        int startingIndex = 0;
        for (int i = 0; i < properOrder.length; i++) {
            int endIndex = properOrder[i];
            splitWords[i] = (line.substring(startingIndex, endIndex)).trim(); //goes from starting index to ending
            startingIndex = endIndex; //sets the beginning index of the next word to the ending of the last
        }
        return splitWords; //returns the split word array
    } //end of splitLine method

    //------------------------------------------------------------------------------------------------------------------
    //method to determine whether a character is alphabetical (includes numbers, periods, and spaces)
    public static boolean isAlpha(char item) {
        boolean flag = false; //return value

        char[] alphabetArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}; //array of alphabet, space character, and numeric digits

        //lower-case alphabet array assignment
        char[] lowerAlphabetArray = new char[alphabetArray.length];
        for (int i = 0; i < lowerAlphabetArray.length; i++) {
            lowerAlphabetArray[i] = Character.toLowerCase(alphabetArray[i]);
        }

        //if a character is a letter, flag is raised
        for (int i = 0; i < alphabetArray.length; i++) {
            if (item == alphabetArray[i] || item == lowerAlphabetArray[i]) {
                flag = true;
            }
        }
        return flag; //returns flag | true = alphabetical, false = non-alphabetical
    } //end of isAlpha

    //------------------------------------------------------------------------------------------------------------------
    //method that eliminates non-alphabetical (or numeric, space, period) characters from a string
    public static String eliminateNonAlpha(String message) {
        char[] messageChars = new char[message.length()];
        int counter = 0;
        //loop to put each character in a string into a char array for individual analysis
        for (int i = 0; i < message.length(); i++) {
            messageChars[i] = message.charAt(i);
            if (!isAlpha(messageChars[i])) {
                counter++;
            }
        }

        StringBuilder trimmedMessage = new StringBuilder();

        for (int i = 0; i < messageChars.length; i++) {
            if (isAlpha(messageChars[i])) {
                trimmedMessage.append(messageChars[i]);
            }
        }

        String finalMessage = trimmedMessage.toString();
        return finalMessage;

    } //end of method eliminateNonAlpha

    //------------------------------------------------------------------------------------------------------------------
    //method to determine if a string contains a particular sequence of characters
    public static boolean stringContains(String fragment, String full) {
        boolean flag = false;
        String[] words = splitLine(full);

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(fragment)) {
                return true;
            }
        }
        return flag;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method that pulls numbers out of strings and converts them to double values
    public static double extractDouble(String line) {
        char[] numericArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        char[] lineArray = new char[line.length()];

        for (int i = 0; i < lineArray.length; i++) {
            lineArray[i] = line.charAt(i);
        }

        StringBuilder storage = new StringBuilder();

        for (int i = 0; i < lineArray.length; i++) {
            for (int j = 0; j < numericArray.length; j++) {
                if (lineArray[i] == numericArray[j]) {
                    storage.append(lineArray[i]);
                }
            }
        }

        String trimmedLine = storage.toString();
        double number = Double.parseDouble(trimmedLine);
        return number;

    }

    //------------------------------------------------------------------------------------------------------------------
    //method that pulls numbers out of strings and converts them to int values
    public static int extractInt(String line) {
        char[] numericArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        char[] lineArray = new char[line.length()];

        for (int i = 0; i < lineArray.length; i++) {
            lineArray[i] = line.charAt(i);
        }

        StringBuilder storage = new StringBuilder();

        for (int i = 0; i < lineArray.length; i++) {
            for (int j = 0; j < numericArray.length; j++) {
                if (lineArray[i] == numericArray[j]) {
                    storage.append(lineArray[i]);
                }
            }
        }

        String trimmedLine = storage.toString();
        int number = Integer.parseInt(trimmedLine);
        return number;

    }

    //------------------------------------------------------------------------------------------------------------------
    //method to convert dates to integers for comparison. Format: YYYY-MM-DD
    public static int dateToInt(String date) {
        date = eliminateNonAlpha(date);
        int returnNum = Integer.parseInt(date);
        return returnNum;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to convert an integer to date format (integer must be 8 characters long)
    public static String intToDate(int num) {

        StringBuilder number = new StringBuilder();
        number.append(num);
        String stringNum = number.toString();
        String[] dateArray = new String[5];
        dateArray[0] = stringNum.substring(0, 4); //YYYY
        dateArray[1] = "-";
        dateArray[2] = stringNum.substring(4, 6); //MM
        dateArray[3] = "-";
        dateArray[4] = stringNum.substring(6, 8); //DD

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < dateArray.length; i++) {
            result.append(dateArray[i]);
        }
        String returnString = result.toString();

        return returnString;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to check if a character is a numeric digit or a dash (-) character
    public static boolean isDigit(char character) {
        char[] acceptableInputArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean flag = false;
        for (int i = 0; i < acceptableInputArray.length; i++) {
            if (character == acceptableInputArray[i]) {
                flag = true;
            }
        }
        return flag;
    }

    //------------------------------------------------------------------------------------------------------------------
    //extension of isDigit (determines if a string contains only numeric characters)
    public static boolean isNum(String line){
        boolean flag = true;

        //automatic disqualification
        if(line.isBlank()){
            flag = false;
        } else {
            for (int i = 0; i < line.length(); i++) {
                if (!isDigit(line.charAt(i))) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to check format of a line for YYYY-MM-DD
    public static boolean correctDateFormat(String line) {

        boolean flag = true; //return value for method
        boolean lengthCheck; //preliminary screening of the argument's length

        //length screening
        if (line.length() == 10) {
            lengthCheck = true;
        } else {
            lengthCheck = false;
            flag = false;
        }


        //together: YYYY-MM-DD
        String year = "";
        String dash1 = "";
        String month = "";
        String dash2 = "";
        String day = "";

        boolean formatCheck = false;
        //only executes if length = 10
        if (lengthCheck) {

            //together: YYYY-MM-DD
            year = line.substring(0, 4); //YYYY
            dash1 = line.substring(4, 5); //-
            month = line.substring(5, 7); //MM
            dash2 = line.substring(7, 8); //-
            day = line.substring(8, 10); //DD

            //process year to ensure only numeric characters are present
            char[] yearArray = new char[year.length()];
            for (int i = 0; i < year.length(); i++) {
                yearArray[i] = year.charAt(i);
            }
            for (int i = 0; i < yearArray.length; i++) {
                if (!isDigit(yearArray[i])) {
                    flag = false;
                }
            }

            //process month to ensure only numeric characters are present
            char[] monthArray = new char[month.length()];
            for (int i = 0; i < month.length(); i++) {
                monthArray[i] = month.charAt(i);
            }
            for (int i = 0; i < monthArray.length; i++) {
                if (!isDigit(monthArray[i])) {
                    flag = false;
                }
            }

            //process day to ensure only numeric characters are present
            char[] dayArray = new char[day.length()];
            for (int i = 0; i < day.length(); i++) {
                dayArray[i] = day.charAt(i);
            }
            for (int i = 0; i < dayArray.length; i++) {
                if (!isDigit(dayArray[i])) {
                    flag = false;
                }
            }

            //process two dashes
            if (!dash1.equals("-") || !dash2.equals("-")) {
                flag = false;
            }
            //if flag has not been switched, formatCheck is true
            if (flag){
                formatCheck = true;
            }

        }


        //only executes if the line has proper date format (prevents NumberFormatExceptions)
        if(formatCheck) {
            LocalDate today = LocalDate.now();
            String todayDate = today.toString();

            String currentYear = todayDate.substring(0, 4);
            int currentYearInt = extractInt(currentYear);

            int monthInt = extractInt(month);
            int dayInt = extractInt(day);


            if (Integer.parseInt(year) > currentYearInt) {
                throw new IllegalArgumentException("Error: cannot exceed current year (" + currentYear + ")");
                //ensures month input is 1 through 12
            } else if (monthInt > 12 || monthInt < 1) {
                throw new IllegalArgumentException("Error: month out of range! Months: 1-12");
            } else if (dayInt < 1) {
                throw new IllegalArgumentException("Error: no day values less than 1");
            }


            //deals day values for each month
            switch (monthInt) { //the integer value that is the "switch" is monthInt
                case 1: //January
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in January!");
                    } else{
                        break;
                    }
                case 2: //February
                    if (dayInt > 29) { //28 or 29 days (leap year)
                        throw new IllegalArgumentException("Error: there are 28/29 days in February!");
                    } else{
                        break;
                    }
                case 3: //March
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in March!");
                    } else{
                        break;
                    }
                case 4: //April
                    if (dayInt > 30) {
                        throw new IllegalArgumentException("Error: there are 30 days in April!");
                    } else{
                        break;
                    }
                case 5: //May
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in May!");
                    } else{
                        break;
                    }
                case 6: //June
                    if (dayInt > 30) {
                        throw new IllegalArgumentException("Error: there are 30 days in June!");
                    } else{
                        break;
                    }
                case 7: //July
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in July!");
                    } else{
                        break;
                    }
                case 8: //August
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in August!");
                    } else {
                        break;
                    }
                case 9: //September
                    if (dayInt > 30) {
                        throw new IllegalArgumentException("Error: there are 30 days in September!");
                    } else{
                        break;
                    }
                case 10: //October
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in October!");
                    } else{
                        break;
                    }
                case 11: //November
                    if (dayInt > 30) {
                        throw new IllegalArgumentException("Error: there are 30 days in November!");
                    } else{
                        break;
                    }
                case 12: //December
                    if (dayInt > 31) {
                        throw new IllegalArgumentException("Error: there are 31 days in December!");
                    } else{
                        break;
                    }

            } //end of switch

        } //end of if-statement

        return flag; //returns flag
    } //end of method correctDateFormat

    //------------------------------------------------------------------------------------------------------------------


    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //toString analysis methods

    //method to analyze weather entries between two specified dates (start, end). Date format: YYYY-MM-DD
    public String analyzeRange(String start, String end) throws Exception {

        //ensures that the two parameters entered are formatted correctly
        if(!correctDateFormat(start) || !correctDateFormat(end)){
            throw new InputMismatchException("Illegal date format! Required: YYYY-MM-DD");

        } else{

            int startInt = dateToInt(start); //start date in integer format
            int endInt = dateToInt(end); //end date in integer format
            int startPlaceHolder; //variable to perform a switch between start and end values
            int endPlaceHolder; //variable to perform a switch between start and end values

            //if the user enters an end date that is less than the start, the two will be switched
            if(endInt < startInt){
                startPlaceHolder = endInt;
                endPlaceHolder = startInt;
                start = intToDate(startPlaceHolder);
                end = intToDate(endPlaceHolder);


            }

            StringBuilder storage = new StringBuilder();

            storage.append("\nLocation: " + getLocation());
            storage.append("\n\tStart date:   " + intToDate(roundStartToClosest(start)));
            storage.append("\n\tEnd date:     " + intToDate(roundEndToClosest(end)));
            storage.append("\n\tTotal days: >>>>>>>>>> " + (roundEndToClosest(end) - roundStartToClosest(start)));
            storage.append("\n\tAverage high: >>>>>>>> " + findAvgHigh(start, end));
            storage.append("\n\tAverage low: >>>>>>>>> " + findAvgLow(start, end));
            storage.append("\n\tAverage variance: >>>> " + findAvgVariance(start, end));
            storage.append("\n\tTotal rainfall: >>>>>> " + findRainQty(start, end) + "\"");
            storage.append("\n\tAverage humidity: >>>> " + findAvgHumidity(start, end) + "%");
            storage.append("\n\tAverage cloud cover: > " + findAvgCloudCover(start, end) + "%");

            return storage.toString();



        }

    } //end of method analyzeRange

    //------------------------------------------------------------------------------------------------------------------
    //method to analyze the weather entries in file without specified dates. Analyzes whole range.
    public String analyzeWeather() { //start of method analyzeWeather
        String message = "";

        try {

            StringBuilder storage = new StringBuilder();

            storage.append("\nLocation: " + getLocation()); //**************************
            storage.append("\n\tStart date:   " + intToDate(dateIntegers[0]));
            storage.append("\n\tEnd date:     " + intToDate(dateIntegers[dateIntegers.length-1]));
            storage.append("\n\tTotal days: >>>>>>>>>> " + getDayCount());
            storage.append("\n\tAverage high: >>>>>>>> " + findAvgHigh());
            storage.append("\n\tAverage low: >>>>>>>>> " + findAvgLow());
            storage.append("\n\tAverage variance: >>>> " + findAvgVariance());
            storage.append("\n\tTotal rainfall: >>>>>> " + findRainQty() + "\"");
            storage.append("\n\tAverage humidity: >>>> " + findAvgHumidity() + "%");
            storage.append("\n\tAverage cloud cover: > " + findAvgCloudCover() + "%");

            message = storage.toString();

        } catch(Exception e){
            System.out.print(e.getMessage());
        }


        return message;

    } //end of method analyzeWeather

    //------------------------------------------------------------------------------------------------------------------
    //method to analyze the weather entries in a file without specified dates. Emphasizes statistical values (%)
    public String statAnalysis(){
        String message = "";

        try {

            StringBuilder storage = new StringBuilder();

            storage.append("\nLocation: " + getLocation());
            storage.append("\n\tStart date: " + intToDate(dateIntegers[0]));
            storage.append("\n\tEnd date:   " + intToDate(dateIntegers[dateIntegers.length-1]));
            storage.append("\n\tTotal days: >>>>>>> " + getDayCount());
            storage.append("\n\tAverage high: >>>>> " + findAvgHigh());
            storage.append("\n\tAverage low: >>>>>> " + findAvgLow());
            storage.append("\n\tAverage variance: > " + findAvgVariance());
            storage.append("\n\tRainy days: >>>>>>> " + findRainPercentage() + "%");
            storage.append("\n\tHumid days: >>>>>>> " + findHumidPercentage() + "%");
            storage.append("\n\tCloudy days: >>>>>> " + findCloudPercentage() + "%");

            message = storage.toString();

        } catch(Exception e){
            System.out.print(e.getMessage());
        }

        return message;

    }

    //------------------------------------------------------------------------------------------------------------------
    //method to analyze the weather entries in a file with specified dates. Emphasizes statistical values (%)
    public String statRangeAnalysis(String start, String end) throws Exception{

        String returnString = "";

        //ensures that the parameters entered are formatted correctly
        if(!correctDateFormat(start) || !correctDateFormat(end)){
            throw new IllegalArgumentException("Illegal date format! Required: YYYY-MM-DD");

        } else{

            StringBuilder storage = new StringBuilder(); //stores lines within above date range ^

            storage.append("\nLocation: " + getLocation());
            storage.append("\n\tStart date: " + intToDate(roundStartToClosest(start)));
            storage.append("\n\tEnd date:   " + intToDate(roundEndToClosest(end)));
            storage.append("\n\tTotal days: >>>>>>> " + (roundEndToClosest(end) - roundStartToClosest(start)));
            storage.append("\n\tAverage high: >>>>> " + findAvgHigh(start, end));
            storage.append("\n\tAverage low: >>>>>> " + findAvgLow(start, end));
            storage.append("\n\tAverage variance: > " + findAvgVariance(start, end));
            storage.append("\n\tRainy days: >>>>>>> " + findRainPercentage(start, end) + "%");
            storage.append("\n\tHumid days: >>>>>>> " + findHumidPercentage(start, end) + "%");
            storage.append("\n\tCloudy days: >>>>>> " + findCloudPercentage(start, end) + "%");

            returnString = storage.toString();
        }

        return returnString; //returns returnString
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to analyse the weather entries for the whole range. Gives the user autonomy for return measurement types
    public String customAnalysis (String [] array) throws Exception{

        StringBuilder storage = new StringBuilder();
        storage.append("Location: " + getLocation());
        storage.append("\nStart date: " + intToDate(dateIntegers[0]));
        storage.append("\nEnd date: " + intToDate(dateIntegers[dateIntegers.length-1]));
        storage.append("\nTotal days: " + getDayCount());

        for (int i = 0; i < array.length; i++) {
            if (array[i].length() == 1) {
                if (array[i].equals("1")) {
                    storage.append("\nAverage high: >>>>>>>> " + findAvgHigh());
                } else if (array[i].equals("2")) {
                    storage.append("\nAverage low: >>>>>>>>> " + findAvgLow());
                } else if (array[i].equals("3")) {
                    storage.append("\nAverage variance: >>>> " + findAvgVariance());
                } else if (array[i].equals("4")) {
                    storage.append("\nAverage humidity: >>>> " + findAvgHumidity() + "%");
                } else if (array[i].equals("5")) {
                    storage.append("\nHumid days: >>>>>>>>>> " + findHumidPercentage() + "%"); //% days above 60% average humidity
                } else if (array[i].equals("6")) {
                    storage.append("\nTotal rainfall: >>>>>> " + findRainQty() + "\"");
                } else if (array[i].equals("7")) {
                    storage.append("\nRainy days: >>>>>>>>>> " + findRainPercentage() + "%");
                } else if (array[i].equals("8")) {
                    storage.append("\nAverage cloud cover: > " + findAvgCloudCover() + "%");
                } else if (array[i].equals("9")) {
                    storage.append("\nCloudy days: >>>>>>>>> " + findCloudPercentage() + "%");
                }
            } else { //query methods
                //daysAbove
                if (array[i].startsWith("10")) {
                    int temp = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays above " + temp + ": " + daysAbove(temp));
                    //daysBelow
                } else if (array[i].startsWith("11")) {
                    int temp = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays below " + temp + ": " + daysBelow(temp));
                } else if (array[i].startsWith("12")){
                    int percentage = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays above " + percentage + "% cloud cover: " + cloudCoverAbove(percentage));
                }
            }
        }

        return storage.toString();
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to analyze the weather entries for a specified range. Gives user autonomy for return measurement types
    public String customRangeAnalysis(String start, String end, String [] array) throws Exception{

        String returnString = "";
        StringBuilder storage = new StringBuilder(); //stores lines within above date range ^

        storage.append("Location: " + getLocation());
        storage.append("\nStart date: " + intToDate(roundStartToClosest(start)));
        storage.append("\nEnd date: " + intToDate(roundEndToClosest(end)));
        storage.append("\nTotal days: " + (roundEndToClosest(end) - roundStartToClosest(start)));

        for (int i = 0; i < array.length; i++) {
            if (array[i].length() == 1) {
                if (array[i].equals("1")) {
                    storage.append("\nAverage high: >>>>>>>> " + findAvgHigh());
                } else if (array[i].equals("2")) {
                    storage.append("\nAverage low: >>>>>>>>> " + findAvgLow());
                } else if (array[i].equals("3")) {
                    storage.append("\nAverage variance: >>>> " + findAvgVariance());
                } else if (array[i].equals("4")) {
                    storage.append("\nAverage humidity: >>>> " + findAvgHumidity() + "%");
                } else if (array[i].equals("5")) {
                    storage.append("\nHumid days: >>>>>>>>>> " + findHumidPercentage() + "%"); //% days above 60% average humidity
                } else if (array[i].equals("6")) {
                    storage.append("\nTotal rainfall: >>>>>> " + findRainQty() + "\"");
                } else if (array[i].equals("7")) {
                    storage.append("\nRainy days: >>>>>>>>>> " + findRainPercentage() + "%");
                } else if (array[i].equals("8")) {
                    storage.append("\nAverage cloud cover: > " + findAvgCloudCover() + "%");
                } else if (array[i].equals("9")) {
                    storage.append("\nCloudy days: >>>>>>>>> " + findCloudPercentage() + "%");
                }
            } else { //query methods
                //daysAbove
                if (array[i].startsWith("10")) {
                    int temp = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays above " + temp + ": " + daysAbove(temp));
                    //daysBelow
                } else if (array[i].startsWith("11")) {
                    int temp = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays below " + temp + ": " + daysBelow(temp));
                } else if (array[i].startsWith("12")){
                    int percentage = Integer.parseInt(array[i].substring(2));
                    storage.append("\nDays above " + percentage + "% cloud cover: " + cloudCoverAbove(percentage));
                }
            }
        }
        return storage.toString();

    }

    //------------------------------------------------------------------------------------------------------------------



} //end of class WeatherAnalysis
//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————


