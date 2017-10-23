
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class Ticket {
    String numberPlate;
    String timeCreated;
    String timeDeparted;
    long timeCreatedMillis;
    long timeDepartedMillis;
    int randomID;
    long cost;
    double hours;
    long time_diff;
    
    public Ticket() {
        // Empty constructor
    }
    
    public Ticket( String np, String tc, long millis ){
        this.numberPlate = np.toUpperCase();
        this.timeCreated = tc;
        this.timeCreatedMillis = millis;
        this.randomID = this.numberPlate.hashCode() + this.timeCreated.hashCode();
    }
    
    public Ticket( String np, String tc, long millis, int rid ){
        this.numberPlate = np.toUpperCase();
        this.timeCreated = tc;
        this.timeCreatedMillis = millis;
        this.randomID = rid;
    }
    
    public String toStringParked() {
        String result = String.format("<%s>: %s [ %s ] \t: PARKED", timeCreated, numberPlate, randomID );    
        return result;
    }
    
    public String toStringPicked() {
        String result = String.format("<%s>: %s [ %s ] \t: PICKED", timeDeparted, numberPlate, randomID );    
        return result;
    }
    
    public String getNumberPlate() {
        return numberPlate.toUpperCase();
    }
    
    public String getTimeCreated() {
        return timeCreated;
    }
    
    public String getTimeDeparted() {
        return timeDeparted;
    }
    
    public long getTimeCreatedMillis() {
        return timeCreatedMillis;
    }
    
    public long getTimeDepartedMillis() {
        return timeDepartedMillis;
    }
    
    public int getRandomID() {
        return randomID;
    }
    
    public long getCost() {
        return cost;
    }
    
    public void setTimeDeparture( String td ) {
        timeDeparted = td;
    }
    
    public void setTimeDepartureMillis( long tm ) {
        timeDepartedMillis = tm;
        
        calculateCost();
    }
    
    public void calculateCost() {
        time_diff = timeDepartedMillis - timeCreatedMillis;
        hours = (TimeUnit.MILLISECONDS.toHours(time_diff) % TimeUnit.DAYS.toHours(1));
        
        // I hr = 100, 2 hrs = 200 and so on
        // If less than 1, then it's 50
        // cost = hours * 100; 
        if(hours < 1){
            cost = 50;
        }else {
            cost = (int)hours * 100;
        }
    }
    
    @Override
    public String toString() {
        String result = String.format("%s [%s]\nArrival time : %s\nDeparture time : %s\nCost : sh.%s\nHours : %s\n\n", 
                numberPlate, randomID, timeCreated, timeDeparted, cost, hours );
        return result;
    }
}
