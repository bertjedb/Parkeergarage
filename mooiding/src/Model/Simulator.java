package Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import View.SimulatorView;

public class Simulator{
	
	//Constants for cars
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String ABO = "3";
	
	public static Simulator simulator;
	private QueueCars queuePassEntrance;
	private QueueCars queueForPaying;
	private QueueCars queueCarEntrance;
    private QueueCars queueExit;
    

    private SimulatorView simulatorView;
  
    private int day = 0; 
    private int hour = 0;
    private int minute = 0;
    private int tickBreak = 100;

    // Program running state
    public static boolean programRunning = false;
    
    private ActionEvent event;
    
    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 1; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 1; // number of cars that can leave per minute
    
    public static void main(String[] args){
         simulator = new Simulator();
      }

public Simulator() {
	queueCarEntrance = new QueueCars();
	queuePassEntrance = new QueueCars();
	queueForPaying = new QueueCars();
	queueExit = new QueueCars();
	simulatorView = new SimulatorView(3, 6, 30, this); // declare a fourth one
}
 
// Method for starting the program
public void startProgram(){
	programRunning = true;
    		runProgram(50);    
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
	programRunning = false;
	
}

// Method for quitting program
public void quitProgram(){
	System.exit(0);
}

public void runProgram(int steps) {           
		for (int i = 0; i < steps; i++) {
			while(programRunning == true){
			tick();
			} 
			while(programRunning == false) {
		break;
		}
	}
}	


private void tick() {
	advanceTime();
	handleExit();
	updateViews();
	// Pause.
    try {
        Thread.sleep(tickBreak);
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
private void handleEntrance(){
	carsArriving();
	carsEntering(queuePassEntrance);
	carsEntering(queueCarEntrance);  	
}

private void handleExit(){
    carsReadyToLeave();
    carsPaying();
    carsLeaving();
}

private void updateViews(){
	simulatorView.tick();
    // Update the car park view.
    simulatorView.updateView();	
}

private void carsArriving(){
	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
    addArrivingCars(numberOfCars, AD_HOC);    	
	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
    addArrivingCars(numberOfCars, PASS);    	
    numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
    addArrivingCars(numberOfCars, ABO);  
}

private void carsEntering(QueueCars queue){
    int i=0;
    // Remove car from the front of the queue and assign to a parking space.
	while (queue.carsInQueue()>0 && 
			simulatorView.getNumberOfOpenSpots()>0 && 
			i<enterSpeed) {
        Car car = queue.removeCar();
        Location freeLocation = simulatorView.getFirstFreeLocation();
        simulatorView.setCarAt(freeLocation, car);
        i++;
    }
}

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
        // TODO Handle payment.
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
	}	
}

private int getNumberOfCars(int weekDay, int weekend){
    Random random = new Random();

    // Get the average number of cars that arrive per hour.
    int averageNumberOfCarsPerHour = day < 5
            ? weekDay
            : weekend;

    // Calculate the number of cars that arrive this minute.
    double standardDeviation = averageNumberOfCarsPerHour * 0.3;
    double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
    return (int)Math.round(numberOfCarsPerHour / 60);	
}

private void addArrivingCars(int numberOfCars, String type){
    // Add the cars to the back of the queue.
	switch(type) {
	case AD_HOC: 
        for (int i = 0; i < numberOfCars; i++) {
        	queueCarEntrance.addCar(new AdHocCar());
        }
        break;
	case PASS:
        for (int i = 0; i < numberOfCars; i++) {
        	queuePassEntrance.addCar(new ParkingPassCar());
        }
        break;	     
	case ABO:
        for (int i = 0; i < numberOfCars; i++) {
        	queuePassEntrance.addCar(new AbonnementCar());
        }
        break;
	}
}

private void carLeavesSpot(Car car){
	simulatorView.removeCarAt(car.getLocation());
    queueExit.addCar(car);
}

}




