package bayesGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.OptionNode;
import bayesGame.bayesbayes.OptionNodeOption;
import bayesGame.bayesbayes.nodeCPD.DeterministicNot;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.MajorityVote;
import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.ChoiceMenuChoice;
import bayesGame.levelcontrollers.ExamLevelScript;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.LoopScript;
import bayesGame.levelcontrollers.Script;
import bayesGame.levelcontrollers.TutorialController;
import bayesGame.levelcontrollers.TutorialLevel2Controller;
import bayesGame.levelcontrollers.WelcomeToSchoolScript;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.MinigameController;
import bayesGame.ui.ColorSelection;
import bayesGame.ui.LanguageChooser;
import bayesGame.world.GameCharacters;
import bayesGame.world.TutorialMessages;
import bayesGame.world.World;

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
	
	/*
	public static String falseColorName = "red";
	public static String trueColorName = "blue";
	public static String unknownColorName = "white";
	public static String falseColorDisplayedName = "red";
	public static String trueColorDisplayedName = "blue";
	public static Color falseColor = Color.RED;
	public static Color trueColor = Color.BLUE;
	*/
	
	public static String falseColorName = "red";
	public static String trueColorName = "green";
	public static String unknownColorName = "white";
	public static String falseColorDisplayedName = "red";
	public static String trueColorDisplayedName = "green";
	public static Color falseColor = Color.RED;
	public static Color trueColor = Color.GREEN;
	
	public static Color unknownColor = Color.WHITE;
	
	public static Locale currentLocale;
	
	public static World world;
	public static GameCharacters gameCharacters;
	public static TutorialMessages tutorialMessages;
	public static LevelController controller;
	
	private static JFrame frame;

	public static void main(String[] args) {
		world = new World();
		gameCharacters = new GameCharacters();
		tutorialMessages = new TutorialMessages();
		
		controller = new LevelController();
		ChoiceMenu mainMenu = new ChoiceMenu();
		ChoiceMenuChoice intro = new ChoiceMenuChoice("Play introduction", new ExamLevelScript(controller));
		ChoiceMenuChoice game = new ChoiceMenuChoice("Play game", new LoopScript(controller));
		ChoiceMenuChoice options = new ChoiceMenuChoice("Options", new OptionsScript(controller));
		// mainMenu.addChoice(intro);
		mainMenu.addChoice(game);
		mainMenu.addChoice(options);
		controller.addChoiceMenu(mainMenu);
		controller.run();
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
		

		
	}
	
	public static void beginTutorial(int level){
		// frame.dispose();
		
		DiscussionNet net = new DiscussionNet();
		
		/*
		net.addNode("A");
		net.addNode("B");
		net.addNode("L");
		net.addDeterministicOr("C", "A", "L");
		net.addDeterministicOr("D", new Object[]{"A", "B"});
		net.addNode("J");
		net.addDeterministicOr("E", "B", "J");
		net.addNode("K");
		net.addDeterministicOr("F", "D", "K");
		net.addDeterministicOr("G", "D");
		net.addNode("H");
		net.addDeterministicOr("I", new Object[]{"G", "H"});
		
		Set<Object> targets = new HashSet<Object>();
		
		MinigameController controller = new MinigameController(net, targets);
		controller.randomizeHiddenNodes(4);
		controller.startGame(10, new Object[]{""});
		
		*/
		
		
		
		
		/*
		OptionNode girls = new OptionNode("Likes girls");
		
		OptionNodeOption flirty = new OptionNodeOption("Be flirty");
		flirty.setPositiveResponse("He smiles in reaction to your subtle flirtation. You think he likes girls.");
		flirty.setNegativeResponse("Your subtle flirtation doesn't elicit any response. You think he's not interested in girls.");
		girls.addOption(flirty);

		OptionNodeOption eyes = new OptionNodeOption("Look at his eyes");
		eyes.setPositiveResponse("His pupils become slightly widened as he looks at your face. He seems to think you're pretty.");
		eyes.setNegativeResponse("He looks at you indifferently. He doesn't seem to be particularly attracted to you.");
		eyes.addRequirement("Empathy 1");
		eyes.setTimeSpent(0);
		girls.addOption(eyes);
		
		net.addNode(girls);
		
		
		OptionNode time = new OptionNode("Has time");
		
		OptionNodeOption askrush = new OptionNodeOption("Are you in a rush?");
		askrush.setPositiveResponse("Kind of, yeah.");
		askrush.setNegativeResponse("No, I'm not in a hurry anywhere.");
		time.addOption(askrush);

		OptionNodeOption body = new OptionNodeOption("Read his body language");
		body.setPositiveResponse("He looks at you politely, but he's kind of fidgeting and keeps glancing above your shoulder.");
		body.setNegativeResponse("He appears calm and relaxed.");
		body.addRequirement("Empathy 1");
		body.setTimeSpent(0);
		time.addOption(body);
		
		net.addNode(time);
		
		
		OptionNode kind = new OptionNode("Kind");
		
		OptionNodeOption looks = new OptionNodeOption("How do I look?");
		looks.setPositiveResponse("You could maybe work on your hair a bit.");
		looks.setNegativeResponse("Pretty terrible.");
		kind.addOption(looks);
		
		net.addNode(kind);
		
		
		net.addNode("M: Willing to help", new MajorityVote(), "Likes girls", "Has time", "Kind");
		
		net.addNode("OR: Ask nicely", new DeterministicOR(), "M: Willing to help");
		net.addNode("NOT: Threaten with sword", new DeterministicNot(), "M: Willing to help");
		
		Set<Object> targets = new HashSet<Object>();
		targets.add("OR: Ask nicely");
		targets.add("NOT: Threaten with sword");
		
		Set<Object> hidden = new HashSet<Object>();
		hidden.add("M: Willing to help");
		// hidden.add("Ask nicely");
		// hidden.add("Threaten with sword");
		
		MinigameController controller = new MinigameController(net, targets);
		controller.setGameMode(1);
		controller.setHiddenNodes(hidden);
		controller.startGame(5, new Object[]{""});
		*/
		
		ExamLevelScript exam = new ExamLevelScript();
		
		
		
		
		
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
