package bayesGame.viewcontrollers;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.Controller;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.MiniScript;
import bayesGame.minigame.DiscussionNet;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;

public class DefaultViewController implements ViewController {
	
	private BayesNet gameNet;
	private GameInterface gameInterface;
	private GraphPanel graphPanel;
	private LevelController owner;

	public DefaultViewController() {
		this.gameInterface = new GameInterface();
	}
	
	public void display(){
		gameInterface.setOwner(this);
		gameInterface.display();
	}
	
	public void addGraph(BayesNet gameNet){
		this.gameNet = gameNet;
	}

	public void addText(String text){
		gameInterface.addText(text);
	}
	
	public void showText(String text){
		this.addText(text);
		this.processEventQueue();
	}
	
	public void addRefreshDisplay(){
		gameInterface.addRefreshDisplay();
	}
	
	public void updateGraph(){
		graphPanel.updateGraph();
	}
	
	public void processEventQueue(){
		gameInterface.processEventQueue();
	}

	public void dispose() {
		gameInterface.dispose();
	}
	
	@Override
	public void giveControlTo(ViewController viewController) {
		viewController.receiveControl(gameInterface);
	}
	
	@Override
	public void receiveControl(Object control){
		gameInterface = (GameInterface)control;
		gameInterface.setOwner(this);
	}

	@Override
	public void setOwner(LevelController owner) {
		this.owner = owner;
	}
	
	@Override
	public void processingDone(){
		owner.processedQueue();
	}

	@Override
	public void showMenu(ChoiceMenu choice, LevelController controller) {
		gameInterface.showMenu(choice, controller);
		
	}

	@Override
	public void addDialog(String title, String[] options) {
		gameInterface.addDialog(title, options);
		
	}

	@Override
	public void addDialog(String string, MiniScript[] scripts) {
		gameInterface.addDialog(string, scripts);
	}

	@Override
	public void showOptionsMenu() {
		gameInterface.showOptionsMenu();
		
	}





}
