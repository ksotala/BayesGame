package bayesGame.ui;

import javax.swing.JPanel;

public interface InterfaceView {
	
	public void display();
	
	public void addText(String text);
	public void addRefreshDisplay();
	public void processEventQueue();
	
	public void proceed();
	
}
