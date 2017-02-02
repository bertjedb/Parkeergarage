package Model;

/**
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */


import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
	private static final Color COLOR=Color.green;
	
	//Constructor AdHocCar
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 1 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    
    //@return color
    public Color getColor(){
    	return COLOR;
    }
}
