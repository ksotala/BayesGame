package bayesGame.ui.swinglisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bayesGame.ui.InterfaceView;

public class AnyKeyListener extends KeyAdapter {
	
	private final KeyController controller;

	public AnyKeyListener(KeyController controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		controller.keyMessage(e);
	}

}
