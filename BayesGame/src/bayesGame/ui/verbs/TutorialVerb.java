package bayesGame.ui.verbs;

import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.swinglisteners.TutorialMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;

public class TutorialVerb extends Verb {

	private PluggableGraphMouse pgm;
	
	public TutorialVerb(Controller controller, returnCall returnType) {
		super(controller, returnType);
		pgm = new PluggableGraphMouse();
		pgm.add(new TutorialMousePlugin(this, "Opin"));
	}

	@Override
	public PluggableGraphMouse getPGM() {
		return pgm;
	}

}
