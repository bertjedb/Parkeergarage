package Model;


import java.awt.*;

/**
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    //@return location
    public Location getLocation() {
        return location;
    }
    
    //@location wordt gelijktgesteld aan this.location.
    public void setLocation(Location location) {
        this.location = location;
    }
    
    //@return minutesleft
    public int getMinutesLeft() {
        return minutesLeft;
    }

    //minutesLeft wordt gelijkgesteld aan this.minutesLeft.
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
   
    //@return isPaying
    public boolean getIsPaying() {
        return isPaying;
    }

    //isPaying wordt gelijkgesteld aan this.isPaying.
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    //@return hasToPay.
    public boolean getHasToPay() {
        return hasToPay;
    }

    //hasToPay wordt gelijkgesteld aan this.hasToPay
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    //minutesLeft -1
    public void tick() {
        minutesLeft--;
    }
    
    public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
}