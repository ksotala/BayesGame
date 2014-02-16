package bayesGame.ui.swinglisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bayesGame.levelcontrollers.Controller;

public class AnyKeyListener extends KeyAdapter {
	
	private final Controller controller;

	public AnyKeyListener(Controller controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		controller.keyMessage(e);
	}

}
