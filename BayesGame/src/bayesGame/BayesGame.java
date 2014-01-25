package bayesGame;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bayesGame.levelcontrollers.TutorialController;
import bayesGame.ui.ColorSelection;

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
	public static Color falseColor = Color.RED;
	public static Color trueColor = Color.GREEN;
	public static Color unknownColor = Color.WHITE;
	
	private static JFrame frame;

	public static void main(String[] args) {
		
		frame = new JFrame("Select colors");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JComponent newContentPane = new ColorSelection();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
	}
	
	public static void beginTutorial(){
		
		frame.dispose();
		TutorialController tutorial = new TutorialController();
		
	}
	


}
