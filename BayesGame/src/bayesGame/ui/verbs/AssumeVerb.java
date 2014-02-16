package bayesGame.ui.verbs;

import java.awt.event.MouseEvent;

import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.swinglisteners.AssumingMousePlugin;

public class AssumeVerb extends Verb {
	
	private PluggableGraphMouse pgm;

	public AssumeVerb(Controller controller, returnCall returnType) {
		super(controller, returnType);
		pgm = new PluggableGraphMouse();
		pgm.add(new AssumingMousePlugin(this, MouseEvent.BUTTON1));
	}

	@Override
	public PluggableGraphMouse getPGM() {
		return pgm;
	}

}
