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
    private JLabel moneyLabel;
    private JLabel carLabel;

    /*
     * Constructor voor class SimulatorView
     * @param numberOfFloors zijn de aantal verdiepingen
     * @param numberOfRows zijn de aantal rijen
     * @param numberOfPlaces aantal plekken in de simulatie
     * @param simulator
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces,Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();

        Container contentPane = getContentPane();
     
        //Worden buttons toegevoegd aan de Panel.
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));

        //Worden labels toegevoegd aan de Panel.
        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(1, 0));
        
        //Buttons en labels toegevoegd aan flow.
        JPanel flow = new JPanel(new GridLayout(0, 1));
        flow.add(buttons);
        flow.add(labels);    
        
        //Plaatsing van de buttons onderin het scherm.
        contentPane.add(flow, BorderLayout.SOUTH );
        
        //Zorgt dat simulatie in midden van het scherm komt.
        contentPane.add(carParkView, BorderLayout.CENTER);
        
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

        //Money label
        JLabel moneyLabel = new JLabel("Money");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setText(String.valueOf(Model.Simulator.getAmountOfMoney()));
        labels.add(moneyLabel);
        
        //Number of Cars label
        JLabel carLabel = new JLabel("Cars in queue");
        carLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carLabel.setText("Number of cars = " + Model.Simulator.getAmountOfCars() + "/540");
        labels.add(carLabel);
        
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
                	Simulator.simulator.oneStep();                    
                }
                
                //Zet actie achter "Step 100 minutes" button.
                if(command == "Step 100 minutes") {
                	Simulator.simulator.step100();                    
                }
                
                //Zorgt dat het programma gepauzeerd kan worden.
                if(command == "Pause") {               	
                	Simulator.simulator.pauseProgram();                    
                }
                //Zorgt dat het programma begint door op start button te drukken.              
                if (command == "Start") {
                		Simulator.simulator.startProgram();                    
                }
                 //Zorgt dat je de simulatie kan stoppen.                                  
                if (command == "Quit") {
                	Simulator.simulator.quitProgram();                                        
                }                
            }          
        };        
        newThread.start();    
    }
    //Update parkeergarage view
    public void updateView() {
        carParkView.updateView();
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
            Model.Simulator.amountOfCars++;
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
    
    private class CarParkView extends JPanel {
        
        /**
		 * Instantie variabelen
		 */
		private static final long serialVersionUID = 1L;
		private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
