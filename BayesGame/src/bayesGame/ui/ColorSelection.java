package bayesGame.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import bayesGame.BayesGame;

public class ColorSelection extends JPanel implements ActionListener {
	
	private ResourceBundle messages = ResourceBundle.getBundle("bayesGame.languagefiles.ColorBundle", BayesGame.currentLocale);
	
	private String option1truecolor = "green";
	private String option1falsecolor = "red";
	private String option1displayedtruecolor = messages.getString(option1truecolor);
	private String option1displayedfalsecolor = messages.getString(option1falsecolor);
	private Color option1trueColor = Color.GREEN;
	private Color option1falseColor = Color.RED;
	
	private String option2truecolor = "purple";
	private String option2falsecolor = "red";
	private String option2displayedtruecolor = messages.getString(option2truecolor);
	private String option2displayedfalsecolor = messages.getString(option2falsecolor);
	private Color option2trueColor = Color.decode("#A52A2A");
	private Color option2falseColor = Color.RED;
	
	private String option3truecolor = "blue";
	private String option3falsecolor = "red";
	private String option3displayedtruecolor = messages.getString(option3truecolor);
	private String option3displayedfalsecolor = messages.getString(option3falsecolor);
	private Color option3trueColor = Color.BLUE;
	private Color option3falseColor = Color.RED;
	
	private String option4truecolor = "blue";
	private String option4falsecolor = "yellow";
	private String option4displayedtruecolor = messages.getString(option4truecolor);
	private String option4displayedfalsecolor = messages.getString(option4falsecolor);
	private Color option4trueColor = Color.BLUE;
	private Color option4falseColor = Color.YELLOW;

	public ColorSelection() {
		super(new GridLayout(0, 1));
		
		JLabel explanationLabel = new JLabel("This game uses colors to indicate things being true or false. By default, green stands for true and red stands for false.");
		JLabel explanationLabel2 = new JLabel("However, in case you're color blind or just don't happen to like red and green, you can choose between different color sets here.");
		explanationLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		explanationLabel2.setFont(new Font("Serif", Font.PLAIN, 22));

		String prefixText = "<html>True font color: <font color=";
		String middleText = "</font> False font color: <font color=";
		
		String defaultText = prefixText + option1truecolor + ">" + option1displayedtruecolor + middleText + option1falsecolor + ">" + option1displayedfalsecolor + "</font>";
		JRadioButton defaultButton = new JRadioButton(defaultText);
		defaultButton.setMnemonic(KeyEvent.VK_1);
		defaultButton.setActionCommand("option1");
		defaultButton.setSelected(true);
		defaultButton.setFont(new Font("Serif", Font.PLAIN, 22));
		
		String option2Text = prefixText + option2truecolor + ">" + option2displayedtruecolor + middleText + option2falsecolor + ">" + option2displayedfalsecolor + "</font>";
		JRadioButton option2Button = new JRadioButton(option2Text);
		option2Button.setMnemonic(KeyEvent.VK_2);
		option2Button.setActionCommand("option2");
		option2Button.setFont(new Font("Serif", Font.PLAIN, 22));
		
		String option3Text = prefixText + option3truecolor + ">" + option3displayedtruecolor + middleText + option3falsecolor + ">" + option3displayedfalsecolor + "</font>";
		JRadioButton option3Button = new JRadioButton(option3Text);
		option3Button.setMnemonic(KeyEvent.VK_3);
		option3Button.setActionCommand("option3");
		option3Button.setFont(new Font("Serif", Font.PLAIN, 22));
		
		String option4Text = prefixText + option4truecolor + ">" + option4displayedtruecolor + middleText + option4falsecolor + ">" + option4displayedfalsecolor + "</font>";
		JRadioButton option4Button = new JRadioButton(option4Text);
		option4Button.setMnemonic(KeyEvent.VK_4);
		option4Button.setActionCommand("option4");
		option4Button.setFont(new Font("Serif", Font.PLAIN, 22));
		
		JButton closeButton = new JButton();
		closeButton.setText("OK");
		closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeButtonPushed();
            }
        });
		
		ButtonGroup group = new ButtonGroup();
		group.add(defaultButton);
		group.add(option2Button);
		group.add(option3Button);
		group.add(option4Button);
		
		defaultButton.addActionListener(this);
		option2Button.addActionListener(this);
		option3Button.addActionListener(this);
		option4Button.addActionListener(this);
		
		add(explanationLabel);
		add(explanationLabel2);
		add(defaultButton);
		add(option2Button);
		add(option3Button);
		add(option4Button);
		add(closeButton);
	}

	private void closeButtonPushed(){
		BayesGame.beginTutorial(1);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "option1":
			BayesGame.trueColorName = option1truecolor;
			BayesGame.falseColorName = option1falsecolor;
			BayesGame.trueColor = option1trueColor;
			BayesGame.falseColor = option1falseColor;
			BayesGame.trueColorDisplayedName = option1displayedtruecolor;
			BayesGame.falseColorDisplayedName = option1displayedfalsecolor;
			break;
		case "option2":
			BayesGame.trueColorName = option2truecolor;
			BayesGame.falseColorName = option2falsecolor;
			BayesGame.trueColor = option2trueColor;
			BayesGame.falseColor = option2falseColor;
			BayesGame.trueColorDisplayedName = option2displayedtruecolor;
			BayesGame.falseColorDisplayedName = option2displayedfalsecolor;
			break;
		case "option3":
			BayesGame.trueColorName = option3truecolor;
			BayesGame.falseColorName = option3falsecolor;
			BayesGame.trueColor = option3trueColor;
			BayesGame.falseColor = option3falseColor;
			BayesGame.trueColorDisplayedName = option3displayedtruecolor;
			BayesGame.falseColorDisplayedName = option3displayedfalsecolor;
			break;
		case "option4":
			BayesGame.trueColorName = option4truecolor;
			BayesGame.falseColorName = option4falsecolor;
			BayesGame.trueColor = option4trueColor;
			BayesGame.falseColor = option4falseColor;
			BayesGame.trueColorDisplayedName = option4displayedtruecolor;
			BayesGame.falseColorDisplayedName = option4displayedfalsecolor;
			break;
		}
	}

}
