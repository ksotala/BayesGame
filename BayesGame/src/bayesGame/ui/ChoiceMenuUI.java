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
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.Script;
import bayesGame.minigame.MinigameController;

public class ChoiceMenuUI extends JDialog {
	
	private ChoiceMenu choiceMenu;
	private LevelController owner;

	public ChoiceMenuUI(JFrame frame, LevelController owner, ChoiceMenu choiceMenu) {
		super(frame, true);
		this.choiceMenu = choiceMenu;
		this.owner = owner;
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
			button.setEnabled(choice.enabled);
			container.add(button);
		}
		this.pack();
	}
	
	private void buttonPressed(ActionEvent evt){
		ChoiceButton button = (ChoiceButton)evt.getSource();
		MinigameController gameController = button.choice.getGameController();
		Script script = button.choice.getScript();
		if (gameController != null){
			owner.menuChoiceMade(gameController);
		} else if (script != null){
			script.run();
		}
		
		this.dispose();
	}

}
