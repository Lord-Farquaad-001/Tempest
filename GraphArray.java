/*
Nathan Dann August 2025

The GraphArray class is meant to interact with the Graph class. It accepts an array of WeatherAnalysis objects and an
integer array as parameters. It will then take the data it is given and convert it to a three-dimensional array that
can be graphed  using the Graph class.

 */

//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————

class GraphArray{

    //private variables for GraphArray class
    private WeatherAnalysis [] locations; //array of WeatherAnalysis objects
    private int [] choiceArray; //array of analysis choices
    private int [][][] graphArray; //array to graph


    //------------------------------------------------------------------------------------------------------------------

    //constructor for GraphArray without restricted domain
    GraphArray(WeatherAnalysis [] array, int [] choices) throws Exception{
        this.setLocations(array); //automatically sets locations
        this.setChoices(choices); //automatically sets choices
        this.setGraphArray(); //automatically sets graphArray

    }

    //------------------------------------------------------------------------------------------------------------------

    //constructor with restricted domain
    GraphArray(WeatherAnalysis [] array, int [] choices, String start, String end) throws Exception{
        this.setLocations(array);
        this.setChoices(choices);
        this.setGraphArray(start, end);
    }

    //------------------------------------------------------------------------------------------------------------------

    //set method for locations
    public void setLocations(WeatherAnalysis [] array){
        locations = array;
    }

    //------------------------------------------------------------------------------------------------------------------

    //get method for locations
    public String [] getLocations(){
        String [] locationNames = new String[locations.length];
        for(int i = 0; i < locations.length; i++){
            locationNames[i] = NewPage.toTitle(locations[i].getLocation());
        }
        return locationNames;
    }

    //------------------------------------------------------------------------------------------------------------------

    //set method for choices
    public void setChoices(int [] array){
        choiceArray = array;
    }

    //------------------------------------------------------------------------------------------------------------------

    //get method for choices
    public int [] getChoices(){
        return choiceArray;
    }

    //------------------------------------------------------------------------------------------------------------------

    //method to generate three-dimensional array with given data
    public void setGraphArray() throws Exception{

        graphArray = new int[locations.length][choiceArray.length][];

        /*
         three-dimensional array for graph data
         level 1: locations
         level 2: choice array for each location
         level 3: data for each choice for each location
         */

        //assigns data to above array ^
        for(int i = 0; i < locations.length; i++){
            for(int j = 0; j < choiceArray.length; j++){
                graphArray[i][j] = locations[i].getData(choiceArray[j]);
            }
        }
    }

    public void setGraphArray(String start, String end) throws Exception{

        graphArray = new int[locations.length][choiceArray.length][];


        /*
         three-dimensional array for graph data
         level 1: locations
         level 2: choice array for each location
         level 3: data for each choice for each location
         */

        //assigns data to above array ^
        for(int i = 0; i < locations.length; i++){
            start = WeatherAnalysis.intToDate(locations[i].roundStartToClosest(start));
            end = WeatherAnalysis.intToDate(locations[i].roundEndToClosest(end));
            for(int j = 0; j < choiceArray.length; j++){
                graphArray[i][j] = locations[i].getData(start, end, choiceArray[j]);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    //get method for graphArray
    public int [][][] getGraphArray(){
        return graphArray;
    }

    //------------------------------------------------------------------------------------------------------------------


} //end of class GraphArray
//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
