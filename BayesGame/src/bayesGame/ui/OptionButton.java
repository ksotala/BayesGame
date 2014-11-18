package bayesGame.ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import bayesGame.bayesbayes.OptionNodeOption;

public class OptionButton extends JButton {

	public OptionNodeOption option;
	
	public OptionButton(String arg0, OptionNodeOption option) {
		super(arg0);
		this.option = option;
	}


}
