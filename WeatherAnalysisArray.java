/*
The WeatherAnalysisArray class is used as an extension of the WeatherAnalysis class. It allows for further processing
of WeatherAnalysis arrays with calls to instance methods. This class simply creates an object (the WeatherAnalysisArray
object) that stores an array of WeatherAnalysis objects. This allows for analysis of data from WeatherAnalysis objects
to be stored in one place for analysis.
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

class WeatherAnalysisArray{

    final Color turqTheme = new Color(0x08EAFF);
    final Color darkTheme = new Color(0x242120);

    private WeatherAnalysis [] locations;


    WeatherAnalysisArray(WeatherAnalysis [] array){
        this.setLocations(array);
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //setter method for constructor
    public void setLocations(WeatherAnalysis [] array){
        locations = array;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to return table for standard analysis (whole range)
    public JTable standardTableGen(){

        int locationQty = locations.length;

        String[] column1Data = {"Location", "Start Date", "End Date", "Avg High", "Avg Low", "Avg Variance", "Total Rainfall", "Avg Humidity", "Avg Cloud Cover"};

        Object[][] data = new Object[locationQty][column1Data.length];

        try {

            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][j] = locations[i].getLocation();
                    } else if (j == 1) {
                        data[i][j] = locations[i].getStartDate();
                    } else if (j == 2) {
                        data[i][j] = locations[i].getEndDate();
                    } else if (j == 3) {
                        data[i][j] = locations[i].findAvgHigh() + "°";
                    } else if (j == 4) {
                        data[i][j] = locations[i].findAvgLow() + "°";
                    } else if (j == 5) {
                        data[i][j] = locations[i].findAvgVariance() + "°";
                    } else if (j == 6) {
                        data[i][j] = locations[i].findRainQty() + "\"";
                    } else if (j == 7) {
                        data[i][j] = locations[i].findAvgHumidity() + "%";
                    } else {
                        data[i][j] = locations[i].findAvgCloudCover() + "%";
                    }
                }

            }


        } catch (Exception e){
            System.out.println(e.getMessage()); //******************
        }

        JTable returnTable = new JTable(data, column1Data);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;

    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public JTable standardTableGen(String start, String end){

        int locationQty = locations.length;

        String[] column1Data = {"Location", "Start Date", "End Date", "Avg High", "Avg Low", "Avg Variance", "Total Rainfall", "Avg Humidity", "Avg Cloud Cover"};

        Object[][] data = new Object[locationQty][column1Data.length];

        try {

            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][j] = locations[i].getLocation();
                    } else if (j == 1) {
                        data[i][j] = locations[i].getStartDate(start);
                    } else if (j == 2) {
                        data[i][j] = locations[i].getEndDate(end);
                    } else if (j == 3) {
                        data[i][j] = locations[i].findAvgHigh(start, end) + "°";
                    } else if (j == 4) {
                        data[i][j] = locations[i].findAvgLow(start, end) + "°";
                    } else if (j == 5) {
                        data[i][j] = locations[i].findAvgVariance(start, end) + "°";
                    } else if (j == 6) {
                        data[i][j] = locations[i].findRainQty(start, end) + "\"";
                    } else if (j == 7) {
                        data[i][j] = locations[i].findAvgHumidity(start, end) + "%";
                    } else {
                        data[i][j] = locations[i].findAvgCloudCover(start, end) + "%";
                    }
                }

            }


        } catch (Exception e){
            System.out.println(e.getMessage()); //******************
        }

        JTable returnTable = new JTable(data, column1Data);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;

    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to return table for statistical analysis (whole range)
    public JTable statTableGen(){
        int locationQty = locations.length;

        String [] column1Data = {"Location", "Start Date","End Date","Avg High", "Avg Low","Avg Variance","Rainy Days","Humid Days","Cloudy Days"};

        Object [][] data = new Object[locationQty][column1Data.length];

        try {

            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][j] = locations[i].getLocation();
                    } else if (j == 1) {
                        data[i][j] = locations[i].getStartDate();
                    } else if (j == 2) {
                        data[i][j] = locations[i].getEndDate();
                    } else if (j == 3) {
                        data[i][j] = locations[i].findAvgHigh() + "°";
                    } else if (j == 4) {
                        data[i][j] = locations[i].findAvgLow() + "°";
                    } else if (j == 5) {
                        data[i][j] = locations[i].findAvgVariance() + "°";
                    } else if (j == 6) {
                        data[i][j] = locations[i].findRainPercentage() + "%";
                    } else if (j == 7) {
                        data[i][j] = locations[i].findHumidPercentage() + "%";
                    } else {
                        data[i][j] = locations[i].findCloudPercentage() + "%";
                    }
                }

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        JTable returnTable = new JTable(data, column1Data);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;
    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public JTable statTableGen(String start, String end){
        int locationQty = locations.length;

        String [] column1Data = {"Location", "Start Date","End Date","Avg High", "Avg Low","Avg Variance","Rainy Days","Humid Days","Cloudy Days"};

        Object [][] data = new Object[locationQty][column1Data.length];

        try {

            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][j] = locations[i].getLocation();
                    } else if (j == 1) {
                        data[i][j] = locations[i].getStartDate(start);
                    } else if (j == 2) {
                        data[i][j] = locations[i].getEndDate(end);
                    } else if (j == 3) {
                        data[i][j] = locations[i].findAvgHigh(start, end) + "°";
                    } else if (j == 4) {
                        data[i][j] = locations[i].findAvgLow(start, end) + "°";
                    } else if (j == 5) {
                        data[i][j] = locations[i].findAvgVariance(start, end) + "°";
                    } else if (j == 6) {
                        data[i][j] = locations[i].findRainPercentage(start, end) + "%";
                    } else if (j == 7) {
                        data[i][j] = locations[i].findHumidPercentage(start, end) + "%";
                    } else {
                        data[i][j] = locations[i].findCloudPercentage(start, end) + "%";
                    }
                }

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        JTable returnTable = new JTable(data, column1Data);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;
    }
    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to return table for custom analysis (whole range)
    public JTable customTableGen(int [] choiceArray){

        int locationQty = locations.length;

        //determines how many columns will be present in the table
        int columnCount = 3;
        for(int i = 0; i < choiceArray.length-3; i++){
            if(choiceArray[i] == 1){
                columnCount++;
            }
        }

        //future loop will set all values to 0, need a duplicate
        int [] choiceCopy = new int[choiceArray.length-3];
        for(int i = 0; i < choiceCopy.length; i++){
            choiceCopy[i] = choiceArray[i];
        }


        String [] column1Data = {"Location", "Start Date","End Date","Avg High", "Avg Low","Avg Variance","Avg Humidity",
                "Humid Days","Total Rainfall","Rainy Days","Avg Cloud Cover","Cloudy Days","Days Above " + choiceArray[12] + "°"
                ,"Days Below " + choiceArray[13] + "°","# Days Cloud Cover Above " + choiceArray[14] + "%"};

        String [] returnColumn = new String[columnCount];
        //constants
        returnColumn[0] = column1Data[0];
        returnColumn[1] = column1Data[1];
        returnColumn[2] = column1Data[2];

        for(int i = 3; i < returnColumn.length; i++){ //picks up at index 3 due to previous assignment
            for(int j = 3; j < column1Data.length; j++){ //picks up at index 3 due to constants
                if(choiceArray[j-3] == 1){ //choiceArray length = 12, need to compensate j by subtracting 3
                    returnColumn[i] = column1Data[j];
                    choiceArray[j-3] = 0; //prevents double counts
                    i++; //jumps out of cycle
                }
            }
        }

        Object [][] data = new Object[locationQty][columnCount];

        int choiceIndex = 0;

        try {

            //loops to add data to two-dimensional data array
            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][choiceIndex] = locations[i].getLocation();
                        choiceIndex++;
                    } else if (j == 1) {
                        data[i][choiceIndex] = locations[i].getStartDate();
                        choiceIndex++;
                    } else if (j == 2) {
                        data[i][choiceIndex] = locations[i].getEndDate();
                        choiceIndex++;
                    } else if (j == 3 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgHigh() + "°";
                        choiceIndex++;
                    } else if (j == 4 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgLow() + "°";
                        choiceIndex++;
                    } else if (j == 5 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgVariance() + "°";
                        choiceIndex++;
                    } else if (j == 6 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgHumidity() + "%";
                        choiceIndex++;
                    } else if (j == 7 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findHumidPercentage() + "%";
                        choiceIndex++;
                    } else if (j == 8 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findRainQty() + "\"";
                        choiceIndex++;
                    } else if (j == 9 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findRainPercentage() + "%";
                        choiceIndex++;
                    } else if (j == 10 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgCloudCover() + "%";
                        choiceIndex++;
                    } else if (j == 11 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findCloudPercentage() + "%";
                        choiceIndex++;
                    } else if (j == 12 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].daysAbove(choiceArray[12]);
                        choiceIndex++;
                    } else if (j == 13 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].daysBelow(choiceArray[13]);
                        choiceIndex++;
                    } else if (j == 14 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].cloudCoverAbove(choiceArray[14]);
                        choiceIndex++;
                    }
                }
                choiceIndex = 0; //after each loop of i, choiceIndex is reset to 0
            }


        } catch (Exception e){
            System.out.print(e.getMessage());
        }

        JTable returnTable = new JTable(data, returnColumn);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;

    }

    //------------------------------------------------------------------------------------------------------------------
    //above ^ overloaded for specific range
    public JTable customTableGen(String start, String end, int [] choiceArray){

        int locationQty = locations.length;

        //determines how many columns will be present in the table
        int columnCount = 3;
        for(int i = 0; i < choiceArray.length-3; i++){
            if(choiceArray[i] == 1){
                columnCount++;
            }
        }

        //future loop will set all values to 0, need a duplicate
        int [] choiceCopy = new int[choiceArray.length-3];
        for(int i = 0; i < choiceCopy.length; i++){
            choiceCopy[i] = choiceArray[i];
        }


        String [] column1Data = {"Location", "Start Date","End Date","Avg High", "Avg Low","Avg Variance","Avg Humidity",
                "Humid Days","Total Rainfall","Rainy Days","Avg Cloud Cover","Cloudy Days","Days Above " + choiceArray[12] + "°"
                ,"Days Below " + choiceArray[13] + "°","# Days Cloud Cover Above " + choiceArray[14] + "%"};

        String [] returnColumn = new String[columnCount];
        //constants
        returnColumn[0] = column1Data[0];
        returnColumn[1] = column1Data[1];
        returnColumn[2] = column1Data[2];

        for(int i = 3; i < returnColumn.length; i++){ //picks up at index 3 due to previous assignment
            for(int j = 3; j < column1Data.length; j++){ //picks up at index 3 due to constants
                if(choiceArray[j-3] == 1){ //choiceArray length = 12, need to compensate j by subtracting 3
                    returnColumn[i] = column1Data[j];
                    choiceArray[j-3] = 0; //prevents double counts
                    i++; //jumps out of cycle
                }
            }
        }

        Object [][] data = new Object[locationQty][columnCount];

        int choiceIndex = 0;

        try {

            //loops to add data to two-dimensional data array
            for (int i = 0; i < locationQty; i++) {
                for (int j = 0; j < column1Data.length; j++) {
                    if (j == 0) {
                        data[i][choiceIndex] = locations[i].getLocation();
                        choiceIndex++;
                    } else if (j == 1) {
                        data[i][choiceIndex] = locations[i].getStartDate(start);
                        choiceIndex++;
                    } else if (j == 2) {
                        data[i][choiceIndex] = locations[i].getEndDate(end);
                        choiceIndex++;
                    } else if (j == 3 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgHigh(start, end) + "°";
                        choiceIndex++;
                    } else if (j == 4 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgLow(start, end) + "°";
                        choiceIndex++;
                    } else if (j == 5 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgVariance(start, end) + "°";
                        choiceIndex++;
                    } else if (j == 6 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgHumidity(start, end) + "%";
                        choiceIndex++;
                    } else if (j == 7 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findHumidPercentage(start, end) + "%";
                        choiceIndex++;
                    } else if (j == 8 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findRainQty() + "\"";
                        choiceIndex++;
                    } else if (j == 9 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findRainPercentage(start, end) + "%";
                        choiceIndex++;
                    } else if (j == 10 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findAvgCloudCover(start, end) + "%";
                        choiceIndex++;
                    } else if (j == 11 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].findCloudPercentage(start, end) + "%";
                        choiceIndex++;
                    } else if (j == 12 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].daysAbove(start, end, choiceArray[12]);
                        choiceIndex++;
                    } else if (j == 13 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].daysBelow(start, end, choiceArray[13]);
                        choiceIndex++;
                    } else if (j == 14 && choiceCopy[j - 3] == 1) {
                        data[i][choiceIndex] = locations[i].cloudCoverAbove(start, end, choiceArray[14]);
                        choiceIndex++;
                    }
                }
                choiceIndex = 0; //after each loop of i, choiceIndex is reset to 0
            }


        } catch (Exception e){
            System.out.print(e.getMessage());
        }

        JTable returnTable = new JTable(data, returnColumn);

        returnTable.setBackground(darkTheme);
        returnTable.setForeground(turqTheme);
        returnTable.getTableHeader().setBackground(darkTheme);
        returnTable.getTableHeader().setForeground(turqTheme);
        returnTable.getTableHeader().setPreferredSize(new Dimension(10,30));
        returnTable.setRowHeight(30);
        returnTable.setGridColor(turqTheme);

        return returnTable;

    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————


}
