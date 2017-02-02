package Controller;
//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Simulator;
/**
 * 
 * @author Bert de Boer
 *
 */
public class Gui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
    private ActionEvent event;
    public JTable table;

	public Gui(){					
	}
	
	
	public void setActionEvent(ActionEvent e) {
        event = e;
    }
    
    public ActionEvent getActionEvent() {
        return event;
    }
    
    Thread newThread = new Thread();
    
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
               try {
				newThread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
                       
        if (command == "Start") {
        	Simulator.simulator.startProgram();                                      
        }
                                        
        if (command == "Quit the program") {
        	Simulator.simulator.quitProgram();                                        
        }                          
};        
    public void actionPerformed(ActionEvent e) {   
    	setActionEvent(e);
        run();
        newThread.start();    
    }
}