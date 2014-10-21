package bayesGame.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import bayesGame.ui.swinglisteners.AnyKeyListener;

public class GameInterface implements InterfaceView {

	private JFrame frame;
	private JPanel bigPanel;
	private JPanel smallPanel;
	private JTextPane textPane;
	private JScrollPane scroll;
	
	private boolean waitingForInput;
	
	private List<String> events;
	
	public GameInterface() {
		frame = new JFrame("Academy Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		bigPanel = new JPanel();
		smallPanel = new JPanel();
		textPane = new JTextPane();
		
		events = new ArrayList<String>();
		
		waitingForInput = false;
		textPane.addKeyListener(new AnyKeyListener(this));
	}
	
	public void setBigPanel(JPanel bigPanel) {
		this.bigPanel = bigPanel;
	}

	public void setSmallPanel(JPanel smallPanel) {
		this.smallPanel = smallPanel;
	}
	
	public void display(){
		addComponentsToPane(frame.getContentPane());
		
		frame.pack();
		frame.setVisible(true);
	}
		
	private void addComponentsToPane(Container pane){
		GridBagConstraints c;
		
		pane.setLayout(new GridBagLayout());
	    
		c = new GridBagConstraints();
		
	    bigPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		bigPanel.setMinimumSize(new Dimension(500,500));
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(bigPanel, c);
	    
	    c = new GridBagConstraints();
	    
	    smallPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    smallPanel.setMinimumSize(new Dimension(250,500));
	    
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(smallPanel, c);
	    
	    c = new GridBagConstraints();
	    
	    textPane.setEditable(false);
	    textPane.setPreferredSize(new Dimension(400,200));
	    textPane.putClientProperty("IgnoreCharsetDirective", Boolean.TRUE);
	    
	    scroll = new JScrollPane (textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 2;
	    c.ipady = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(scroll, c);
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
		}
	}
	
	private void processFirstEvent(){
		String text = events.remove(0);
		
		if (text.equals("$$REFRESHDISPLAY")){
			// redraw panels
		} else {
			writeToTextPane(text);
		}
	}
	
	private void writeToTextPane(String text){
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, 16);
		
		text = text + System.getProperty("line.separator");
		
		StyledDocument doc = textPane.getStyledDocument();
		
		try { doc.insertString(doc.getLength(), text, style); }
        catch (BadLocationException e){}
		
		frame.pack();
		
		textPane.setCaretPosition(textPane.getDocument().getLength());
		scroll.revalidate();
	}
	
	private void waitForInput(){
		textPane.requestFocusInWindow();
		
		waitingForInput = true;
		
	}
	
	@Override
	public void proceed(){
		if (waitingForInput){
			processEventQueue();
		}
	}
	
	

}
