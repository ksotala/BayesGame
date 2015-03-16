package bayesGame.ui;

import javax.swing.JButton;

import bayesGame.levelcontrollers.ChoiceMenuChoice;

public class ChoiceButton extends JButton {
	
	public ChoiceMenuChoice choice;
	
	public ChoiceButton(String description){
		super(description);
	}

}
