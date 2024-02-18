package log.service;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * 
 * @Dave
 * just an error class to push the responsibility to a single class
 */
public class Error extends Exception{
	
	public Error() {
		
		JOptionPane.showMessageDialog(new JFrame(), "ungültig", "Error", JOptionPane.ERROR_MESSAGE);
		
	}

}
