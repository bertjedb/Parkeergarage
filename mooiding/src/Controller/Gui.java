package Controller;
//imports
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Simulator;
/**
 * 
 * @author Team Sublime 
 * @klas ITV1A
 * @version 2-2-2017
 */

public class Gui implements ActionListener {
    private ActionEvent event;
    public JTable table;

    //Constructor voor GUI class
	public Gui(){
	}
    
	public void actionPerformed(ActionEvent e) {   
  	                
        String command = event.getActionCommand();
        
      //Zet actie achter "Step one minute" button.
        if(command == "Step one minute") {
        	Simulator.simulator.oneStep();                    
        }
        
      //Zet actie achter "Step 100 minutes" button.
        if(command == "Step 100 minutes") {
        	Simulator.simulator.step100();                    
        }
        
      //Zet actie achter "Pause" button.
        if(command == "Pause Program") {
        	Simulator.simulator.pauseProgram();
        }
           
      //Zet actie achter "Start" button.
        if (command == "Start") {
        	Simulator.simulator.startProgram();                                      
        }
        
      //Zet actie achter "Quit" button.                                
        if (command == "Quit the program") {
        	Simulator.simulator.quitProgram();                                        
        }                                  
	}
}