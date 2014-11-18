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
import bayesGame.levelcontrollers.Controller;

public class DialogMenu extends JDialog {
	
	private Controller owner;
	private List<OptionNodeOption> options;
	private List<OptionButton> buttons;
	private Object node;

	public DialogMenu(Controller owner, JFrame frame, Object node, List<OptionNodeOption> options) {
		super(frame, true);
		this.options = options;
		this.owner = owner;
		this.node = node;
		
		if (options != null){
			setupMenu();
		} else {
			this.sendMessage(1);
		}
	}
	
	private void setupMenu(){
		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		buttons = new ArrayList<OptionButton>();
		for (OptionNodeOption o : options){
			String buttonText = o.getDescription();
			int timeTaken = o.getTimeSpent();
			OptionButton button = new OptionButton(buttonText, timeTaken);
			
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                buttonPressed(evt);
	            }
	        });
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			if (!this.requirementsMet(o)){
				button.setEnabled(false);
			}
			
			
			container.add(button);
		}
		
		JButton cancelButton = new JButton("Never mind");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(cancelButton);
	}

	protected void CancelButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	protected void buttonPressed(ActionEvent evt) {
		OptionButton source = (OptionButton)evt.getSource();
		this.sendMessage(source.timeTaken);
		this.dispose();
	}
	
	private void sendMessage(int timeTaken){
		Object[] message = new Object[2];
		message[0] = node;
		message[1] = timeTaken;
		
		owner.genericMessage(message);
	}
	
	private boolean requirementsMet(OptionNodeOption option){
		return !option.hasRequirements();
	}
	
	



}
