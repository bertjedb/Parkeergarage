package Controller;
//imports
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Model;
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
        	Model.simulator.oneStep();                    
        }
        
      //Zet actie achter "Step 100 minutes" button.
        if(command == "Step 100 minutes") {
        	Model.simulator.step100();                    
        }
        
      //Zet actie achter "Pause" button.
        if(command == "Pause") {
        	Model.simulator.pauseProgram();
        }
           
      //Zet actie achter "Start" button.
        if (command == "Start") {
        	Model.simulator.startProgram();                                      
        }
        
      //Zet actie achter "Quit" button.                                
        if (command == "Quit") {
        	Model.simulator.quitProgram();                                        
        }      
        //Zorgt dat je de simulatie sneller gaat.                                  
        if (command == "Make sim faster") {
        	if(Model.simulator.tickPause > 50){
       	Model.simulator.tickPause -= 50;
        View.SimulatorView.currentSpeed += 1;
        }     
        //Zorgt dat je de simulatie slomer gaat.                                  
        if (command == "Make sim slower") {
    	  if(Model.simulator.tickPause < 1500){
        Model.simulator.tickPause += 50;
        View.SimulatorView.currentSpeed -= 1;
    	  }
        }
      }
   }
}