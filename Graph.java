/*
Nathan Dann August 2025

The Graph class accepts a three-dimensional array as a parameter and generates a custom line graph based on the data
contained within. The first level of the array serves as each separate data entity (location in this case), the second
level houses what is being graphed (analysis choices [high/low/variance, etc.]), the third level houses the data sets
themselves (the points that will be plotted);

The graph is drawn on a JPanel by overriding the paintComponent of the object and using x and y coordinates like a grid
to plot points and draw lines between them.

Using a three-dimensional array, in theory, allows a user to graph multiple analysis options for multiple locations
(eg. plotting highs, lows, humidity values, and cloud values for an infinite amount of locations. Although this could
have been hedged against by allowing only one analysis option to be graphed at a time, the author wanted to allow
multiple options to be graphed for a single location (eg. plotting both cloud cover and humidity for one location) in
order to analyze relationships between these data.

The bottom of the panel housing the graph will be painted with a color-coded key linking locations to line color. The
line colors will automatically alternate. If an unreasonable amount of locations are entered, the key will overflow
off the page. It will accept at least half a dozen locations with names that are of normal length.


* add mean line option
* x-axis values

 */
//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————

public class Graph extends JPanel{

    final Color darkTheme = new Color(0x242120);

    //the plotted dataset
    private final int [][][] datasetY;

    //master object for graph
    private final GraphArray masterObject;

    private int xScale = 5;

    private boolean meanLines = false;

    //String [] datasetX; // = {"Jan","Feb","Mar","Apr,","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    //constructor for Graph
    Graph(GraphArray gArray) {

        //used for all plotted data and locations in graph
        masterObject = gArray;

        //assigns datasetY values
        datasetY = gArray.getGraphArray();

        //height of panel housing graph (graph height + 80 for padding)
        int panelHeight = ((maxArray(datasetY) - minArray(datasetY)) * 3) + 80;


        //frame that serves as container for Graph
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.getContentPane().setBackground(darkTheme);
        frame.setTitle("Graph");


        this.setLayout(null); //sets layout of Graph panel to null


        //largest dataset in three-dimensional array
        int maxElement = findLargestElement(datasetY);
        int plotWidth = getPlotWidth(maxElement);


        //sets panel and frame dimensions
        this.setBounds(20, 20, maxElement * plotWidth + 40, panelHeight);
        frame.setSize(this.getWidth() + 40, this.getHeight() + 90);

        JButton meanButton = new JButton("Mean");
        meanButton.setBounds(20, this.getHeight() + 50 - 20, 75, 25);
        frame.add(meanButton);


        frame.add(this);
        frame.setVisible(true);
    }

    //------------------------------------------------------------------------------------------------------------------
    //override of panel's paintComponent to achieve graph

    @Override
    protected void paintComponent(Graphics g) {

        //g2d for line
        Graphics2D g2d = (Graphics2D) g; //cast graphics to Graphics2D for enhanced features
        BasicStroke axisStroke = new BasicStroke(2.0f);
        g2d.setStroke(axisStroke); //sets line width based on above stroke ^
        g2d.setPaint(Color.black); //sets paint color for grid and axes


        super.paintComponent(g2d); //applies g2d to the Graph panel (super)


        //used to set y-axis bounds
        int minVal = minArray(datasetY); //starting point for y-axis bounds (smallest item in arrays)
        int maxVal = maxArray(datasetY); //end point for y-axis bounds (largest item in arrays)

        int plotWidth = getPlotWidth(findLargestElement(datasetY)); //calculates horizontal distance between each point

        int gridHeight = (maxVal - minVal)*3; //height of the graph area (each value of ten is scaled X3)

        int initialX = 40; //x=0 (padded by 40 to allow space for y-axis labels)
        int initialY = gridHeight + 30; //y = 0 (padded by 30)

        this.setBackground(Color.GRAY);


        //--------------------------------------------------------------------------------------------------------------
        //preliminary graph setup (axes and horizontal gridlines)

        //Y-axis labels (Y-scale 1:3)
        int yMultiplier = 0; //the index value for each count-by-ten y-grid value
        for (int i = minVal; i <= maxVal; i += 10) {
            int yVal = initialY - (yMultiplier * 30); //y=0 - index value * 30 (the space between each y label); moves up
            g2d.drawString(String.valueOf(i), 10, yVal + 5); //yVal+5 centers y-labels
            g2d.drawLine(initialX, yVal, this.getWidth() - plotWidth, yVal); //horizontal grid
            yMultiplier++; //increments y-multiplier to move horizontal grid lines up with each loop cycle

            //draws y-axis and y-boundary on last loop cycle (so that y values are at their maximum)
            if(i == maxVal){
                g2d.drawLine(initialX, initialY, initialX, yVal); //y-axis
                g2d.drawLine(this.getWidth()-plotWidth, initialY, this.getWidth()-plotWidth, yVal); //y-boundary
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        //plotting points and graph drawing

        //spacing for key tags
        int xKeyVal = 40; //x-position for color-coded key tags (changes)
        int yKeyVal = initialY+40; //y-position for color-coded key tags (does not change)

        int xScale = 5; //count-by value for x-axis labels //changeable count-by x-axis label tag

        String [] locationNames = masterObject.getLocations(); //gets location names from masterObject


        //level 1: location
        for (int i = 0; i < datasetY.length; i++) {

            //line color assignments
            if((i+1) % 5 == 0){ //fifth location
                g2d.setPaint(new Color(0xFF8912)); //orange
            } else if((i+1) % 4 == 0){ //fourth location
                g2d.setPaint(new Color(0xFFF712)); //yellow
            } else if((i+1) % 3 == 0){ //third location
                g2d.setPaint(new Color(0xFF1212)); //red
            } else if((i+1) % 2 == 0){ //second location
                g2d.setPaint(new Color(0x12FF49));
            } else { //first location
                g2d.setPaint(new Color(0xFF12FF));
            }


            //key labels
            g2d.fillRect(xKeyVal, yKeyVal-10, 10, 10);
            xKeyVal+=20;
            g2d.drawString(locationNames[i], xKeyVal, yKeyVal);
            xKeyVal+=100;


            //level 2: data choices for each location
            for(int j = 0; j < datasetY[i].length; j++) {

                int oldX = 0; //placeholder for old x value (starting point of line)
                int oldY = 0; //placeholder for old y value (starting point of line)
                int incrementX = 0; //horizontal space between each successive point

                //level 3: data for each choice for each location
                for (int z = 0; z < datasetY[i][j].length; z++) {
                    int x = 40 + incrementX; //40-3 = 37; adjusts for point spacing;
                    int y = initialY - (datasetY[i][j][z] * 3) + minVal * 3; //y-coordinate
                    g2d.fillOval(x - 3, y - 3, 6, 6); //y-3 centers the plotted point
                    if (z > 0) {
                        g2d.drawLine(oldX, oldY, x, y); //shift x to center (y is centered above ^)
                    }


                    //values to draw lines between points
                    oldX = x; //placeholder for x1 coordinate
                    oldY = y; //placeholder for y1 coordinate
                    incrementX += plotWidth;


                    //executes only once for graph (x-axis hash marks)
                    if(i == 0) {
                        g2d.drawLine(x, initialY + 3, x, initialY - 3); //x-axis hashes for each data point
                        if(z == 0 || (z+1) % this.getxScale() == 0){ //remainder of index value / xScale
                            g2d.drawString(String.valueOf(z+1), x-3, initialY+20); //x-labels
                        }
                    }

                } //end of z loop

            } //end of j loop

        } //end of i loop
        //--------------------------------------------------------------------------------------------------------------

    } //end of paintComponent override

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for xScale
    public void setxScale(int val){
        xScale = val;
    }
    //------------------------------------------------------------------------------------------------------------------
    //accessor for xScale
    public int getxScale(){
        return xScale;
    }

    public int getMean(int i){
        int sum = 0;
        int count = 0;
        for(int j = 0; j < datasetY[i].length; j++){
            for(int z = 0; z < datasetY[i][j].length; z++){
                sum += datasetY[i][j][z];
                count++;
            }
        }
        return sum / count;
    }

    //———————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //utility methods

    /*
    Method to round integer values to the closest value of ten
    upDown = 0, rounds down, upDown = 1, rounds up
     */
    public static int roundTen(int val, int upDown){
        int returnVal;
        int remainder = val % 10;
        if(remainder == 0){
            returnVal = val;
        } else{
            if(upDown == 0){
                returnVal = val -remainder;
            } else{
                returnVal = val-remainder+10;
            }
        }
        return returnVal;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to find the maximum value in a three-dimensional array (rounds up to closest value of ten)
    public static int maxArray(int [][][] array){

        int max = roundTen(array[0][0][0],1);
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int z = 0; z < array[i][j].length; z++) {
                    if (roundTen(array[i][j][z], 1) > max) {
                        max = roundTen((array[i][j][z]), 1);
                    }
                }
            }
        }
        return max;
    }

    //------------------------------------------------------------------------------------------------------------------
    //method to find the minimum value in a three-dimensional array (rounds down to closest value of ten)
    public static int minArray(int [][][] array){

        int min = roundTen(array[0][0][0],1);
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                for (int z = 0; z < array[i][j].length; z++) {
                    if (roundTen(array[i][j][z], 0) < min) {
                        min = roundTen((array[i][j][z]), 0);
                    }
                }
            }
        }
        return min;
    }

    //------------------------------------------------------------------------------------------------------------------
    //finds largest element in array (third-level [1][2][3]) to determine plotWidth
    public static int findLargestElement(int [][][] array){
        int width = 0;

        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j].length > width) {
                    width = array[i][j].length;
                }
            }
        }
        return width;
    }

    //------------------------------------------------------------------------------------------------------------------

    //method to determine plotWidth (horizontal distance between each point) given a maximum z-length. [x][y][z]
    public static int getPlotWidth(int maxElement){
        int plotWidth = 0;

        if(maxElement <= 20){
            plotWidth = 50;

        } else if(maxElement <=50){
            plotWidth = 25;

        } else if(maxElement <= 100){
            plotWidth = 10;

        } else if(maxElement <=1000){
            plotWidth = 5;
        }
        return plotWidth;

    }

    //------------------------------------------------------------------------------------------------------------------

} //end of class Graph

//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————