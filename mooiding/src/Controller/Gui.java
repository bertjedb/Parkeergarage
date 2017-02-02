package Controller;
//imports
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Simulator;
/**
 * 
 * @author Bert de Boer
 *
 */
public class Gui implements ActionListener {
    private ActionEvent event;
    public JTable table;

	public Gui(){
	}
    
	public void actionPerformed(ActionEvent e) {   
  	                
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
}