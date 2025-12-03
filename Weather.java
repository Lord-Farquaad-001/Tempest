/*
Nathan Dann
30 May 2025

The Weather class allows users to create Weather objects with the following attributes:

    * high (integer, temperature)
    * low (integer, temperature)
    * precipitation (double)
    * humid (integer, percentage)
    * cloudy (integer, percentage)

The purpose of this class is to allow users to create entries that track the weather of a particular day.
------------------------------------------------------------------------------------------------------------------------
 */

import java.time.LocalDate;

class Weather{ //start of class Weather

    //------------------------------------------------------------------------------------------------------------------
    //Section 1: Weather variables and constructors

    private int high; //day's high temperature, integer
    private int low; //day's low temperature, integer

    private double precipitation; //day's total precipitation (inches)

    private int humid; //day's average humidity (%)
    private int cloudCover; //day's average cloud cover (%)

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //default constructor for Weather objects
    public Weather(){
        high = 70;
        low = 50;
        precipitation = 0;
        humid = 0;
        cloudCover = 0;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //Weather constructor with argument parameters
    public Weather(int max, int min, double rain, int percentage, int nub){

        this.setHigh(Math.max(max, min));
        this.setLow(Math.min(max, min));
        this.setPrecipitation(rain);
        this.setHumid(percentage);
        this.setCloudCover(nub);

    }
    //------------------------------------------------------------------------------------------------------------------
    //Section 2: Weather get instance methods

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that automatically fills today's date
    public LocalDate getDate(){
        LocalDate today = LocalDate.now();
        return today;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that returns high temperature
    public int getHigh(){
        return high;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that returns low temperature
    public int getLow(){
        return low;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that returns precipitation T/F
    public double getPrecipitation(){
        return precipitation;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that returns humid T/F
    public int getHumid(){
        return humid;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method that returns cloudy T/F
    public int getCloudy(){
        return cloudCover;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //------------------------------------------------------------------------------------------------------------------
    //Section 3: Weather set instance methods

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for high
    public void setHigh(int val){
        if(high > 140 || high < -130){
            throw new IllegalArgumentException("Error (setHigh)\nValue outside of reasonable range!");
        } else {
            high = val;
        }
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for low
    public void setLow(int val){

        if(val < -130 || val > 140){
            throw new IllegalArgumentException("Error (setLow)\nValue outside of reasonalbe range!");
        } else {
            //if the user enters a low value that is greater than the high, these values are switched
            if (val > high) {
                high = val;
            } else {
                low = val;
            }
        }
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for precipitation
    public void setPrecipitation(double val){
        if(val > 72){
            throw new IllegalArgumentException("Error (setPrecipitation)\nValue outside of reasonable range!");
        } else {
            precipitation = val;
        }
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for humid
    public void setHumid(int val){
        if(val < 1 || val > 100){
            throw new IllegalArgumentException("Error (setHumid)\nValue outside of reasonable range!");
        }
        humid = val;
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //modifier for cloudy
    public void setCloudCover(int val){
        if(val < 0 || val > 100){
            throw new IllegalArgumentException("Error (setCloudCover)\nValue outside of reasonable range");
        } else {
            cloudCover = val;
        }
    }

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    //method to output object data in string format
    public String toString(){
        StringBuilder storage = new StringBuilder();

        storage.append(getDate() + "\n");
        storage.append("\tHigh: " + getHigh() + "\n");
        storage.append("\tLow: " + getLow() + "\n");
        storage.append(("\tPrecipitation: " + getPrecipitation()) + "\"\n");
        storage.append("\tHumidity: " + getHumid() + "%\n");
        storage.append("\tCloud Cover: " + getCloudy() + "%\n\n");

        String message = storage.toString();

        return message;

    } //end of method toString

    //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————

    //------------------------------------------------------------------------------------------------------------------

} //end of class Weather