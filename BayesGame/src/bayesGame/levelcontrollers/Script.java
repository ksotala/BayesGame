package bayesGame.levelcontrollers;

public interface Script {
	
	public void MinigameCompleted(String message);
	public void QueueEmpty();
	public void run();
	public void setController(LevelController controller);
	
}