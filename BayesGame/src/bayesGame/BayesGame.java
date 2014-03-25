package bayesGame;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.levelcontrollers.TutorialController;
import bayesGame.levelcontrollers.TutorialLevel2Controller;
import bayesGame.ui.ColorSelection;
import bayesGame.ui.DefaultInterfaceView;
import bayesGame.ui.LanguageChooser;

/**
 *    Copyright 2014 Kaj Sotala

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class BayesGame {
	
	public static String falseColorName = "red";
	public static String trueColorName = "green";
	public static String unknownColorName = "white";
	public static String falseColorDisplayedName = "red";
	public static String trueColorDisplayedName = "green";
	public static Color falseColor = Color.RED;
	public static Color trueColor = Color.GREEN;
	public static Color unknownColor = Color.WHITE;
	
	public static Locale currentLocale;
	
	private static JFrame frame;

	public static void main(String[] args) {
		
	    // showLanguageSelector();
		beginTutorial(2);
	}
	
	public static void showLanguageSelector(){
		
		currentLocale = Locale.getDefault();
		
		frame = new JFrame("Select language");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JComponent newContentPane = new LanguageChooser();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
		
	}
	
	public static void showColorSelector(){
		
		frame.dispose();
		
		frame = new JFrame("Select colors");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JComponent newContentPane = new ColorSelection();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
		
	}
	
	public static void beginTutorial(int level){
		
		DefaultInterfaceView ui = new DefaultInterfaceView();
		BayesNet net = new BayesNet();

		net.addNode("Dad");
		net.addNode("Mom");
		net.addDeterministicOr("Opin", "Mom", "Dad");
		
		ui.setGraph(net);
		ui.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
		
		
		
		/*
		if (level == 1){
			frame.dispose();
			TutorialController tutorial = new TutorialController();
		}
		
		if (level == 2){
			TutorialLevel2Controller tutorial = new TutorialLevel2Controller();
		}
		
	*/	
	}
	


}
