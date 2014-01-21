package bayesGame.levelcontrollers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Controller {

	public Controller() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void keyMessage(KeyEvent e);
	public abstract void mouseMessage(MouseEvent e);

}
