package bayesGame.levelcontrollers;

import bayesGame.minigame.MinigameController;

public class ChoiceMenuChoice {
	
	private String description;
	private MinigameController gameController;
	private Script script;
	public boolean enabled;
	
	public ChoiceMenuChoice(){
		enabled = true;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MinigameController getGameController() {
		return gameController;
	}
	
	public void setGameController(MinigameController gameController) {
		this.gameController = gameController;
		this.script = null;
	}
	
	public void setScript(Script script) {
		this.script = script;
		this.gameController = null;
	}
	
	public Script getScript() {
		return this.script;
	}
	
	public void updateScriptController(LevelController controller){
		if (script != null){
			script.setController(controller);
		}
	}
	

}
