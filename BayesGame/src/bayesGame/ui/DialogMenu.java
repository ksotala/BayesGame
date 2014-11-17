package bayesGame.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import bayesGame.bayesbayes.OptionNodeOption;

public class DialogMenu extends JDialog {
	
	private List<OptionNodeOption> options;
	private List<JButton> buttons;

	public DialogMenu(JFrame frame, List<OptionNodeOption> options) {
		super(frame);
		this.options = options;
		
		if (options != null){
			setupMenu();
		}
	}
	
	private void setupMenu(){
		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		buttons = new ArrayList<JButton>();
		for (OptionNodeOption o : options){
			JButton button = new JButton();
			String buttonText = o.getDescription();
			button.setText(buttonText);
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                buttonPressed(evt);
	            }
	        });
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			container.add(button);
		}
		
		
		
		
	}

	protected void buttonPressed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	



}
