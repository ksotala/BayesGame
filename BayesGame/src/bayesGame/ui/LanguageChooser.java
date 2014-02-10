package bayesGame.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import bayesGame.BayesGame;

public class LanguageChooser extends JPanel implements ActionListener {
	
	private Locale chosenLocale = new Locale("en");

	public LanguageChooser() {
		super(new GridLayout(0, 1));
		
		JRadioButton englishButton = new JRadioButton("English");
		JRadioButton finnishButton = new JRadioButton("Suomi");
		
		englishButton.setMnemonic(KeyEvent.VK_1);
		englishButton.setActionCommand("en");
		englishButton.setFont(new Font("Serif", Font.PLAIN, 22));
		englishButton.setSelected(true);
		
		finnishButton.setMnemonic(KeyEvent.VK_2);
		finnishButton.setActionCommand("fi");
		finnishButton.setFont(new Font("Serif", Font.PLAIN, 22));
		
		englishButton.addActionListener(this);
		finnishButton.addActionListener(this);
		
		JButton closeButton = new JButton();
		closeButton.setText("OK");
		closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeButtonPushed();
            }
        });
		
		add(englishButton);
		add(finnishButton);
		add(closeButton);
		
		
		// TODO Auto-generated constructor stub
	}

	
	private void closeButtonPushed(){
		BayesGame.currentLocale = chosenLocale;
		BayesGame.showColorSelector();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		chosenLocale = new Locale(e.getActionCommand());		
	}



}
