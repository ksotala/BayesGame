package bayesGame.levelcontrollers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Controller {
	
	public abstract void keyMessage(Object o);
	public abstract void mouseMessage(Object o);
	public abstract void genericMessage();
	public abstract void genericMessage(Object o);
	
}
