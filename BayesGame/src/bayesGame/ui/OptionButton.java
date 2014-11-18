package bayesGame.ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class OptionButton extends JButton {

	public int timeTaken;
	
	public OptionButton(String arg0, int timeTaken) {
		super(arg0);
		this.timeTaken = timeTaken;
	}


}
