package bayesGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Kaj Sotala
 * 
 * The default interface for the game. 
 *
 */
public class DefaultInterfaceView {

	private JFrame frame;
	private JLabel environmentImage;
	private JLabel textField;
	
	
	public DefaultInterfaceView() throws IOException {
		
		frame = new JFrame("Academy Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponentsToPane(frame.getContentPane());
		
		frame.pack();
		frame.setVisible(true);
				
	}
	
	private void addComponentsToPane(Container pane) throws IOException {
		
		JButton button;
		GridBagConstraints c;
		
		pane.setLayout(new GridBagLayout());
	    
		c = new GridBagConstraints();
	    
	    BufferedImage myPicture = ImageIO.read(new File("Images/Castle.png"));
	    JLabel pictureLabel = new JLabel(new ImageIcon(myPicture));
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.NONE;
	    pane.add(pictureLabel, c);
	    
	    c = new GridBagConstraints();
	    button = new JButton("Button 2");
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 300;
	    c.ipadx = 300;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(button, c);
	    
	    c = new GridBagConstraints();
	    button = new JButton("Button 3");
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 2;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(button, c);
		
	    c = new GridBagConstraints();
	    JLabel textLabel = new JLabel("Text label");
	    c.gridx = 0;
	    c.gridy = 2;
	    c.gridwidth = 2;
	    c.ipady = 300;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(textLabel, c);
		
	}
	
	public void setVisible(boolean visible){
		
		frame.setVisible(visible);
		
	}
	
	public void setText(String text){
		
		textField.setText(text);

	}
	
	

}
