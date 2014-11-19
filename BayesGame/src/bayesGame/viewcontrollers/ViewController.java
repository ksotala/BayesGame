package bayesGame.viewcontrollers;

public interface ViewController {
	
	public abstract void addText(String text);
	public abstract void showText(String text);
	public abstract void addRefreshDisplay();
	public abstract void processEventQueue();
	public abstract void dispose();

}
