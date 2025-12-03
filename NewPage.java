/*
Nathan Dann
Summer 2025

The NewPage class has three forms:

1. The driver page

   Takes the following information:

   * location name
   * day's high temperature (integer value)
   * day's low temperature (integer value)
   * day's total precipitation (double value)
   * day's average humidity (integer value, percentage)
   * day's average cloud cover (integer value, percentage)

   If all of these fields are filled with acceptable values, they will be written to a file named
   (location)Weather.txt in the user's /Applications/documents/tempestFiles directory

   If not, exception messages will display

2. The analysis parameters page

   Takes the following information:

   * number of locations to analyze
   * names of locations
   * range of analysis
   * analysis mode
   — if analysis mode = custom, select pertinent checkboxes

   If all parameters are entered correctly, a table of the data values corresponding to the locations and
   selected analysis mode will appear

3. The graph parameters page

    Takes the following information:

   * number of locations
   * location names
   * range of analysis
   * data to graph

   If all parameters were entered correctly, a line graph will appear displaying the data values selected for the
   specified locations

*/


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

class NewPage {

    JFrame frame = new JFrame();

    final Color turqTheme = new Color(0x08EAFF);
    final Color darkTheme = new Color(0x242120);

    final Font themeFont = new Font("", Font.BOLD, 14);
    final Font smallThemeFont = new Font("", Font.BOLD, 12);

    JTextField [] locationText; //array that stores location text fields
    JCheckBox [] boxArray; //array that stores check boxes for later access

    NewPage(int choice){

        //driver page option
        if(choice == 1) {
            frame.setTitle("Weather Driver");
            frame.setSize(500, 375);
            frame.getContentPane().setBackground(darkTheme);
            frame.setLayout(null);
            //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //panel to house the GridLayout
            JPanel parentPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            parentPanel.setBackground(darkTheme);
            parentPanel.setBounds(10,0,480,300);

            JPanel buttonPanel = new JPanel(null);
            buttonPanel.setBounds(0,300,500,75);
            buttonPanel.setBackground(turqTheme);

            //button that writes to location files
            JButton writeButton;
            writeButton = new JButton("Write");
            writeButton.setFont(themeFont);
            writeButton.setBounds(200,5,100,35);

            JButton helpButton = new JButton("?");
            helpButton.setFont(themeFont);
            helpButton.setBounds(10,5,35,35);

            buttonPanel.add(writeButton);
            buttonPanel.add(helpButton);

            frame.add(buttonPanel);

            //labels
            JLabel locationLabel = new JLabel("Location");
            locationLabel.setFont(themeFont);
            locationLabel.setForeground(turqTheme);
            JLabel highLabel = new JLabel("High");
            highLabel.setFont(themeFont);
            highLabel.setForeground(turqTheme);
            JLabel lowLabel = new JLabel("Low");
            lowLabel.setFont(themeFont);
            lowLabel.setForeground(turqTheme);
            JLabel precipLabel = new JLabel("Precipitation (inches)");
            precipLabel.setFont(themeFont);
            precipLabel.setForeground(turqTheme);
            JLabel humidLabel = new JLabel("Avg Humidity (%)");
            humidLabel.setFont(themeFont);
            humidLabel.setForeground(turqTheme);
            JLabel cloudLabel = new JLabel("Avg Cloud Cover (%)");
            cloudLabel.setFont(themeFont);
            cloudLabel.setForeground(turqTheme);


            //entry boxes
            JTextField locationEntryBox = new JTextField();
            JTextField highEntryBox = new JTextField();
            JTextField lowEntryBox = new JTextField();
            JTextField precipEntryBox = new JTextField();
            JTextField humidEntryBox = new JTextField();
            JTextField cloudEntryBox = new JTextField();


            //adding labels to panel
            parentPanel.add(locationLabel);
            parentPanel.add(locationEntryBox);

            parentPanel.add(highLabel);
            parentPanel.add(highEntryBox);

            parentPanel.add(lowLabel);
            parentPanel.add(lowEntryBox);

            parentPanel.add(precipLabel);
            parentPanel.add(precipEntryBox);

            parentPanel.add(humidLabel);
            parentPanel.add(humidEntryBox);

            parentPanel.add(cloudLabel);
            parentPanel.add(cloudEntryBox);


            //sets alignment of cells
            locationLabel.setHorizontalAlignment(SwingConstants.CENTER);
            highLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lowLabel.setHorizontalAlignment(SwingConstants.CENTER);
            precipLabel.setHorizontalAlignment(SwingConstants.CENTER);
            humidLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cloudLabel.setHorizontalAlignment(SwingConstants.CENTER);


            frame.add(parentPanel);

            frame.setVisible(true);

            ActionListener writeListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == writeButton) {

                        boolean [] exceptionIndexes = new boolean[6];

                        int exceptionCount = 0;

                        //establishes name of file to be written to
                        String location = "/Applications/Documents/TempestFiles/" + locationEntryBox.getText() + "Weather.txt";

                        //initializes location name
                        String locationName = "";

                        File outFile = new File(location);

                        //establishes day as an object of the weather class
                        Weather day = new Weather();

                        if (!locationEntryBox.getText().isBlank()) {
                            locationEntryBox.setBackground(new Color(0xC9F5D6));
                            locationEntryBox.setFocusable(false);
                            exceptionIndexes[0] = false;
                            //stores location name in title format
                            locationName = WeatherDriver.toTitle(locationEntryBox.getText());
                        } else {
                            locationEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[0] = true;
                            exceptionCount++;
                        }

                        //sets high value
                        if (isNum(highEntryBox.getText())) {
                            int high = Integer.parseInt(highEntryBox.getText());
                            if(high < 140) {
                                day.setHigh(high);
                                highEntryBox.setBackground(new Color(0xC9F5D6));
                                highEntryBox.setFocusable(false);
                                exceptionIndexes[1] = false;
                                //value outside of reasonable range
                            } else{
                                highEntryBox.setBackground(new Color(0xF07A90));
                                exceptionIndexes[1] = true;
                                exceptionCount++;
                            }
                            //not a number
                        } else {
                            highEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[1] = true;
                            exceptionCount++;
                        }


                        //sets low value
                        if (isNum(lowEntryBox.getText())) {
                            int low = Integer.parseInt(lowEntryBox.getText());
                            if(low > -140) {
                                day.setLow(low);
                                lowEntryBox.setBackground(new Color(0xC9F5D6));
                                lowEntryBox.setFocusable(false);
                                exceptionIndexes[2] = false;
                                //executes if value is outside of reasonable range
                            } else{
                                lowEntryBox.setBackground(new Color(0xF07A90));
                                exceptionIndexes[2] = true;
                                exceptionCount++;
                            }
                            //executes if value is not a number
                        } else {
                            lowEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[2] = true;
                            exceptionCount++;
                        }


                        //sets precipitation value
                        if (isNum(precipEntryBox.getText())) {
                            double rain = Double.parseDouble(precipEntryBox.getText());
                            if(rain >=0) {
                                day.setPrecipitation(rain);
                                precipEntryBox.setBackground(new Color(0xC9F5D6));
                                precipEntryBox.setFocusable(false);
                                exceptionIndexes[3] = false;
                                //executes in case of a negative precipitation value
                            } else{
                                precipEntryBox.setBackground(new Color(0xF07A90));
                                exceptionIndexes[3] = true;
                                exceptionCount++;
                            }
                            //executes in case of a non-numeric entry
                        } else {
                            precipEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[3] = true;
                            exceptionCount++;

                        }


                        //sets humid value
                        if (isNum(humidEntryBox.getText())) {
                            int humid = Integer.parseInt(humidEntryBox.getText());
                            if(humid > 0 && humid <= 100) {
                                day.setHumid(humid);
                                humidEntryBox.setBackground(new Color(0xC9F5D6));
                                humidEntryBox.setFocusable(false);
                                exceptionIndexes[4] = false;
                                //executes in case of out-of-range value
                            } else{
                                humidEntryBox.setBackground(new Color(0xF07A90));
                                exceptionIndexes[4] = true;
                                exceptionCount++;
                            }
                            //executes in case of non-numeric entry
                        } else {
                            humidEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[4] = true;
                            exceptionCount++;
                        }


                        //sets cloudy value
                        if (isNum(cloudEntryBox.getText())) {
                            int cloud = Integer.parseInt(cloudEntryBox.getText());
                            if(cloud > 0 && cloud <=100) {
                                day.setCloudCover(cloud);
                                cloudEntryBox.setBackground(new Color(0xC9F5D6));
                                cloudEntryBox.setFocusable(false);
                                exceptionIndexes[5] = false;
                                //executes in case of out-of-range value
                            } else{
                                cloudEntryBox.setBackground(new Color(0xF07A90));
                                exceptionIndexes[5] = true;
                                exceptionCount++;
                            }
                            //executes in case of non-numeric entry
                        } else {
                            cloudEntryBox.setBackground(new Color(0xF07A90));
                            exceptionIndexes[5] = true;
                            exceptionCount++;
                        }


                        //----------------------------------------------------------------------------------------------
                        //executes if errors are present
                        if(exceptionCount > 0) {

                            //array to store the messages for the following errors
                            String[] exceptionMessages = new String[exceptionCount];

                            int pointer = 0; //index value for the above exceptionMessages array

                            //rather than exceptions, just print to Error Window popup
                            for (int i = 0; i < exceptionIndexes.length; i++) {
                                if (exceptionIndexes[i]) {
                                    if (i == 0) {
                                        exceptionMessages[pointer] = "Error: Location Cannot be Blank!";
                                        pointer++;
                                        //high
                                    } else if (i == 1) {
                                        if(isNum(highEntryBox.getText())){
                                            int high = Integer.parseInt(highEntryBox.getText());
                                            //error arises due to unreasonable value
                                            if(high > 140) {
                                                exceptionMessages[pointer] = "Error (High): Value exceeds reasonable limits!";
                                                pointer++;
                                            }
                                            //error arises due to non-numeric value
                                        } else {
                                            exceptionMessages[pointer] = "Error (High): Numeric Values Only!";
                                            pointer++;
                                        }
                                        //low
                                    } else if (i == 2) {
                                        if(isNum(lowEntryBox.getText())){
                                            int low = Integer.parseInt(lowEntryBox.getText());
                                            //error arises due to unreasonable value
                                            if(low < -140) {
                                                exceptionMessages[pointer] = "Error (Low): Value exceeds reasonable limits!";
                                                pointer++;
                                            }
                                            //error arises due to non-numeric value
                                        } else {
                                            exceptionMessages[pointer] = "Error (Low): Numeric Values Only!";
                                            pointer++;
                                        }
                                        //precipitation
                                    } else if (i == 3) {
                                        if(isNum(precipEntryBox.getText())){
                                            double precipitation = Double.parseDouble(precipEntryBox.getText());
                                            //error arises due to negative input
                                            if(precipitation < 0) {
                                                exceptionMessages[pointer] = "Error (Precipitation): Negative values prohibited!";
                                                pointer++;
                                            }
                                            //error arises due to non-numeric input
                                        } else {
                                            exceptionMessages[pointer] = "Error (Precipitation): Numeric Values Only!";
                                            pointer++;
                                        }
                                        //humidity
                                    } else if (i == 4) {
                                        if(isNum(humidEntryBox.getText())){
                                            int humid = Integer.parseInt(humidEntryBox.getText());
                                            //error arises to invalid percentage value
                                            if(humid <= 0 || humid > 100) {
                                                exceptionMessages[pointer] = "Error (Humid): Invalid percentage value!";
                                                pointer++;
                                            }
                                            //error arises due to non-numeric input
                                        } else {
                                            exceptionMessages[pointer] = "Error (Humid): Integer values only!";
                                            pointer++;
                                        }
                                    } else {
                                        if(isNum(cloudEntryBox.getText())){
                                            int cloud = Integer.parseInt(cloudEntryBox.getText());
                                            if(cloud <= 0 || cloud > 100) {
                                                exceptionMessages[pointer] = "Error (Cloud Cover): Invalid percentage value!";
                                                pointer++;
                                            }
                                        } else {
                                            exceptionMessages[pointer] = "Error (Cloud Cover): Integer values only!";
                                            pointer++;
                                        }
                                    }
                                }
                            }

                            //executes if exceptions are present
                            ReturnWindow errorWindow = new ReturnWindow(exceptionMessages);

                            //executes if no exceptions occur
                        } else {

                            try {
                                //converts weather object contents to string format
                                String message = day.toString();

                                if (!outFile.exists()) {
                                    outFile.createNewFile();
                                }

                                //output stream enables appending, opens file in append mode
                                FileOutputStream outStream = new FileOutputStream(outFile, true);
                                PrintWriter outWriter = new PrintWriter(outStream); //writes text to external files

                                //prints location name if file is new / empty
                                Scanner readFile = new Scanner(outFile);
                                boolean flag = true; //true = file is blank
                                while (readFile.hasNextLine()) {
                                    String line = readFile.nextLine();
                                    if (!line.isBlank()) {
                                        flag = false; //false = file has contents
                                    }
                                }

                                //prints the location name if the entry is the first in the file
                                if (flag) {
                                    outWriter.print(locationName + "\n\n"); //prints location name
                                }

                                outWriter.print(message); //prints the weather entry

                                outWriter.close(); //closes outWriter to execute writing / prevent data corruption

                                //message for first file entry uses locationName to avoid entire file path being showed
                                if (flag) {
                                    JTextArea successMessage = new JTextArea(locationName.toLowerCase() + "Weather.txt was successfully created\nand written to.");
                                    ReturnWindow successWindow = new ReturnWindow(successMessage);
                                    //message for all other file entries
                                } else {
                                    JTextArea successMessage = new JTextArea(locationName.toLowerCase() + "Weather.txt was successfully written to.");
                                    ReturnWindow successWindow = new ReturnWindow(successMessage);
                                }

                            } catch(Exception x){ //catches ioExceptions
                                ReturnWindow errorWindow = new ReturnWindow(x.getMessage());
                            }
                        }
                        //----------------------------------------------------------------------------------------------


                    } else if(e.getSource() == helpButton){
                        //informs the user of the purpose of the present window
                        String message = "This window takes weather data input for a\nspecific location " +
                                "and writes it to a .txt file\nthat will be stored for analysis.\n\n" +
                                "1. Enter location name\n" +
                                "2. Enter the day's high temperature\n" +
                                "3. Enter the day's low temperature\n" +
                                "4. Enter the day's total precipitation\n" +
                                "5. Enter the day's average humidity\n" +
                                "6. Enter the day's average cloud cover";
                        JTextArea helpMessage = new JTextArea(message);
                        ReturnWindow helpWindow = new ReturnWindow(helpMessage);
                    }

                }

            };

            writeButton.addActionListener(writeListener);
            helpButton.addActionListener(writeListener);

            //————————————————————————————————————————————————————————————————————————————————————————————————————————————
            //option for analysis window
        } else if (choice == 2){
            frame.setTitle("Analysis Parameters");
            frame.setSize(500,550);
            frame.getContentPane().setBackground(darkTheme);
            //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(null);

            //----------------------------------------------------------------------------------------------------------
            //parent panel for frame
            JPanel topLeft = new JPanel();
            topLeft.setLayout(null);
            topLeft.setBounds(0,0,500,550);
            topLeft.setBackground(darkTheme);
            //--------------------------------------------------------------------------------------------------------------
            //section 1

            int xCoord = 0;
            int yCoord = 0;

            //parent panel for location section
            JPanel locationPanel = new JPanel();
            locationPanel.setLayout(null);
            locationPanel.setBackground(darkTheme);
            locationPanel.setBounds(xCoord,yCoord,500,170); //runs to y = 140

            yCoord+=170;


            JLabel label = new JLabel();
            label.setText("Number of Locations");
            label.setFont(themeFont);
            label.setForeground(turqTheme);
            label.setBounds(30,10,200,25); //to 35
            locationPanel.add(label);

            //top left comboBox
            String [] choices = {"1","2","3","4","5","6","7","8","9","10"};
            JComboBox numberComboBox = new JComboBox(choices);
            numberComboBox.setBounds(230,10,75,25); //to 35
            locationPanel.add(numberComboBox);


            JLabel locationNamesLabel = new JLabel("Enter Location Names: ");
            locationNamesLabel.setFont(smallThemeFont);
            locationNamesLabel.setForeground(turqTheme);
            locationNamesLabel.setBounds(30,35,200,25);
            //locationPanel.add(locationNamesLabel);

            //to 70

            //child panel for section
            JPanel locationEntryPanel = new JPanel();
            locationEntryPanel.setSize(500,100);
            locationEntryPanel.setBackground(darkTheme);

            JScrollPane scroll = new JScrollPane(locationEntryPanel);
            scroll.setBounds(30,60,300,100); //to 160
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            Border scrollBorder = new LineBorder(turqTheme, 2);

            scroll.setBorder(scrollBorder);

            locationPanel.add(scroll);

            topLeft.add(locationPanel);

            //--------------------------------------------------------------------------------------------------------------
            //section 2

            //parent panel for section
            JPanel rangePanel = new JPanel();
            rangePanel.setLayout(null);
            rangePanel.setBackground(darkTheme);
            rangePanel.setBounds(0,yCoord,500,80); //runs to y = 215

            yCoord+= 90;

            //range label
            JLabel rangeLabel = new JLabel("Range of Analysis:");
            rangeLabel.setFont(themeFont);
            rangeLabel.setForeground(turqTheme);
            rangeLabel.setBounds(30,0,150,25);
            rangePanel.add(rangeLabel);

            //rangeButtons
            JRadioButton wholeRangeButton = new JRadioButton("Whole");
            wholeRangeButton.setFont(themeFont);
            wholeRangeButton.setForeground(turqTheme);
            JRadioButton specificRangeButton = new JRadioButton("Specific");
            specificRangeButton.setFont(themeFont);
            specificRangeButton.setForeground(turqTheme);
            wholeRangeButton.setBounds(180,0,100,25);
            specificRangeButton.setBounds(270,0,100,25);
            ButtonGroup rangeGroup = new ButtonGroup();
            rangeGroup.add(wholeRangeButton);
            rangeGroup.add(specificRangeButton);
            rangePanel.add(wholeRangeButton);
            rangePanel.add(specificRangeButton);

            JLabel startDateLabel = new JLabel("Start Date");
            startDateLabel.setFont(smallThemeFont);
            startDateLabel.setForeground(turqTheme);
            JLabel endDateLabel = new JLabel("End Date");
            endDateLabel.setFont(smallThemeFont);
            endDateLabel.setForeground(turqTheme);
            startDateLabel.setBounds(50,30,75,25);
            endDateLabel.setBounds(50,55,75,25);
            JTextField startDate = new JTextField();
            JTextField endDate = new JTextField();
            startDate.setBounds(135,30,100,25);
            endDate.setBounds(135,55,100,25);

            //startDate.setText("YYYY-MM-DD");
            //endDate.setText("YYYY-MM-DD");

            topLeft.add(rangePanel);

            //--------------------------------------------------------------------------------------------------------------
            //section 3

            //parent panel for section
            JPanel modePanel = new JPanel();
            modePanel.setLayout(null);
            modePanel.setBackground(darkTheme);
            modePanel.setBounds(0,yCoord,500,205); //runs to y = 410

            yCoord+=205;

            //label for analysis mode
            JLabel modeLabel = new JLabel("Analysis Mode");
            modeLabel.setFont(themeFont);
            modeLabel.setForeground(turqTheme);
            modeLabel.setBounds(30,0,150,25);
            modePanel.add(modeLabel);

            //modeComboBox
            String [] modes = {"Standard","Statistical","Custom"};
            JComboBox modeComboBox = new JComboBox(modes);
            modeComboBox.setBounds(160,0, 115,25);
            modeComboBox.setSelectedIndex(0);
            modePanel.add(modeComboBox);


            //checkboxes for custom analysis options
            JPanel checkBoxPanel = new JPanel();
            checkBoxPanel.setLayout(null);
            checkBoxPanel.setBackground(darkTheme);
            checkBoxPanel.setBounds(30,35,500,150);

            boxArray = new JCheckBox[12]; //12 is the number of checkboxes

            JCheckBox avgHighButton = new JCheckBox("Avg High");
            avgHighButton.setForeground(turqTheme);
            avgHighButton.setBounds(10,0,180,25);
            checkBoxPanel.add(avgHighButton);
            boxArray[0] = avgHighButton;
            JCheckBox avgLowButton = new JCheckBox("Avg Low");
            avgLowButton.setForeground(turqTheme);
            avgLowButton.setBounds(10,25,180,25);
            checkBoxPanel.add(avgLowButton);
            boxArray[1] = avgLowButton;
            JCheckBox avgVarianceButton = new JCheckBox("Avg Variance");
            avgVarianceButton.setForeground(turqTheme);
            avgVarianceButton.setBounds(10,50,180,25);
            checkBoxPanel.add(avgVarianceButton);
            boxArray[2] = avgVarianceButton;
            JCheckBox avgHumidityButton = new JCheckBox("Avg Humidity");
            avgHumidityButton.setForeground(turqTheme);
            avgHumidityButton.setBounds(10,75,180,25);
            checkBoxPanel.add(avgHumidityButton);
            boxArray[3] = avgHumidityButton;
            JCheckBox humidQtyBox = new JCheckBox("# Humid Days");
            humidQtyBox.setForeground(turqTheme);
            humidQtyBox.setBounds(10,100,180,25);
            checkBoxPanel.add(humidQtyBox);
            boxArray[4] = humidQtyBox;
            JCheckBox totalPrecipBox = new JCheckBox("Total Precipitation");
            totalPrecipBox.setForeground(turqTheme);
            totalPrecipBox.setBounds(10,125,180,25);
            checkBoxPanel.add(totalPrecipBox);
            boxArray[5] = totalPrecipBox;
            JCheckBox rainyDaysBox = new JCheckBox("# Rainy Days");
            rainyDaysBox.setForeground(turqTheme);
            rainyDaysBox.setBounds(200,0,180,25);
            checkBoxPanel.add(rainyDaysBox);
            boxArray[6] = rainyDaysBox;
            JCheckBox avgCloudButton = new JCheckBox("Avg Cloud Cover");
            avgCloudButton.setForeground(turqTheme);
            avgCloudButton.setBounds(200,25,180,25);
            checkBoxPanel.add(avgCloudButton);
            boxArray[7] = avgCloudButton;
            JCheckBox cloudyQtyBox = new JCheckBox("# Cloudy Days");
            cloudyQtyBox.setForeground(turqTheme);
            cloudyQtyBox.setBounds(200,50,180,25);
            checkBoxPanel.add(cloudyQtyBox);
            boxArray[8] = cloudyQtyBox;

            //first of three checkboxes requiring further input
            JCheckBox daysAboveBox = new JCheckBox("# Days Above (high)"); //add a combobox
            daysAboveBox.setForeground(turqTheme);
            daysAboveBox.setBounds(200,75,180,25);
            checkBoxPanel.add(daysAboveBox);
            boxArray[9] = daysAboveBox;
            JTextField highXVal = new JTextField();
            highXVal.setBounds(380,75,50,25);
            highXVal.setFocusable(false);
            checkBoxPanel.add(highXVal);


            JCheckBox daysBelowBox = new JCheckBox("# Days Below (low)"); //add a combobox
            daysBelowBox.setForeground(turqTheme);
            daysBelowBox.setBounds(200,100,200,25);
            checkBoxPanel.add(daysBelowBox);
            boxArray[10] = daysBelowBox;
            JTextField lowXVal = new JTextField();
            lowXVal.setBounds(380,100,50,25);
            lowXVal.setFocusable(false);
            checkBoxPanel.add(lowXVal);

            JCheckBox cloudCoverAboveBox = new JCheckBox("# Days Cloud Cover >"); //add a combobox
            cloudCoverAboveBox.setForeground(turqTheme);
            cloudCoverAboveBox.setBounds(200,125,200,25);
            checkBoxPanel.add(cloudCoverAboveBox);
            boxArray[11] = cloudCoverAboveBox;
            JTextField cloudXVal = new JTextField();
            cloudXVal.setBounds(380,125,50,25);
            cloudXVal.setFocusable(false);
            checkBoxPanel.add(cloudXVal);

            topLeft.add(modePanel);

            //--------------------------------------------------------------------------------------------------------------
            //section 4

            //parent panel for section
            JPanel goPanel = new JPanel();
            goPanel.setLayout(null);
            goPanel.setBackground(turqTheme);
            goPanel.setBounds(0, yCoord,500,60);

            yCoord+= 60;

            JButton goButton = new JButton("Go");
            goButton.setBounds(225,10,50,35);
            goButton.setFont(themeFont);
            goPanel.add(goButton);

            JButton helpButton = new JButton("?");
            helpButton.setBounds(10,10,35,35);
            helpButton.setFont(themeFont);
            goPanel.add(helpButton);

            topLeft.add(goPanel);
            //--------------------------------------------------------------------------------------------------------------

            frame.add(topLeft);
            frame.setVisible(true);

            //--------------------------------------------------------------------------------------------------------------

            //ActionListener controls button-induced processes
            ActionListener buttonListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){

                    //------------------------------------------------------------------------------------------------------
                    //numberComboBox | results adapt based on user number selection
                    if(e.getSource() == numberComboBox){

                        locationPanel.remove(locationEntryPanel);
                        locationPanel.add(locationNamesLabel);
                        locationPanel.repaint();
                        locationPanel.revalidate();

                        locationEntryPanel.removeAll();

                        int num = numberComboBox.getSelectedIndex()+1;

                        int rowNum;
                        if(num%2 == 0){
                            rowNum = num/2;
                        } else{
                            rowNum = num/2 + num%2;
                        }

                        locationEntryPanel.setLayout(new GridLayout(rowNum, 2, 0,10));

                        locationText = new JTextField[num];


                        int labelNum = 1; //label number

                        for(int i = 0; i < num; i++){
                            //column 1
                            JPanel panel = new JPanel();
                            panel.setBackground(darkTheme);
                            panel.setLayout(null);
                            panel.setPreferredSize(new Dimension(140,25));

                            JLabel numLabel = new JLabel(String.valueOf(labelNum));
                            numLabel.setPreferredSize(new Dimension(20,25));
                            numLabel.setBounds(5, 0, 20,25);
                            numLabel.setForeground(turqTheme);
                            panel.add(numLabel);
                            labelNum++;

                            JTextField field = new JTextField();
                            field.setPreferredSize(new Dimension(120,25));
                            field.setBounds(20,0, 120, 25);
                            panel.add(field);

                            locationEntryPanel.add(panel);
                            locationText[i] = field;


                            locationEntryPanel.repaint();
                            locationEntryPanel.revalidate();
                        }


                        //------------------------------------------------------------------------------------------------------
                        //specific range radioButton
                    } else if(e.getSource() == specificRangeButton){
                        rangePanel.add(startDateLabel);
                        rangePanel.add(endDateLabel);
                        rangePanel.add(startDate);
                        rangePanel.add(endDate);
                        rangePanel.revalidate();
                        rangePanel.repaint();
                        //------------------------------------------------------------------------------------------------------
                        //whole range radioButton
                    } else if(e.getSource() == wholeRangeButton){
                        rangePanel.remove(startDateLabel);
                        rangePanel.remove(endDateLabel);
                        rangePanel.remove(startDate);
                        rangePanel.remove(endDate);
                        rangePanel.revalidate();
                        rangePanel.repaint();
                        //----------------------------------------------------------------------------------------------------
                        //analysis mode comboBox
                    } else if(e.getSource() == modeComboBox) {
                        //number of selection
                        int num = (modeComboBox.getSelectedIndex() + 1);

                        //standard mode selected
                        if (num == 1) {
                            modePanel.remove(checkBoxPanel);
                            topLeft.revalidate();
                            topLeft.repaint();

                            //statistical mode selected
                        } else if (num == 2) {
                            modePanel.remove(checkBoxPanel);
                            topLeft.revalidate();
                            topLeft.repaint();

                            //custom mode selected
                        } else if (num == 3) {

                            modePanel.add(checkBoxPanel);
                            topLeft.revalidate();
                            topLeft.repaint();
                        }

                        //----------------------------------------------------------------------------------------------

                    } else if(e.getSource() == daysAboveBox) {
                        if(daysAboveBox.isSelected()){
                            highXVal.setFocusable(true);
                        } else{
                            highXVal.setFocusable(false);
                        }

                    } else if(e.getSource() == daysBelowBox) {
                        if(daysBelowBox.isSelected()){
                            lowXVal.setFocusable(true);
                        } else{
                            lowXVal.setFocusable(false);
                        }

                    } else if(e.getSource() == cloudCoverAboveBox){
                        if(cloudCoverAboveBox.isSelected()) {
                            cloudXVal.setFocusable(true);
                        } else{
                            cloudXVal.setFocusable(false);
                        }

                        //----------------------------------------------------------------------------------------------
                        //goButton
                    } else if(e.getSource() == goButton){

                        int errorCount = 0;
                        String [] errorMessages = new String[errorCount];

                        try {

                            int locationQty = (numberComboBox.getSelectedIndex() + 1);

                            //array that stores converted text from locationText array
                            String[] fileNames = new String[locationQty];

                            //assigns values to the above fileNames array
                            for(int i = 0; i < locationQty; i++){
                                fileNames[i] = "/Applications/Documents/TempestFiles/" + locationText[i].getText().toLowerCase() + "Weather.txt";
                                File readFile = new File(fileNames[i]);

                                //counts number of errors
                                if(!readFile.exists()){
                                    errorCount++;
                                }
                            }

                            //executes in the event that errors occur
                            if(errorCount > 0) {

                                errorMessages = new String[errorCount]; //array to store error messages(if present)

                                int errorMessagesIndex = 0; //counts index of above array ^

                                //second loop to assign values to errorMessages array
                                for (int i = 0; i < locationQty; i++) {
                                    fileNames[i] = "/Applications/Documents/TempestFiles/" + locationText[i].getText().toLowerCase() + "Weather.txt";
                                    File readFile = new File(fileNames[i]);

                                    if (!readFile.exists()) {
                                        errorMessages[errorMessagesIndex] = "File does not exist for location: \"" + (locationText[i].getText() + "\"");
                                        errorMessagesIndex++; //increments index pointer
                                    }

                                }

                                ReturnWindow window = new ReturnWindow(errorMessages); //window displaying error messages

                                //executes only if no outstanding errors are present in previous sections
                            } else{

                                //array of WeatherAnalysis objects to analyze the above files ^
                                WeatherAnalysis [] locations = new WeatherAnalysis[locationQty];


                                //assigns values to the above ^
                                for (int i = 0; i < locations.length; i++) {
                                    locations[i] = new WeatherAnalysis(fileNames[i]);
                                }

                                WeatherAnalysisArray weatherArray = new WeatherAnalysisArray(locations);

                                //----------------------------------------------------------------------------------------------
                                //whole range analysis table output options
                                if (wholeRangeButton.isSelected()) {

                                    //standard analysis chosen
                                    if (modeComboBox.getSelectedIndex() == 0) {

                                        JTable table = weatherArray.standardTableGen();
                                        ReturnWindow window = new ReturnWindow(table);


                                        //statistical analysis chosen
                                    } else if (modeComboBox.getSelectedIndex() == 1) {

                                        JTable table = weatherArray.statTableGen();
                                        ReturnWindow window = new ReturnWindow(table);

                                        //custom analysis chosen
                                    } else if (modeComboBox.getSelectedIndex() == 2) {

                                        //array to hold user's custom analysis choices
                                        int[] choiceArray = new int[boxArray.length + 3];

                                        //assigns values to above ^
                                        for (int i = 0; i < choiceArray.length - 3; i++) {
                                            if (boxArray[i].isSelected()) { //selected = 1
                                                choiceArray[i] = 1;
                                            } else {
                                                choiceArray[i] = 0; //not selected = 0
                                            }

                                        }

                                        errorCount = 0; //resets errorCount

                                        //daysAbove option
                                        if(daysAboveBox.isSelected()) {
                                            if (WeatherAnalysis.isNum(highXVal.getText())) {
                                                choiceArray[12] = Integer.parseInt(highXVal.getText());
                                            } else {
                                                errorCount++;
                                            }
                                        }

                                        //daysBelow option
                                        if(daysBelowBox.isSelected()){
                                            if(WeatherAnalysis.isNum(lowXVal.getText())){
                                                choiceArray[13] = Integer.parseInt(lowXVal.getText());
                                            } else{
                                                errorCount++;
                                            }
                                        }

                                        //cloudCoverAbove option
                                        if(cloudCoverAboveBox.isSelected()) {
                                            if (WeatherAnalysis.isNum(cloudXVal.getText())) {
                                                choiceArray[14] = Integer.parseInt(cloudXVal.getText());
                                            } else {
                                                errorCount++;
                                            }
                                        }

                                        if(errorCount > 0){
                                            String [] errors = new String[errorCount];
                                            int errorIndexes = 0;

                                            if(daysAboveBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(highXVal.getText())) {
                                                    choiceArray[12] = Integer.parseInt(highXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (high): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            if(daysBelowBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(lowXVal.getText())) {
                                                    choiceArray[13] = Integer.parseInt(lowXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (low): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            if(cloudCoverAboveBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(cloudXVal.getText())) {
                                                    choiceArray[14] = Integer.parseInt(cloudXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (cloud cover): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            ReturnWindow errorWindow = new ReturnWindow(errors);

                                            //executes if no errors are present
                                        } else{
                                            JTable table = weatherArray.customTableGen(choiceArray);
                                            ReturnWindow window = new ReturnWindow(table);
                                        }


                                    }

                                    //-------------------------------------------------------------------------------------------
                                    //specific range
                                } else if (specificRangeButton.isSelected()) {

                                    //standard analysis chosen
                                    if (modeComboBox.getSelectedIndex() == 0) {
                                        JTable table = weatherArray.standardTableGen(startDate.getText(), endDate.getText());
                                        ReturnWindow window = new ReturnWindow(table);

                                        //statistical analysis chosen
                                    } else if (modeComboBox.getSelectedIndex() == 1) {
                                        JTable table = weatherArray.statTableGen(startDate.getText(), endDate.getText());
                                        ReturnWindow window = new ReturnWindow(table);

                                        //custom analysis chosen
                                    } else if (modeComboBox.getSelectedIndex() == 2) {

                                        //array to hold user's custom analysis choices
                                        int[] choiceArray = new int[boxArray.length + 3];

                                        //assigns values to above ^
                                        for (int i = 0; i < choiceArray.length - 3; i++) {
                                            if (boxArray[i].isSelected()) { //selected = 1
                                                choiceArray[i] = 1;
                                            } else {
                                                choiceArray[i] = 0; //not selected = 0
                                            }

                                        }

                                        errorCount = 0; //resets errorCount

                                        //daysAbove option
                                        if(daysAboveBox.isSelected()) {
                                            if (WeatherAnalysis.isNum(highXVal.getText())) {
                                                choiceArray[12] = Integer.parseInt(highXVal.getText());
                                            } else {
                                                errorCount++;
                                            }
                                        }

                                        //daysBelow option
                                        if(daysBelowBox.isSelected()){
                                            if(WeatherAnalysis.isNum(lowXVal.getText())){
                                                choiceArray[13] = Integer.parseInt(lowXVal.getText());
                                            } else{
                                                errorCount++;
                                            }
                                        }

                                        //cloudCoverAbove option
                                        if(cloudCoverAboveBox.isSelected()) {
                                            if (WeatherAnalysis.isNum(cloudXVal.getText())) {
                                                choiceArray[14] = Integer.parseInt(cloudXVal.getText());
                                            } else {
                                                errorCount++;
                                            }
                                        }

                                        if(errorCount > 0){
                                            String [] errors = new String[errorCount];
                                            int errorIndexes = 0;

                                            if(daysAboveBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(highXVal.getText())) {
                                                    choiceArray[12] = Integer.parseInt(highXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (high): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            if(daysBelowBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(lowXVal.getText())) {
                                                    choiceArray[13] = Integer.parseInt(lowXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (low): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            if(cloudCoverAboveBox.isSelected()) {
                                                if (WeatherAnalysis.isNum(cloudXVal.getText())) {
                                                    choiceArray[14] = Integer.parseInt(cloudXVal.getText());
                                                } else {
                                                    errors[errorIndexes] = "Error (cloud cover): Numeric values only!";
                                                    errorIndexes++;
                                                }
                                            }

                                            ReturnWindow errorWindow = new ReturnWindow(errors);

                                            //executes if no errors are present
                                        } else{
                                            JTable table = weatherArray.customTableGen(startDate.getText(), endDate.getText(),choiceArray);
                                            ReturnWindow window = new ReturnWindow(table);
                                        }
                                    }
                                }
                                //----------------------------------------------------------------------------------------------

                            } //end of if-statement depending on errorCount

                            //add panels for each if / else if, then add these panels to the new window at the end of goButton
                        } catch (Exception x){
                            ReturnWindow errorWindow = new ReturnWindow(x.getMessage()); //catches any unanticipated errors
                        }

                    } else if(e.getSource() == helpButton){

                        String message = "These are the parementers of analysis.\n" +
                                "Once the go button is pressed, a table displaying\n" +
                                "the selected data will display for each location.\n\n" +
                                "1. Select number of locations to analyze\n" +
                                "2. Enter location names\n" +
                                "3. Select the range of dates to analyze\n" +
                                "4. Select the type of analysis to perform\n" +
                                "5. Run the analysis";

                        JTextArea helpMessage = new JTextArea(message);

                        ReturnWindow helpWindow = new ReturnWindow(helpMessage);
                    }
                    //------------------------------------------------------------------------------------------------------

                }
            };


            //main buttons
            numberComboBox.addActionListener(buttonListener);
            wholeRangeButton.addActionListener(buttonListener);
            specificRangeButton.addActionListener(buttonListener);
            modeComboBox.addActionListener(buttonListener);
            goButton.addActionListener(buttonListener);
            helpButton.addActionListener(buttonListener);

            //checkBoxes
            avgHighButton.addActionListener(buttonListener);
            avgLowButton.addActionListener(buttonListener);
            avgVarianceButton.addActionListener(buttonListener);
            avgHumidityButton.addActionListener(buttonListener);
            humidQtyBox.addActionListener(buttonListener);
            totalPrecipBox.addActionListener(buttonListener);
            rainyDaysBox.addActionListener(buttonListener);
            avgCloudButton.addActionListener(buttonListener);
            cloudyQtyBox.addActionListener(buttonListener);
            daysAboveBox.addActionListener(buttonListener);
            daysBelowBox.addActionListener(buttonListener);
            cloudCoverAboveBox.addActionListener(buttonListener);

            //——————————————————————————————————————————————————————————————————————————————————————————————————————————

        //graph page
        } else if(choice == 3){
            int height = 420;
            int width = 400;
            frame.setTitle("Graph Parameters");
            frame.setSize(width, height);
            frame.getContentPane().setBackground(darkTheme);
            frame.setLayout(null);
            //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //----------------------------------------------------------------------------------------------------------
            //parent panel for frame
            JPanel topLeft = new JPanel();
            topLeft.setLayout(null);
            topLeft.setBounds(0,0,width,height);
            topLeft.setBackground(darkTheme);
            //----------------------------------------------------------------------------------------------------------
            //section 1

            int xCoord = 0;
            int yCoord = 0;

            //parent panel for location section
            JPanel locationPanel = new JPanel();
            locationPanel.setLayout(null);
            locationPanel.setBackground(darkTheme);
            locationPanel.setBounds(xCoord,yCoord,width,170); //runs to y = 140

            yCoord+=170;
            //to 170


            JLabel label = new JLabel();
            label.setText("Number of Locations");
            label.setFont(themeFont);
            label.setForeground(turqTheme);
            label.setBounds(30,10,200,25); //to 35
            locationPanel.add(label);

            //top left comboBox
            String [] choices = {"1","2","3","4","5","6","7","8","9","10"};
            JComboBox numberComboBox = new JComboBox(choices);
            numberComboBox.setBounds(230,10,75,25); //to 35
            locationPanel.add(numberComboBox);


            JLabel locationNamesLabel = new JLabel("Enter Location Names: ");
            locationNamesLabel.setFont(smallThemeFont);
            locationNamesLabel.setForeground(turqTheme);
            locationNamesLabel.setBounds(30,35,200,25);


            //child panel for section
            JPanel locationEntryPanel = new JPanel();
            locationEntryPanel.setSize(500,100);
            locationEntryPanel.setBackground(darkTheme);

            JScrollPane scroll = new JScrollPane(locationEntryPanel);
            scroll.setBounds(30,60,300,100); //to 160
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            Border scrollBorder = new LineBorder(turqTheme, 2);

            scroll.setBorder(scrollBorder);

            locationPanel.add(scroll);

            topLeft.add(locationPanel);

            //--------------------------------------------------------------------------------------------------------------
            //section 2

            //parent panel for section
            JPanel rangePanel = new JPanel();
            rangePanel.setLayout(null);
            rangePanel.setBackground(darkTheme);
            rangePanel.setBounds(0,yCoord,width,80);

            yCoord+= 80;
            //up to 250

            //range label
            JLabel rangeLabel = new JLabel("Range of Analysis:");
            rangeLabel.setFont(themeFont);
            rangeLabel.setForeground(turqTheme);
            rangeLabel.setBounds(30,0,150,25);
            rangePanel.add(rangeLabel);

            //rangeButtons
            JRadioButton wholeRangeButton = new JRadioButton("Whole");
            wholeRangeButton.setFont(themeFont);
            wholeRangeButton.setForeground(turqTheme);
            JRadioButton specificRangeButton = new JRadioButton("Specific");
            specificRangeButton.setFont(themeFont);
            specificRangeButton.setForeground(turqTheme);
            wholeRangeButton.setBounds(180,0,100,25);
            specificRangeButton.setBounds(270,0,100,25);
            ButtonGroup rangeGroup = new ButtonGroup();
            rangeGroup.add(wholeRangeButton);
            rangeGroup.add(specificRangeButton);
            rangePanel.add(wholeRangeButton);
            rangePanel.add(specificRangeButton);

            JLabel startDateLabel = new JLabel("Start Date");
            startDateLabel.setFont(smallThemeFont);
            startDateLabel.setForeground(turqTheme);
            JLabel endDateLabel = new JLabel("End Date");
            endDateLabel.setFont(smallThemeFont);
            endDateLabel.setForeground(turqTheme);
            startDateLabel.setBounds(50,30,75,25);
            endDateLabel.setBounds(50,55,75,25);
            JTextField startDate = new JTextField();
            JTextField endDate = new JTextField();
            startDate.setBounds(135,30,100,25);
            endDate.setBounds(135,55,100,25);

            //startDate.setText("YYYY-MM-DD");
            //endDate.setText("YYYY-MM-DD");

            topLeft.add(rangePanel);

            //--------------------------------------------------------------------------------------------------------------
            //checkboxes for custom analysis options
            JPanel checkBoxPanel = new JPanel();
            checkBoxPanel.setLayout(null);
            checkBoxPanel.setBackground(darkTheme);
            checkBoxPanel.setBounds(30,yCoord,width,90);

            yCoord+=90;
            //up to 340

            //short-range graph analysis

            int boxCount = 0; //variable to count the number of checkboxes

            JCheckBox highBox = new JCheckBox("Highs");
            highBox.setForeground(turqTheme);
            highBox.setBounds(10,0,180,25);
            checkBoxPanel.add(highBox);
            boxCount++;

            JCheckBox lowBox = new JCheckBox("Lows");
            lowBox.setForeground(turqTheme);
            lowBox.setBounds(10,25,180,25);
            checkBoxPanel.add(lowBox);
            boxCount++;

            JCheckBox varianceBox = new JCheckBox("Variances");
            varianceBox.setForeground(turqTheme);
            varianceBox.setBounds(10,50,180,25);
            checkBoxPanel.add(varianceBox);
            boxCount++;

            JCheckBox humidBox = new JCheckBox("Humidity");
            humidBox.setForeground(turqTheme);
            humidBox.setBounds(200,0,180,25);
            checkBoxPanel.add(humidBox);
            boxCount++;

            JCheckBox cloudBox = new JCheckBox("Cloud Cover");
            cloudBox.setForeground(turqTheme);
            cloudBox.setBounds(200,25,180,25);
            checkBoxPanel.add(cloudBox);
            boxCount++;

            boxArray = new JCheckBox[boxCount]; //instantiate boxArray
            boxArray[0] = highBox;
            boxArray[1] = lowBox;
            boxArray[2] = varianceBox;
            boxArray[3] = humidBox;
            boxArray[4] = cloudBox;


            topLeft.add(checkBoxPanel);


            //parent panel for section
            JPanel goPanel = new JPanel();
            goPanel.setLayout(null);
            goPanel.setBackground(turqTheme);
            goPanel.setBounds(0, yCoord,width,60);

            yCoord+= 60;
            //400

            JButton goButton = new JButton("Go");
            goButton.setBounds(175,10,50,35);
            goButton.setFont(themeFont);
            goPanel.add(goButton);

            JButton helpButton = new JButton("?");
            helpButton.setBounds(10,10,35,35);
            helpButton.setFont(themeFont);
            goPanel.add(helpButton);

            topLeft.add(goPanel);


            frame.add(topLeft);
            frame.setVisible(true);

            //actionListener for graph parameter page
            ActionListener buttonListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == numberComboBox){

                        locationPanel.remove(locationEntryPanel);
                        locationPanel.add(locationNamesLabel);
                        locationPanel.repaint();
                        locationPanel.revalidate();

                        locationEntryPanel.removeAll();

                        int num = numberComboBox.getSelectedIndex()+1;

                        int rowNum;
                        if(num%2 == 0){
                            rowNum = num/2;
                        } else{
                            rowNum = num/2 + num%2;
                        }

                        locationEntryPanel.setLayout(new GridLayout(rowNum, 2, 0,10));

                        locationText = new JTextField[num];


                        int labelNum = 1; //label number

                        for(int i = 0; i < num; i++){
                            //column 1
                            JPanel panel = new JPanel();
                            panel.setBackground(darkTheme);
                            panel.setLayout(null);
                            panel.setPreferredSize(new Dimension(140,25));

                            JLabel numLabel = new JLabel(String.valueOf(labelNum));
                            numLabel.setPreferredSize(new Dimension(20,25));
                            numLabel.setBounds(5, 0, 20,25);
                            numLabel.setForeground(turqTheme);
                            panel.add(numLabel);
                            labelNum++;

                            JTextField field = new JTextField();
                            field.setPreferredSize(new Dimension(120,25));
                            field.setBounds(20,0, 120, 25);
                            panel.add(field);

                            locationEntryPanel.add(panel);
                            locationText[i] = field;


                            locationEntryPanel.repaint();
                            locationEntryPanel.revalidate();
                        }


                        //------------------------------------------------------------------------------------------------------
                        //specific range radioButton
                    } else if(e.getSource() == specificRangeButton){
                        rangePanel.add(startDateLabel);
                        rangePanel.add(endDateLabel);
                        rangePanel.add(startDate);
                        rangePanel.add(endDate);
                        rangePanel.revalidate();
                        rangePanel.repaint();
                        //------------------------------------------------------------------------------------------------------
                        //whole range radioButton
                    } else if(e.getSource() == wholeRangeButton) {
                        rangePanel.remove(startDateLabel);
                        rangePanel.remove(endDateLabel);
                        rangePanel.remove(startDate);
                        rangePanel.remove(endDate);
                        rangePanel.revalidate();
                        rangePanel.repaint();


                        //----------------------------------------------------------------------------------------------
                    } else if(e.getSource() == helpButton){
                        String message =

                                "This window takes parameters for weather\n" +
                                        "data tobe graphed\n" +

                                "\n1. Select the number of locations for\n" +
                                        "    which you wish to graph data\n" +

                                "\n2. Enter the names of these locations\n" +
                                "    in the entry boxes\n" +

                                "\n3. Choose the range of dates for which\n" +
                                        "   you wish to graph data\n" +
                                        "   Date format: YYYY-MM-DD\n" +

                                "\n4. Select the checkboxes corresponding\n" +
                                        "    to the data you wish to graph\n" +

                                "\n5. Pressing the \"Go\" button will launch a\n" +
                                        "    window displaying a line graph with the\n" +
                                        "    data you selected";

                        JTextArea helpMessage = new JTextArea(message);
                        ReturnWindow helpWindow = new ReturnWindow(helpMessage);


                        //----------------------------------------------------------------------------------------------

                    } else if (e.getSource() == goButton) {

                        int errorCount = 0;
                        String [] errorMessages = new String[errorCount];

                        try {

                            int locationQty = (numberComboBox.getSelectedIndex() + 1);

                            //array that stores converted text from locationText array
                            String[] fileNames = new String[locationQty];

                            //assigns values to the above fileNames array
                            for(int i = 0; i < locationQty; i++){
                                fileNames[i] = "/Applications/Documents/TempestFiles/" + locationText[i].getText().toLowerCase() + "Weather.txt";
                                File readFile = new File(fileNames[i]);

                                //counts number of errors
                                if(!readFile.exists()){
                                    errorCount++;
                                }
                            }

                            //executes in the event that errors occur
                            if(errorCount > 0) {

                                errorMessages = new String[errorCount]; //array to store error messages(if present)

                                int errorMessagesIndex = 0; //counts index of above array ^

                                //second loop to assign values to errorMessages array
                                for (int i = 0; i < locationQty; i++) {
                                    fileNames[i] = "/Applications/Documents/TempestFiles/" + locationText[i].getText().toLowerCase() + "Weather.txt";
                                    File readFile = new File(fileNames[i]);

                                    if (!readFile.exists()) {
                                        errorMessages[errorMessagesIndex] = "File does not exist for location: \"" + (locationText[i].getText() + "\"");
                                        errorMessagesIndex++; //increments index pointer
                                    }

                                }

                                ReturnWindow window = new ReturnWindow(errorMessages); //window displaying error messages

                                //executes only if no outstanding errors are present in previous sections

                            } else{

                                //array of WeatherAnalysis objects to analyze the above files ^
                                WeatherAnalysis [] locations = new WeatherAnalysis[locationQty];

                                //assigns values to the above ^
                                for (int i = 0; i < locations.length; i++) {
                                    locations[i] = new WeatherAnalysis(fileNames[i]);
                                }

                                //WeatherAnalysisArray object of above ^
                                WeatherAnalysisArray weatherArray = new WeatherAnalysisArray(locations);


                                //----------------------------------------------------------------------------------------------
                                //whole range analysis graph output options
                                if (wholeRangeButton.isSelected()) {

                                    int choiceCount = 0; //variable to store number of boxes checked
                                    for(int i = 0; i < boxArray.length; i++){
                                        if(boxArray[i].isSelected()){
                                            choiceCount++;
                                        }
                                    }


                                    //array of user checkbox choices
                                    int [] choiceArray = new int[choiceCount]; //stores user's checkbox choices
                                    int choiceIndex = 0; //tracks boxArray index
                                    for(int i = 0; i < boxArray.length; i++){
                                        if(boxArray[i].isSelected()){
                                            choiceArray[choiceIndex] = i;
                                            choiceIndex++;
                                        }

                                    }

                                    /*
                                    three-dimensional array for graph data
                                    level 1: locations
                                    level 2: choice array for each location
                                    level 3: data for each choice for each location
                                     */

                                    GraphArray gArray = new GraphArray(locations, choiceArray);

                                    Graph g = new Graph(gArray); //new graph


                                    //-------------------------------------------------------------------------------------------
                                    //specific range
                                } else if (specificRangeButton.isSelected()) {

                                    int choiceCount = 0; //variable to store number of boxes checked
                                    for(int i = 0; i < boxArray.length; i++){
                                        if(boxArray[i].isSelected()){
                                            choiceCount++;
                                        }
                                    }


                                    //array of user checkbox choices
                                    int [] choiceArray = new int[choiceCount]; //stores user's checkbox choices
                                    int choiceIndex = 0; //tracks boxArray index
                                    for(int i = 0; i < boxArray.length; i++){
                                        if(boxArray[i].isSelected()){
                                            choiceArray[choiceIndex] = i;
                                            choiceIndex++;
                                        }

                                    }

                                    //creates new GraphArray object with domain restrictions
                                    GraphArray gArray = new GraphArray(locations, choiceArray,
                                            startDate.getText(), endDate.getText());

                                    //generates a graph based on the above object ^
                                    Graph g = new Graph(gArray); //new graph ^

                                }
                                //----------------------------------------------------------------------------------------------

                            } //end of if-statement depending on errorCount

                            //add panels for each if / else if, then add these panels to the new window at the end of goButton
                        } catch (Exception x){
                            ReturnWindow errorWindow = new ReturnWindow(x.getMessage()); //catches any unanticipated errors
                        }


                    }

                }
            };

            goButton.addActionListener(buttonListener);
            helpButton.addActionListener(buttonListener);

            //main buttons
            numberComboBox.addActionListener(buttonListener);
            wholeRangeButton.addActionListener(buttonListener);
            specificRangeButton.addActionListener(buttonListener);

            //checkBoxes
            highBox.addActionListener(buttonListener);
            lowBox.addActionListener(buttonListener);
            varianceBox.addActionListener(buttonListener);
            humidBox.addActionListener(buttonListener);
            cloudBox.addActionListener(buttonListener);


        }


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
    //method to check if a string contains non-numeric characters
    public static boolean isNum(String line){
        char [] lineArray = new char[line.length()];

        for(int i = 0; i < lineArray.length; i++){
            lineArray[i] = line.charAt(i);
        }

        char [] acceptableArray = {'0','1','2','3','4','5','6','7','8','9','.','-'};

        int periodCount = 0;
        boolean check = false;
        for(int i = 0; i < lineArray.length; i++){
            for(int j = 0; j < acceptableArray.length; j++){
                if(lineArray[i] == acceptableArray[j]) {
                    check = true;
                    if(lineArray[i] == '.'){
                        periodCount++;
                    }
                }
            }
        }

        if(periodCount > 1){
            check = false;
        }

        return check;
    }

    //method to access location names
    public String [] getLocationNames(){
        String [] locationNames = new String[locationText.length];
        for(int i = 0; i < locationText.length; i++){
            locationNames[i] = toTitle(locationText[i].getText());
        }
        return locationNames;
    }


}