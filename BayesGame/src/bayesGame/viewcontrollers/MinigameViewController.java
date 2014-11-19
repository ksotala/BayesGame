package bayesGame.viewcontrollers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.bayesbayes.OptionNodeOption;
import bayesGame.levelcontrollers.Controller;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.MinigameController;
import bayesGame.ui.DialogMenu;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;
import bayesGame.ui.verbs.InteractingVerb;
import bayesGame.ui.verbs.Verb;

public class MinigameViewController implements Controller, ViewController {
	
	private final MinigameController owner;
	private final DiscussionNet gameNet;
	private final GameInterface gameInterface;
	private GraphPanel graphPanel;
	private JPanel infoPanel;
	private JLabel infoPanelText;

	
	public MinigameViewController(MinigameController minigameController,
			DiscussionNet gameNet) {
		this.owner = minigameController;
		this.gameNet = gameNet;
		this.gameInterface = new GameInterface();
		
		initializeView();
	}
	
	private void initializeView(){
		graphPanel = new GraphPanel(gameNet);
		Verb interactingVerb = new InteractingVerb(this, Verb.returnCall.MouseMessage, MouseEvent.BUTTON3);
		Verb textVerb = new InteractingVerb(this, Verb.returnCall.KeyMessage, MouseEvent.BUTTON1);
		graphPanel.addVerb(interactingVerb);
		graphPanel.addVerb(textVerb);

		gameInterface.setBigPanel(graphPanel);
		
	    infoPanel = new JPanel();
	    gameInterface.setSmallPanel(infoPanel);
		
		gameInterface.display();
	}
	
	public void addText(String text){
		gameInterface.addText(text);
	}
	
	public void showText(String text){
		this.addText(text);
		this.processEventQueue();
	}
	
	public void addRefreshDisplay(){
		gameInterface.addRefreshDisplay();
	}
	
	public void updateGraph(){
		graphPanel.updateGraph();
	}
	
	public void processEventQueue(){
		gameInterface.processEventQueue();
	}
	
	public void dispose() {
		gameInterface.dispose();
	}

	@Override
	public void keyMessage(Object o) {
	    BayesNode node = (BayesNode)o;
	    String text = gameNet.getCPTDescription(node.type);
	    System.out.println(text);
	    
	    infoPanelText = new JLabel();
		infoPanelText.setText(text);
		infoPanelText.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanelText.setFont(new Font("Serif", Font.PLAIN, 20));
		
		infoPanel.add(infoPanelText);
		
		gameInterface.getFrame().pack();
	}

	@Override
	public void mouseMessage(Object o) {
		if (o != null){
			BayesNode node = (BayesNode)o;
			Object type = node.type;
			owner.chooseNode(type);
		}
	}

	@Override
	public void genericMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genericMessage(Object o) {
		Object[] message = (Object[])o;
		Object type = message[0];
		
		OptionNodeOption option = (OptionNodeOption)message[1];
		owner.observeNode(type, option);
	}

	public void displayOptions(Object node) {
		if (gameNet.getOptions(node) != null){
			DialogMenu menu = new DialogMenu(this, gameInterface.getFrame(), node, gameNet.getOptions(node));
			menu.setVisible(true);
		} else {
			owner.observeNode(node, null);
		}
	}

	public void displayPopup(String description, String response) {
		String message = description + "\n\n" + response;
		JOptionPane.showMessageDialog(gameInterface.getFrame(), message, "", JOptionPane.PLAIN_MESSAGE);
	}
	

	
	

}