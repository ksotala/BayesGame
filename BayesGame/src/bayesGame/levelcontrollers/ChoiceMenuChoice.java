package bayesGame.levelcontrollers;

import bayesGame.minigame.MinigameController;

public class ChoiceMenuChoice {
	
	private String description;
	private MinigameController gameController;
	private Script script;
	public boolean enabled;
	
	public ChoiceMenuChoice(){
	}
	
	public ChoiceMenuChoice(String description, Script script){
		enabled = true;
		this.description = description;
		this.script = script;
	}
	
	public ChoiceMenuChoice(String description) {
		this.description = description;
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
		this.enabled = true;
	}
	
	public void setScript(Script script) {
		this.script = script;
		this.gameController = null;
		this.enabled = true;
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
