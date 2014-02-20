package bayesGame.ui.verbs;

import java.awt.event.MouseEvent;

import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.swinglisteners.AssumingMousePlugin;
import bayesGame.ui.swinglisteners.InteractingMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;

public class InteractingVerb extends Verb {
	
	private PluggableGraphMouse pgm;

	public InteractingVerb(Controller controller, returnCall returnType) {
		super(controller, returnType);
		pgm = new PluggableGraphMouse();
		pgm.add(new InteractingMousePlugin(this, MouseEvent.BUTTON3));
	}

	@Override
	public PluggableGraphMouse getPGM() {
		return pgm;
	}

}