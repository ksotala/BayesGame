package bayesGame.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.MiniScript;
import bayesGame.ui.swinglisteners.AnyKeyListener;
import bayesGame.ui.swinglisteners.KeyController;
import bayesGame.viewcontrollers.ViewController;

public class GameInterface implements InterfaceView, KeyController {

	private JFrame frame;
	private JPanel bigPanel;
	private JTextArea smallPanel;
	private JPanel buttonPanel;
	private JTextPane textPane;
	private JScrollPane scroll;
	
	private ViewController owner;
	private boolean waitingForInput;
	
	private List<String> events;
	private List<MiniScript[]> miniScriptQueue;
	
	public GameInterface() {
		frame = new JFrame("Academy Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setUndecorated(true);
		
		bigPanel = new JPanel();
		smallPanel = new JTextArea();
		buttonPanel = new JPanel();
		textPane = new JTextPane();
				
		events = new ArrayList<String>();
		miniScriptQueue = new ArrayList<MiniScript[]>();
		
		waitingForInput = false;
		textPane.addKeyListener(new AnyKeyListener(this));
		buttonPanel.addKeyListener(new AnyKeyListener(this));
		bigPanel.addKeyListener(new AnyKeyListener(this));
		smallPanel.addKeyListener(new AnyKeyListener(this));
		
		addComponentsToPane(frame.getContentPane());
	}
	
	public void setBigPanel(JPanel bigPanel) {
		frame.getContentPane().remove(this.bigPanel);
		
	    bigPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		bigPanel.setMinimumSize(new Dimension(BayesGame.getNewHeight(600),BayesGame.getNewWidth(650)));
		bigPanel.setPreferredSize(new Dimension(BayesGame.getNewHeight(750),BayesGame.getNewWidth(750)));
		frame.getContentPane().add(bigPanel, getBigPanelConstraints());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.bigPanel = bigPanel;
	}

	public void setSmallPanel(JTextArea smallPanel) {
		frame.getContentPane().remove(this.smallPanel);
		
		smallPanel.setLineWrap(true);
		smallPanel.setWrapStyleWord(true);
		smallPanel.setFont(smallPanel.getFont().deriveFont(BayesGame.getNewFontSize()));
		smallPanel.setEditable(false);
		
	    smallPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    smallPanel.setMinimumSize(new Dimension(BayesGame.getNewHeight(250),BayesGame.getNewWidth(400)));
	    smallPanel.setPreferredSize(new Dimension(BayesGame.getNewHeight(250),BayesGame.getNewWidth(500)));
		frame.getContentPane().add(smallPanel, getSmallPanelConstraints());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.smallPanel = smallPanel;
	}
	
	public void setButtonPanel(JPanel buttonPanel) {
		frame.getContentPane().remove(this.buttonPanel);
		
	    buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    buttonPanel.setMinimumSize(new Dimension(BayesGame.getNewHeight(250),BayesGame.getNewWidth(50)));
	    buttonPanel.setPreferredSize(new Dimension(BayesGame.getNewHeight(250),BayesGame.getNewWidth(50)));
	    frame.getContentPane().add(buttonPanel, getButtonPanelConstraints());

		this.buttonPanel = buttonPanel;
	}
	

	
	public void display(){
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
		
	private void addComponentsToPane(Container pane){
		pane.setLayout(new GridBagLayout());
	    
	    this.setBigPanel(bigPanel);
	    this.setSmallPanel(smallPanel);
	    this.setButtonPanel(buttonPanel);
	    
	    GridBagConstraints c = new GridBagConstraints();
	    
	    textPane.setEditable(false);
	    textPane.setMinimumSize(new Dimension(BayesGame.getNewHeight(350),BayesGame.getNewWidth(150)));
	    textPane.setPreferredSize(new Dimension(BayesGame.getNewHeight(350),BayesGame.getNewWidth(150)));
	    textPane.putClientProperty("IgnoreCharsetDirective", Boolean.TRUE);
	    
	    scroll = new JScrollPane (textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    c.gridx = 0;
	    c.gridy = 2;
	    c.gridwidth = GridBagConstraints.REMAINDER;
	    c.gridheight = GridBagConstraints.REMAINDER;
	    c.ipady = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(scroll, c);
	}
	
	private GridBagConstraints getBigPanelConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridheight = 2;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    
	    return c;
	}
	
	private GridBagConstraints getSmallPanelConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		
	    c.gridx = 1;
	    c.gridy = 1;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    
	    return c;
	}
	
	private GridBagConstraints getButtonPanelConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.2;
	    c.ipady = 0;
	    c.ipadx = 0;
		c.fill = GridBagConstraints.BOTH;
		
		return c;
	}

	@Override
	public void addText(String text) {
		if (events.size() > 0){
			addMoreIndicatorToPreviousItem();
		}
		events.add(text);
	}

	private void addMoreIndicatorToPreviousItem(){
		int lastitem = events.size()-1;
		String text = events.get(lastitem);
		if (!text.startsWith("$$")){
			text = text + ">";
			events.remove(lastitem);
			events.add(text);
		}
	}
	
	@Override
	public void addRefreshDisplay() {
		addText("$$REFRESHDISPLAY");
	}

	@Override
	public void processEventQueue() {
		if (events.size() > 0){
			processFirstEvent();
			waitForInput();
		} else {
			owner.processingDone();
		}
	}
	
	private void processFirstEvent(){
		String text = events.remove(0);
		
		if (text.equals("$$REFRESHDISPLAY")){
			bigPanel.repaint();
		} else if (text.equals("$$DIALOGMENU")) {
			dialogMenu();
		} else {
			deletePreviousNextIndicatorFromPane();
			writeToTextPane(text);			
		}
		refreshScrollbar();
	}
	
	private void dialogMenu() {
		MiniScript[] scripts = miniScriptQueue.remove(0);
		String[] titles = new String[scripts.length];
		for (int i = 0; i < scripts.length; i++){
			titles[i] = scripts[i].name;
		}
		
		int choice = showMenu("", titles);
		scripts[choice].run();
	}

	private void deletePreviousNextIndicatorFromPane(){
		try {
			String text = textPane.getText(textPane.getDocument().getLength()-3, 1);
			if (text.equals(">")){
				textPane.getDocument().remove(textPane.getDocument().getLength()-3, 1);
			}
		} catch (BadLocationException e) { }
		
		
		// textPane.getDocument().remove(textPane., arg1);
	}
	
	private void writeToTextPane(String text){
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, BayesGame.getNewFontSizeInt());
		
		text = text + System.getProperty("line.separator");
		
		StyledDocument doc = textPane.getStyledDocument();
		
		try { doc.insertString(doc.getLength(), text, style); }
        catch (BadLocationException e){}
		
		textPane.revalidate();
		textPane.setCaretPosition(textPane.getDocument().getLength());
		scroll.revalidate();
		// frame.pack();
	}
	
	private void waitForInput(){
		textPane.requestFocusInWindow();
		
		waitingForInput = true;
		
	}
	
	@Override
	public void keyMessage(KeyEvent e) {
		if (waitingForInput){
			waitingForInput = false;
			processEventQueue();
		}
		
	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}
	
	public void dispose(){
		frame.dispose();
	}

	public void setOwner(ViewController viewController) {
		this.owner = viewController;
	}

	public void showMenu(ChoiceMenu choice, LevelController owner) {
		ChoiceMenuUI choiceMenuUI = new ChoiceMenuUI(frame, owner, choice);
		choiceMenuUI.setVisible(true);
	}
	
	public void refreshScrollbar(){
		textPane.revalidate();
		scroll.revalidate();
		JScrollBar vertical = scroll.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}

	
	public int showMenu(String title, String[] options) {
		if (options.length == 2){
			return JOptionPane.showOptionDialog(frame, null, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		} else {
			return JOptionPane.showOptionDialog(frame, null, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);			
		}
	}

	public void addDialog(String title, String[] options) {
		MiniScript[] scripts = new MiniScript[options.length];
		for (int i = 0; i < options.length; i++){
			scripts[i] = new MiniScript(options[i]);
		}
		addDialog(title, scripts);
	}

	public void addDialog(String title, MiniScript[] scripts) {
		events.add("$$DIALOGMENU");
		miniScriptQueue.add(scripts);
		
	}

	public void showOptionsMenu() {
		frame.dispose();
		
		frame = new JFrame("Select colors");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JComponent newContentPane = new ColorSelection();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
	}

	public void clearText() {
		textPane.setText("");
	}

	public void showEndMessage(String string) {
		showMessage(string);
		System.exit(0);
	}

	public void showMessage(String string) {
		JOptionPane.showMessageDialog(frame, string);
	}

	public void showResolutionMenu() {
		Object[] possibilities = {"1280x720", "1600x900", "1920x1080"};
		BayesGame.gameResolution = (String)JOptionPane.showInputDialog(
                frame,
                null,
                "Choose a resolution",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "1920x1080");
		BayesGame.run();

	}

	
	
	

}
