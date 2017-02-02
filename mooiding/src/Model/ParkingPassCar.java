package Model;


import java.util.Random;
import java.awt.*;

/**
 * 
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.red;
	
	/*
	 * Constructor van ParkingPassCar
	 */
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60 );
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
   //@return color
    public Color getColor(){
    	return COLOR;
    }
}
