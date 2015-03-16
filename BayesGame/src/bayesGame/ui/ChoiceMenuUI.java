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

import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.ChoiceMenuChoice;
import bayesGame.levelcontrollers.Controller;
import bayesGame.minigame.MinigameController;

public class ChoiceMenuUI extends JDialog {
	
	private ChoiceMenu choiceMenu;

	public ChoiceMenuUI(JFrame frame, ChoiceMenu choiceMenu) {
		super(frame, true);
		this.choiceMenu = choiceMenu;
		setupMenu();
	}
	
	private void setupMenu(){
		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		List<JButton> buttons = new ArrayList<JButton>();
		for (ChoiceMenuChoice choice : choiceMenu){
			ChoiceButton button = new ChoiceButton(choice.getDescription());
			button.choice = choice;
			button.setFont(new Font("Serif", Font.PLAIN, 30));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                buttonPressed(evt);
	            }
	        });
			container.add(button);
		}
		this.pack();
	}
	
	private void buttonPressed(ActionEvent evt){
		ChoiceButton button = (ChoiceButton)evt.getSource();
		MinigameController gameController = button.choice.getGameController();
		gameController.startGame();
		this.dispose();
	}

}
