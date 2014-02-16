package bayesGame.ui.verbs;

import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import bayesGame.levelcontrollers.Controller;

public abstract class Verb {
	
	private final Controller controller;
	public final returnCall returnType;
	
	public enum returnCall{
		GenericMessage,
		GenericMessageObject,
		MouseMessage,
		KeyMessage
	}

	public Verb(Controller controller, returnCall returnType) {
		this.controller = controller;
		this.returnType = returnType;
	}
	
	public void message(Object o){
		messageController(o);
	}
	
	public abstract PluggableGraphMouse getPGM();
	
	public void message(){
		if (returnType == returnCall.GenericMessage){
			controller.genericMessage();
		}
	}
	
	private void messageController(Object o){
		switch(returnType){
		case GenericMessage:
			controller.genericMessage();
			break;
		
		case GenericMessageObject:
			controller.genericMessage(o);
			break;
			
		case KeyMessage:
			controller.keyMessage(o);
			break;
			
		case MouseMessage:
			controller.mouseMessage(o);
			break;
		}
	}

}
