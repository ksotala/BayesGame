package bayesGame.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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

public class MinigameMenu extends JDialog {
	
	private Controller owner;
	private List<OptionNodeOption> options;
	private List<OptionButton> buttons;
	private Object node;

	public MinigameMenu(Controller owner, JFrame frame, Object node, List<OptionNodeOption> options) {
		super(frame, true);
		this.options = options;
		this.owner = owner;
		this.node = node;
		
		if (options != null && options.size() > 0){
			setupMenu();
		} else {
			this.sendMessage(null);
			this.CancelButtonActionPerformed(null);
		}
	}
	
	private void setupMenu(){
		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		buttons = new ArrayList<OptionButton>();
		for (OptionNodeOption o : options){
			String requirementText = "<html>";
			
			if (o.hasRequirements()){
				requirementText = requirementText + "<b>(" + o.getRequirementString() + ")</b> ";
			}
			String buttonText = requirementText + o.getDescription();
			if (o.getTimeSpent() > 0){
				buttonText = buttonText + " (" + o.getTimeSpent() + " turns)";
			}
			
			OptionButton button = new OptionButton(buttonText, o);
			button.setFont(new Font("Serif", Font.PLAIN, 30));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                buttonPressed(evt);
	            }
	        });
			
			if (!this.requirementsMet(o)){
				button.setEnabled(false);
			}
			
			container.add(button);
		}
		
		JButton cancelButton = new JButton("Never mind");
		cancelButton.setFont(new Font("Serif", Font.PLAIN, 30));
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(cancelButton);
		this.pack();
	}

	protected void CancelButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	protected void buttonPressed(ActionEvent evt) {
		OptionButton source = (OptionButton)evt.getSource();
		this.sendMessage(source.option);
		this.dispose();
	}
	
	private void sendMessage(OptionNodeOption option){
		Object[] message = new Object[2];
		message[0] = node;
		message[1] = option;
		
		owner.genericMessage(message);
	}
	
	private boolean requirementsMet(OptionNodeOption option){
		return !option.hasRequirements();
	}
	
	



}
