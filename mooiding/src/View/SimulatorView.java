package View;
// Imports
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import Model.*;

public class SimulatorView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private ActionEvent event;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces,Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();

        Container contentPane = getContentPane();
     
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(1, 0));
        
        JPanel flow = new JPanel(new GridLayout(1, 0));
        flow.add(toolbar);
        
        contentPane.add(flow, BorderLayout.SOUTH );
        contentPane.add(carParkView, BorderLayout.CENTER);
        
        //Start button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(50, 50));
        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.white);
        startButton.addActionListener(this);
        toolbar.add(startButton);
        
        //One step button        
        JButton stepButton = new JButton("Step one minute");
        stepButton.setPreferredSize(new Dimension(50, 50));
        stepButton.setBackground(Color.red);
        stepButton.setForeground(Color.white);
        stepButton.addActionListener(this);
        toolbar.add(stepButton); 
        
        //100 steps button        
        JButton step100Button = new JButton("Step 100 minutes");
        step100Button.setPreferredSize(new Dimension(50, 50));
        step100Button.setBackground(Color.blue);
        step100Button.setForeground(Color.white);
        step100Button.addActionListener(this);
        toolbar.add(step100Button); 
        
        //Pause button       
        JButton pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(50, 50));
        pauseButton.setBackground(Color.red);
        pauseButton.setForeground(Color.white);
        pauseButton.addActionListener(this);
        toolbar.add(pauseButton); 
        
        //Quit button        
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(50, 50));
        quitButton.setBackground(Color.blue);
        quitButton.setForeground(Color.white);
        quitButton.addActionListener(this);
        toolbar.add(quitButton);

        pack();
        
        setVisible(true);

        updateView();
      }
    
    
    public void setActionEvent(ActionEvent e) {
        event = e;
    }
    
    public ActionEvent getActionEvent() {
        return event;
    }
    
    public void actionPerformed(ActionEvent e) {
        setActionEvent(e);
        
        Thread newThread = new Thread() {
            public void run() {
            	
                ActionEvent event = getActionEvent();                
                String command = event.getActionCommand();
                
                if(command == "Step one minute") {
                	Simulator.simulator.oneStep();                    
                }
                
                if(command == "Pause Program") {               	
                	Simulator.simulator.pauseProgram();                    
                }
                               
                if (command == "Start") {
                	if (Model.Simulator.programRunning == false){
                		Simulator.simulator.startProgram();                    
                	}
                }
                                                   
                if (command == "Quit the program") {
                	Simulator.simulator.quitProgram();                                        
                }                
            }          
        };        
        newThread.start();    
    }
    public void updateView() {
        carParkView.updateView();
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

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

    public Location getFirstFreeLocation() {
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
		 * 
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