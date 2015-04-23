package bayesGame.levelcontrollers;

import bayesGame.levelcontrollers.events.SpokeAboutJacenEvent;
import bayesGame.world.World;

public class MeetingCherylScript implements Script {
	
	public static boolean metCheryl = false;

	@Override
	public void MinigameCompleted(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void QueueEmpty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		if (metCheryl){
			new LoopScript().run();
		} else {
			// TODO: actual meeting Cheryl event			
			boolean spokeAboutJacen = false;
			
			if (spokeAboutJacen){
				World.insertEvent(2, new SpokeAboutJacenEvent());
			}
			
			metCheryl = true;
			
			new LoopScript().run();
		}

	}

	@Override
	public void setController(LevelController controller) {
		// TODO Auto-generated method stub

	}

}
