package bayesGame.viewcontrollers;

import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.Controller;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.MiniScript;

public interface ViewController {
	
	public abstract void addText(String text);
	public abstract void showText(String text);
	public abstract void addRefreshDisplay();
	public abstract void processEventQueue();
	public abstract void dispose();
	public abstract void giveControlTo(ViewController viewController);
	public abstract void display();
	public abstract void setOwner(LevelController owner);
	public abstract void showMenu(ChoiceMenu choice, LevelController levelController);
	
	abstract void receiveControl(Object control);
	abstract void processingDone();
	public abstract void addDialog(String title, String[] options);
	public abstract void addDialog(String string, MiniScript[] scripts);
	public abstract void showOptionsMenu();
	public abstract void showEndMessage(String string);
	public abstract void showMessage(String string);
	public abstract void showResolutionMenu();

}
