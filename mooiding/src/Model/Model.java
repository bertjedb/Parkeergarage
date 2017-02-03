package Model;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.sun.glass.ui.View;

import View.SimulatorView;

/**
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */


public class Model implements Runnable{
	
	//Constants for cars
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String ABO = "3";
	//Instantie variabelen.
	public static Model simulator;
	private ArrayList<Car> aboList;
	private ArrayList<String> subList;
	private static QueueCars queueForPaying;
	private QueueCars queuePassEntrance;
	private QueueCars queueCarEntrance;
	private QueueCars queueAboEntrance;
    private QueueCars queueExit;
    public static int revenue = 0;
    public static int estimatedRevenue = 0;
    public static int amountOfCars = 0;
    private SimulatorView simulatorView;
    private static int queueLength = 0;
    
    private static int amountOfAdHoc = 0;
    private static int amountOfPass = 0;
    private static int amountOfAbo = 0;
    
    private int day = 0; 
    private int hour = 0;
    private int minute = 0;
    private int tickPause = 500;

    // Program running state
    private boolean run = false;
    
    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 80; // average number of arriving parking pass cars per hour
    int weekendPassArrivals = 5; // average number of arriving parking pass cars per hour
    int weekDayAboArrivals= 20; // average number of arriving abo cars per hour
    int weekendAboArrivals = 10; // average number of arriving abo cars per hour
    
    int enterSpeed = 1; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 1; // number of cars that can leave per minute
    
    public static void main(String[] args){
         simulator = new Model();
      }

    //Constructor voor Simulator Class
public Model() {
	queueCarEntrance = new QueueCars();
	queuePassEntrance = new QueueCars();
	queueAboEntrance = new QueueCars();
	queueForPaying = new QueueCars();
	queueExit = new QueueCars();
	//simulatorView = new SimulatorView(3, 6, 30, this); // declare a fourth one
	simulatorView = new SimulatorView(3, 6, 30); // declare a fourth one


}
 
//method for returning amount of revenue
public static int getAmountOfRevenue(){
	return revenue;
}

//method for returning amount of revenue
public static int getAmountOfAdHoc(){
	return amountOfAdHoc;
}
//method for returning amount of revenue
public static int getAmountOfPass(){
	return amountOfPass;
}
//method for returning amount of revenue
public static int getAmountOfAbo(){
	return amountOfAbo;
}


//method for returning estimated revenue
public static int getEstimatedRevenue(){
	return estimatedRevenue;
}

//method for returning amount of cars
public static int getAmountOfCars(){
	return amountOfCars;
}

//method for returning amount of cars
public static int getQueueLength(){
	return queueLength;
}

// Method for starting the program
public void startProgram(){
	if(!run){
	run();
	}
}


//Method to make one step in the program
public void oneStep(){
	for(int i = 0; i < 2; i++) {
        tick();
	}
}

//Method to make 100 steps in the program
public void step100(){
	for(int i = 0; i<100; i++) {
      tick();
	}
}
// Method for pausing the program
public void pauseProgram(){
	run = false;
}

// Method for quitting program
public void quitProgram(){
    System.exit(0);
}
//Method om programma te runnen
public void run() {           
	run = true;	
	for (int i = 0; i < 10000; i++) {
			if(run){
			tick();
	    }		
	}
}	

//Method stap vooruit in simulatie.
private void tick() {
	advanceTime();
	handleExit();
	updateViews();
//	View.SimulatorView.updateLabels();
	// Pause.
    try {
        Thread.sleep(tickPause);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
	handleEntrance();
}

public void advanceTime() {
    // Advance the time by one minute.    	
		minute++;
		while (minute > 59) {
			minute -= 60;
			hour++;
		}
		while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
	}
//Inrijden van garage
private void handleEntrance(){
	carsArriving();
	carsEntering(queuePassEntrance);
	carsEntering(queueCarEntrance);  
	carsEntering(queueAboEntrance); 
}

//Uitrijden van garage
private void handleExit(){
    carsReadyToLeave();
    carsPaying();
    carsLeaving();
}

//Update views
private void updateViews(){
	simulatorView.tick();
    // Update the car park view.
    simulatorView.updateView();	
}

//Aantal arriverende auto's. 
private void carsArriving(){
	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
    addArrivingCars(numberOfCars, AD_HOC);    	
	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
    addArrivingCars(numberOfCars, PASS);    	
    numberOfCars=getNumberOfCars(weekDayAboArrivals, weekendAboArrivals);
    addArrivingCars(numberOfCars, ABO);  
}

//Aantal auto's die garage binnen gaan.
private void carsEntering(QueueCars queue){
    int i=0;
    // Remove car from the front of the queue and assign to a parking space.
	while (queue.carsInQueue()>0 && 
			simulatorView.getNumberOfOpenSpots()>0 && 
			i<enterSpeed) {
        Car car = queue.removeCar();
        
        if(car.getColor() == Color.black){
        	Location subLocation = simulatorView.getFirstAboLocation();
        	simulatorView.setCarAt(subLocation, car);
        	queueLength--;
        	amountOfCars++;
        }
        Location freeLocation = simulatorView.getFirstFreeLocation();
        if(car.getColor() == Color.green || car.getColor() == Color.red){
        simulatorView.setCarAt(freeLocation, car);
            queueLength--;
            amountOfCars++;
            if(car.getColor() == Color.green){
            	estimatedRevenue += 20;
            	amountOfAdHoc++;
            }
            if(car.getColor() == Color.red){
            	estimatedRevenue += 10;
            	amountOfPass++;
            }
        }
        if(car.getColor() == Color.black){
        	amountOfAbo++;
        }
    
        i++;       
    }
}


//Auto's die willen vertrekken.
private void carsReadyToLeave(){
    // Add leaving cars to the payment queue.
    Car car = simulatorView.getFirstLeavingCar();
    while (car!=null) {
    	if (car.getHasToPay()){
            car.setIsPaying(true);
            queueForPaying.addCar(car);
    	}
    	else {
    		carLeavesSpot(car);
    	}
        car = simulatorView.getFirstLeavingCar();
    }
}

private void carsPaying(){
    // Let cars pay.
	int i=0;
	while (queueForPaying.carsInQueue()>0 && i < paymentSpeed){
        Car car = queueForPaying.removeCar();
        if(car.getColor() == Color.green){
        	revenue += 20;
        	estimatedRevenue -= 20;
        }
        if(car.getColor() == Color.red){
        	revenue += 10;
        	estimatedRevenue -= 10;
        }
        if(car.getColor() == Color.black){
        }
        carLeavesSpot(car);
        i++;
	  }
}    


private void carsLeaving(){
    // Let cars leave.
	int i=0;
	while (queueExit.carsInQueue()>0 && i < exitSpeed){
		queueExit.removeCar();
        i++;
        amountOfCars--;
        
	}	
}

private int getNumberOfCars(int weekDay, int weekend){
    Random random = new Random();

    // Get the average number of cars that arrive per hour.
    int averageNumberOfCarsPerHour = day < 5
            ? weekDay
            : weekend;

    // Calculate the number of cars that arrive this minute.
    double standardDeviation = averageNumberOfCarsPerHour * 1;
    double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
    return (int)Math.round(numberOfCarsPerHour / 60);	
}

private void addArrivingCars(int numberOfCars, String type){
    // Add the cars to the back of the queue.
	switch(type) {
	case AD_HOC: 
        for (int i = 0; i < numberOfCars; i++) {
        	queueCarEntrance.addCar(new AdHocCar());
        	queueLength++;
        }
        break;
	case PASS:
        for (int i = 0; i < numberOfCars; i++) {
        	queuePassEntrance.addCar(new ParkingPassCar());
        	queueLength++;
        }
        break;	     
	case ABO:
        for (int i = 0; i < numberOfCars; i++) {
//        	if(aboList.size() < 30){
//        		subList.add("1");
        		queueAboEntrance.addCar(new AbonnementCar());
        		queueLength++;
           	}
//        }
        break;
	}
}

//Auto verlaat parkeerplek.
private void carLeavesSpot(Car car){
	simulatorView.removeCarAt(car.getLocation());
    queueExit.addCar(car);
}
}	





