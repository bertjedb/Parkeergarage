package Controller;
//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Simulator;

public class Gui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
    private ActionEvent event;
    public JTable table;

	public Gui(){				


		Container contentPane = getContentPane();
		
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(1, 0));
        
        JPanel flow = new JPanel();
        flow.add(toolbar);

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton stepButton = new JButton("Step one minute");
        JButton step100Button = new JButton("Step 100 minutes");
        JButton quitButton = new JButton("Quit");

        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        stepButton.addActionListener(this);
        step100Button.addActionListener(this);
        quitButton.addActionListener(this);

        toolbar.add(startButton);
        toolbar.add(quitButton);
        toolbar.add(stepButton);   
        toolbar.add(step100Button); 
        toolbar.add(pauseButton);        

        contentPane.add(flow, BorderLayout.NORTH);
        
        pack();
        
        setVisible(true);	
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
                
                if(command == "Step 100 minutes") {
                	Simulator.simulator.oneStep();                    
                }
                
                if(command == "Pause Program") {
                	Simulator.simulator.pauseProgram(); 
                }
                               
                if (command == "Start") {
                	Simulator.simulator.startProgram();                                      
                }
                                                
                if (command == "Quit the program") {
                	Simulator.simulator.quitProgram();                                        
                }                
            }          
        };        
        newThread.start();    
    }
}