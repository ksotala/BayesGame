package bayesGame.levelcontrollers;

import java.util.ArrayList;
import java.util.List;

import bayesGame.minigame.MinigameController;
import bayesGame.viewcontrollers.DefaultViewController;
import bayesGame.viewcontrollers.ViewController;

public class LevelController {
	
	private ViewController viewController;
	private List<String> eventQueue;
	private List<Object[]> minigameQueue; 
	private boolean waiting = false;
	
	public LevelController() {
		this.viewController = new DefaultViewController();
		this.viewController.setOwner(this);
		this.eventQueue = new ArrayList<String>();
		this.minigameQueue = new ArrayList<Object[]>();
	}
	
	public LevelController(ViewController viewController){
		this.viewController = viewController;
		this.viewController.setOwner(this);
		this.eventQueue = new ArrayList<String>();
		this.minigameQueue = new ArrayList<Object[]>();
	}
	
	public void addText(String text){
		eventQueue.add(text);
	}
	
	public void addProcessEventQueue(){
		eventQueue.add("$$PROCESSQUEUE");
	}
	
	public void run(){
		viewController.display();
		if (eventQueue.size() > 0){
			String event = eventQueue.remove(0);
			executeEvent(event);
		}
	}
	
	private void executeEvent(String event){
		switch(event){
		case "$$PROCESSQUEUE":
			processQueue();
			break;
		case "$$MINIGAME":
			miniGame();
			break;
		default:
			viewController.addText(event);
			run();
			break;
		}
	}
	
	private void processQueue(){
		viewController.processEventQueue();
		waiting = true;
	}
	
	private void miniGame(){
		Object[] minigame = minigameQueue.remove(0);
		MinigameController controller = (MinigameController)minigame[0];
		controller.setOwner(this);
		
		int timeLimit = (int)minigame[1];
		Object[] knowledges = (Object[])minigame[2];
		controller.offerViewController(viewController);
		controller.startGame(timeLimit, knowledges);
	}
	
	public void addMinigame(MinigameController controller, Object... parameters){
		eventQueue.add("$$MINIGAME");
		minigameQueue.add(new Object[]{controller, parameters[0], parameters[1]});
	}
	
	public void processedQueue(){
		if (waiting){
			waiting = false;
			run();
		}
	}
	
	public void minigameCompleted(ViewController minigameViewController){
		minigameViewController.giveControlTo(viewController);
		run();
	}
	
}