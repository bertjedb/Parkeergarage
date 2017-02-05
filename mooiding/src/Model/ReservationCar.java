package Model;

/**
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
	private static final Color COLOR=Color.black;
	
	//Constructor AbonnementCar
    public ReservationCar() {
    	Random random = new Random();	//Nieuwe random
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);	//stayMinutes wordt hier berekend.
        this.setMinutesLeft(stayMinutes);	//SetMinutesLeft wordt gereset doormiddel van stayMinutes.
        this.setHasToPay(false);	//HasToPay wordt op false gezet.
    }
    
    //@return Color
    public Color getColor(){
    	return COLOR;
    }
}
