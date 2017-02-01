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
		
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        
        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(1, 0));
        
        JPanel flow = new JPanel();
        flow.add(buttons);

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

        buttons.add(startButton);
        buttons.add(quitButton);
        buttons.add(stepButton);   
        buttons.add(step100Button); 
        buttons.add(pauseButton);        

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
                	Simulator.simulator.step100();                    
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