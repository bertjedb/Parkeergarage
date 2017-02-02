package Model;
//Imports
import java.util.LinkedList;
import java.util.Queue;

/**
 *  
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

public class QueueCars {
    private Queue<Car> queue = new LinkedList<>();

    //Voegt auto toe aan queue
    public boolean addCar(Car car) {
        return queue.add(car);
    }
    //Verwijderd auto uit queue
    public Car removeCar() {
        return queue.poll();
    }
    //@return aantal auto's in queue
    public int carsInQueue(){
    	return queue.size();
    }
}
