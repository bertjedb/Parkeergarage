package View;
// Imports
//sfsdjfb
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import Model.*;
/**
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

public class SimulatorView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private ActionEvent event;
    private JLabel revenueLabel;
    private JLabel carLabel;
    private JLabel queueLabel;
    private JLabel estimatedRevenueLabel;
    
    private JLabel adhocCarsLabel;
    private JLabel passCarsLabel;
    private JLabel aboCarsLabel;

    /*
     * Constructor voor class SimulatorView
     * @param numberOfFloors zijn de aantal verdiepingen
     * @param numberOfRows zijn de aantal rijen
     * @param numberOfPlaces aantal plekken in de simulatie
     * @param simulator
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
    	// ,Model simulator
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView(this);

        Container contentPane = getContentPane();
     
        //Worden buttons toegevoegd aan de Panel.
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));

        //Worden labels toegevoegd aan de Panel.
        JPanel labels1 = new JPanel();
        labels1.setLayout(new GridLayout(1, 0));
        
        //Worden labels toegevoegd aan de Panel.
        JPanel labels2 = new JPanel();
        labels2.setLayout(new GridLayout(1, 0));
        
        //Buttons toegevoegd aan row1
        JPanel flow = new JPanel(new GridLayout(0, 1));
        flow.add(labels1);
        flow.add(labels2);
        flow.add(buttons);
        
        //Plaatsing van de buttons onderin het scherm.
        contentPane.add(flow, BorderLayout.SOUTH );
        
        //Zorgt dat simulatie bovenin van het scherm komt.
        contentPane.add(carParkView, BorderLayout.NORTH);
        
        //Start button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(50, 50));
        startButton.setBackground(Color.green);
        startButton.addActionListener(this);
        buttons.add(startButton);
        
        //One step button        
        JButton stepButton = new JButton("Step one minute");
        stepButton.setPreferredSize(new Dimension(50, 50));
        stepButton.addActionListener(this);
        buttons.add(stepButton); 
        
        //100 steps button        
        JButton step100Button = new JButton("Step 100 minutes");
        step100Button.setPreferredSize(new Dimension(50, 50));
        step100Button.addActionListener(this);
        buttons.add(step100Button); 
        
        //Pause button       
        JButton pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(50, 50));
        pauseButton.addActionListener(this);
        buttons.add(pauseButton); 
        
        //Quit button        
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(50, 50));
        quitButton.setBackground(Color.red);
        quitButton.addActionListener(this);
        buttons.add(quitButton);

        //Revenue label
        this.revenueLabel = new JLabel("Revenue");
        revenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        revenueLabel.setText(String.valueOf(Model.getAmountOfRevenue()));
        labels1.add(revenueLabel);
        
        //Number of Cars label
        this.carLabel = new JLabel("Cars in garage");
        carLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carLabel.setText("Number of cars = " + Model.getAmountOfRevenue() + "/540");
        labels1.add(carLabel);
        
        //Number of Cars in queue label
        this.queueLabel = new JLabel("Cars in queue");
        queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        queueLabel.setText("Queue = " + Model.getQueueLength());
        labels1.add(queueLabel);
        
        //Estimated revenue label
        this.estimatedRevenueLabel = new JLabel("Estimated revenue");
        estimatedRevenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        estimatedRevenueLabel.setText(String.valueOf(Model.getEstimatedRevenue()));
        labels1.add(estimatedRevenueLabel);
             
        //Amount of adhoccars label
        this.adhocCarsLabel = new JLabel("adhocCarsLabel");
        adhocCarsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adhocCarsLabel.setText(String.valueOf(Model.getAmountOfAdHoc()));
        labels2.add(adhocCarsLabel);
        
        //Amount of parkingpasscars label
        this.passCarsLabel = new JLabel("passCarsLabel");
        passCarsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passCarsLabel.setText(String.valueOf(Model.getAmountOfPass()));
        labels2.add(passCarsLabel);
        
        //Amount of abonnementcars label
        this.aboCarsLabel = new JLabel("aboCarsLabel");
        aboCarsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aboCarsLabel.setText(String.valueOf(Model.getAmountOfAbo()));
        labels2.add(aboCarsLabel);
        
        
        pack();
        
        setVisible(true);

        updateView();
      }
    
    /*
     * @param e wordt gelijkgesteld aan event.
     * 
     */
    public void setActionEvent(ActionEvent e) {
        event = e;
    }
    
    /*
     * @return returned event.
     */
    public ActionEvent getActionEvent() {
        return event;
    }
    
    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
   
   
    public void actionPerformed(ActionEvent e) {
        setActionEvent(e);
        
        /*
         * Wordt nieuwe thread aangemaakt. 
         */
        
        Thread newThread = new Thread() {
            public void run() {
            	
                ActionEvent event = getActionEvent();                
                String command = event.getActionCommand();
                
                //Zet actie achter "Step one minute" button.
                if(command == "Step one minute") {
                	Model.simulator.oneStep();                    
                }
                
                //Zet actie achter "Step 100 minutes" button.
                if(command == "Step 100 minutes") {
                	Model.simulator.step100();                    
                }
                
                //Zorgt dat het programma gepauzeerd kan worden.
                if(command == "Pause") {               	
                	Model.simulator.pauseProgram();                    
                }
                //Zorgt dat het programma begint door op start button te drukken.              
                if (command == "Start") {
                		Model.simulator.startProgram();                    
                }
                 //Zorgt dat je de simulatie kan stoppen.                                  
                if (command == "Quit") {
                	Model.simulator.quitProgram();                                        
                }                
            }          
        };        
        newThread.start();    
    }
    //Update parkeergarage view
    public void updateView() {
        updateLabels();
 
    	carParkView.updateView();
    }
    
    public  void updateLabels(){
    	 if (revenueLabel != null && carLabel != null){
    		 revenueLabel.setText("Total money: " + Model.getAmountOfRevenue() + ",-");
    		 carLabel.setText("Number of cars = " + Model.getAmountOfCars() + "/540");
    		 queueLabel.setText("Length of queue = " + Model.getQueueLength());
    		 estimatedRevenueLabel.setText("Estimated revenue: " + Model.getEstimatedRevenue() + ",-");
    		 adhocCarsLabel.setText("Number of AdHocCars = " + Model.getAmountOfAdHoc());
       		 passCarsLabel.setText("Number of ParkingPassCars = " + Model.getAmountOfPass());
       		 aboCarsLabel.setText("Number of AbonnementCars = " + Model.getAmountOfAbo());
        }
    }
    
    
    //@return aantal verdiepingen
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

	//@return aantal rijen
    public int getNumberOfRows() {
        return numberOfRows;
    }

    //@return aantal plaatsen
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    //@return aantal vrije plekken
    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    /*
     * Method die locatie van auto's returned.
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /*
     * Method die auto kan plaatsen.
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    /*
     * Method om auto van te verwijderen.
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    /*
     * Method om locatie van eerst volgende vrije plek op te vragen.
     */
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            if(floor==0){
        	for (int row = 2; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    } 
                    	
                }
            }
        } else { 
        	for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
        	 }
          }
       }
      return null;
    }
    
    public Location getFirstAboLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /*
     * Method om eerst volgende vertrekkende auto te laten zien.
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    //
    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

   // 
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    

}
