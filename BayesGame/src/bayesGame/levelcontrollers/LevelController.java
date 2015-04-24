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
	private List<ChoiceMenu> choiceMenuQueue;
	private boolean waiting = false;
	private Script nextScript;
	
	public LevelController() {
		this.viewController = new DefaultViewController();
		this.viewController.setOwner(this);
		this.eventQueue = new ArrayList<String>();
		this.minigameQueue = new ArrayList<Object[]>();
		this.choiceMenuQueue = new ArrayList<ChoiceMenu>();
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
		if (nextScript != null){
			nextScript.run();
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
		case "$$CHOICEMENU":
			choiceMenu();
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
	
	private void choiceMenu(){
		ChoiceMenu choice = choiceMenuQueue.remove(0);
		viewController.showMenu(choice, this);
	}
	
	private void miniGame(){
		Object[] minigame = minigameQueue.remove(0);
		MinigameController controller = (MinigameController)minigame[0];
		
		int timeLimit = (int)minigame[1];
		Object[] knowledges = (Object[])minigame[2];
		
		startMiniGame(controller, timeLimit, knowledges);
	}
	
	private void startMiniGame(MinigameController controller, int timeLimit, Object[] knowledges){
		controller.setOwner(this);
		controller.offerViewController(viewController);
		if (timeLimit > 0 || knowledges != null){
			controller.startGame(timeLimit, knowledges);
		} else {
			controller.startGame();
		}
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

	public void addChoiceMenu(ChoiceMenu choice) {
		eventQueue.add("$$CHOICEMENU");
		choiceMenuQueue.add(choice);
		
	}
	
	public void menuChoiceMade(MinigameController controller){
		startMiniGame(controller, 0, null);
	}

	public void setNextScript(Script nextScript) {
		this.nextScript = nextScript;
	}

	public void addDialog(String title, String ... options) {
		viewController.addDialog(title, options);
		
		MiniScript[] scripts = new MiniScript[options.length];
		for (int i = 0; i < options.length; i++){
			scripts[i] = new MiniScript(options[i]);
		}
		addDialog(title, scripts);
	}

	public void addDialog(String string, MiniScript ... scripts) {
		viewController.addDialog(string, scripts);
		

		
	}
	
}
