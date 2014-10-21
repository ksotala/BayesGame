package bayesGame.ui;

public interface InterfaceView {
	
	public void display();
	
	public void addText(String text);
	public void addRefreshDisplay();
	public void processEventQueue();
	
}
