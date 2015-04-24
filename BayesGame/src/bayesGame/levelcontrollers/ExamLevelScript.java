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

package bayesGame.levelcontrollers;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import bayesGame.bayesbayes.OptionNode;
import bayesGame.bayesbayes.OptionNodeOption;
import bayesGame.bayesbayes.nodeCPD.DeterministicAND;
import bayesGame.bayesbayes.nodeCPD.DeterministicNot;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.MajorityVote;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.MinigameController;
import bayesGame.viewcontrollers.DefaultViewController;
import bayesGame.viewcontrollers.ViewController;

public class ExamLevelScript extends Script {

	private LevelController controller;
	
	public ExamLevelScript() {
		controller = new LevelController();
		run();
	}

	public ExamLevelScript(LevelController controller) {
		this.controller = controller;
	}

	public void run() {
		
		/*
		DiscussionNet net = new DiscussionNet();
	
		net.addNode("Good mood");
		net.addNode("Kind");
		net.addNode("Busy");
		net.addNode("Has extra time", new DeterministicNot(), "Busy");
		net.addNode("Helpful", new DeterministicOR(), "Kind", "Good mood");
		net.addNode("Do you have a chance to help me out?", new DeterministicAND(), "Helpful", "Has extra time");
		
		net.addNode("Proud");
		net.addNode("Receptive to help", new DeterministicNot(), "Proud");
		net.addNode("You seem busy, can I help you?", new DeterministicAND(), "Busy", "Receptive to help");
		
		Set<Object> responseTargets = new HashSet<Object>();
		responseTargets.add("Do you have a chance to help me out?");
		responseTargets.add("You seem busy, can I help you?");
		
		MinigameController responseMinigameController = new MinigameController(net, responseTargets);
		responseMinigameController.setHiddenNodes(responseTargets);
		
		net.updateBeliefs();
		
		controller.addMinigame(responseMinigameController, 0, new Object[]{""});
		controller.run();

		
		*/
		

		
		
		
		
		
		controller.addText("I am nervous.");
		controller.addText("I’m standing at the gates of the Academy, the school where my brother Opin was studying when he disappeared. When we asked the school to investigate, they were oddly reluctant, and told us to drop the issue.");
		controller.addText("The police were more helpful at first, until they got in contact with the school. Then they actually started threatening us, and told us that we would get thrown in prison if we didn’t forget about Opin.");
		controller.addText("That was three years ago. Ever since it happened, I’ve been studying hard to make sure that I could join the Academy once I was old enough, to find out what exactly happened to Opin. The answer lies somewhere inside the Academy gates, I’m sure of it.");
		controller.addText("Now I’m finally 16, and facing the Academy entrance exams. I have to do everything I can to pass them, and I have to keep my relation to Opin a secret, too.");
		controller.addText("???: 'Hey there.'");
		controller.addText("Eep! Someone is talking to me! Is he another applicant, or a staff member? Wait, let me think… I’m guessing that applicant would look a lot younger than staff members! So, to find that out… I should look at him!");
		controller.addText("[You are trying to figure out whether the voice you heard is a staff member or another applicant. While you can't directly observe his staff-nature, you believe that he'll look young if he's an applicant, and like an adult if he's a staff member. You can look at him, and therefore reveal his staff-nature, by right-clicking on the node representing his apperance.]");
		controller.addProcessEventQueue();
		
		// viewController.dispose();
		
		DiscussionNet net = new DiscussionNet();
		net.addNode("Student", true);
		
		OptionNode youngLooking = new OptionNode("Young-looking");
		OptionNodeOption lookHim = new OptionNodeOption("Look at him");
		lookHim.setPositiveResponse("Short for an adult, face has boyish look, teenagerish clothes... yeah, he looks young!");
		lookHim.setTimeSpent(0);
		youngLooking.addOption(lookHim);
		net.addNode(youngLooking, new DeterministicOR(), "Student");
		net.setTrueValue("Young-looking", true);
		
		Set<Object> targets = new HashSet<Object>();
		targets.add("Student");
		
		net.updateBeliefs();
		
		MinigameController minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("He's young, so he has to be a student!");
		controller.addText("...I feel like I’m overthinking things now.");
		controller.addText("...he’s looking at me.");
		controller.addText("I’m guessing he’s either waiting for me to respond, or there’s something to see behind me, and he’s actually looking past me. If there isn’t anything behind me, then I know that he must be waiting for me to respond!");
		controller.addText("Maybe there's a monster behind me, and he's paralyzed with fear! I should check that possibility before it eats me!");
		controller.addText("[You want to find out whether the boy is waiting for your reply or staring at a monster behind you. You know that he's looking at you, and your model of the world suggests that he will only look in your direction if he's waiting for you to reply, or if there's a monster behind you. So if there's no monster behind you, you know that he's waiting for you to reply!]");
		controller.addProcessEventQueue();
		
		DiscussionNet responseNet = new DiscussionNet();
		
		responseNet.addNode("Waiting for you", true);
		
		OptionNode monster = new OptionNode("Attacking monster");
		OptionNodeOption behindYou = new OptionNodeOption("Look behind you");
		behindYou.setNegativeResponse("You see nothing there. Besides trees, that is.");
		behindYou.setTimeSpent(0);
		monster.addOption(behindYou);
		responseNet.addNode(monster);
		responseNet.setTrueValue("Attacking monster", false);
		
		responseNet.addDeterministicOr("Looks at you", "Waiting for you", "Attacking monster");
		responseNet.observe("Looks at you", true);
		
		Set<Object> responseTargets = new HashSet<Object>();
		responseTargets.add("Waiting for you");
		
		MinigameController responseMinigameController = new MinigameController(responseNet, responseTargets);
		responseMinigameController.setHiddenNodes(responseTargets);
		
		controller.addMinigame(responseMinigameController, 0, new Object[]{""});
		
		controller.addText("Boy: 'Umm, are you okay?'");
		controller.addText("'Yeah, sorry. I just… you were looking in my direction, and I wasn’t sure of whether you were expecting me to reply, or whether there was a monster behind me.'");
		controller.addText("He blinks.");
		controller.addText("'You thought that there was a reasonable chance for a monster to be behind you?'");
		controller.addText("'Yeah...'");
		controller.addText("I’m embarrassed to admit it, but I’m not really sure of what the probability of a monster having snuck up behind me really should have been.");
		controller.addText("My studies have entirely focused on getting into this school, and Monsterology isn’t one of the subjects on the entrance exam!");
		controller.addText("I just went with a 50-50 chance since I didn’t know any better.");
		controller.addText("'Okay, look. Monsterology is my favorite subject. Monsters avoid the Academy, since it’s surrounded by a mystical protective field. There’s no chance of them getting even near! 0 percent chance.'");
		controller.addText("'Oh. Okay.'");
		controller.addText("[Your model of the world has been updated! The prior of the variable 'Monster Near The Academy' is now 0%.]");
		
		controller.addText("Stuff happens");
		controller.addText("AAAAAAH! A MONSTER BEHIND ME!");
		controller.addText("Huh, the monster is carrying a sword.");
		controller.addText("Well, I may not have studied Monsterology, but I sure did study fencing!");
		
		controller.addText("[You draw your sword. Seeing this, the monster rushes at you.]");
		controller.addText("He looks like he's going to strike. But is it really a strike, or is it a feint?");
		controller.addText("If it's a strike, I want to block and counter-attack. But if it's a feint, that leaves him vulnerable to my attack.");
		controller.addText("I have to choose wisely. If I make the wrong choice, I may be dead.");
		controller.addText("What did my master say? If the opponent has at least two of dancing legs, an accelerating midbody, and ferocious eyes, then it's an attack!");
		controller.addText("Otherwise it's a feint! Quick, I need to read his body language before it's too late!");
		controller.addText("[Now for some actual decision-making! The node in the middle represents the monster's intention to attack (or to feint, if it's false). Again, you cannot directly observe his intention, but on the top row, there are things about his body language that signal his intention. If at least two of them are true, then he intends to attack.]");
		controller.addText("[Your possible actions are on the bottom row. If he intends to attack, then you want to block, and if he intends to feint, you want to attack. You need to inspect his body language and then choose an action based on his intentions. But hurry up! Your third decision must be an action, or he'll slice you in two!]");
		controller.addProcessEventQueue();
		
		DiscussionNet battleNet = new DiscussionNet();
		
		OptionNode dancingLegs = new OptionNode("Dancing legs");
		OptionNode acceleratingBody = new OptionNode("Accelerating midbody");
		OptionNode ferociousEyes = new OptionNode("Ferocious eyes");
		
		OptionNodeOption thinkLegs = new OptionNodeOption("Think of your legs");
		thinkLegs.setPositiveResponse("You trust your instincts to be automatically mirroring his leg movements, and just for a brief second, you take your attention off your opponent and think of how your legs are moving. They are dancing! That means his legs must be dancing as well!");
		thinkLegs.setNegativeResponse("You trust your instincts to be automatically mirroring his leg movements, and just for a brief second, you take your attention off your opponent and think of how your legs are moving. They are dashing around, but not dancing! Then his legs must not be dancing either!");
		
		OptionNodeOption feelLegs = new OptionNodeOption("Feel your legs");
		feelLegs.addRequirement("Fencing 2");
		feelLegs.setTimeSpent(0);
		
		dancingLegs.addOption(thinkLegs);
		dancingLegs.addOption(feelLegs);
		
		OptionNodeOption lookBody = new OptionNodeOption("Look at his midbody");
		lookBody.setPositiveResponse("You take a moment to look at his midbody. Even though you only look at it for one third of a second, during that time its velocity increases from five to six meters per second! It's accelerating alright!");
		lookBody.setNegativeResponse("You take a moment to look at his midbody. Even though you only look at it for one third of a second, during that time its velocity decreases from eight to five meters per second! That's not accelerating, it's decelerating!");
		acceleratingBody.addOption(lookBody);
		
		OptionNodeOption matchGazes = new OptionNodeOption("Match your gaze with his");
		matchGazes.setPositiveResponse("You try to look him in the eyes, but his gaze is so ferocious that you are forced to look away!");
		matchGazes.setNegativeResponse("You look him the eyes, and get a playful look back.");
		OptionNodeOption feelGaze = new OptionNodeOption("Feel the nature of his gaze");
		feelGaze.addRequirement("Empathy 1");
		feelGaze.setTimeSpent(0);
		
		ferociousEyes.addOption(matchGazes);
		ferociousEyes.addOption(feelGaze);
		
		battleNet.addNode(dancingLegs);
		battleNet.addNode(acceleratingBody);
		battleNet.addNode(ferociousEyes);
		
		Random rn = new Random();
		int answer = rn.nextInt(2);
		boolean bool = (answer == 0);
		
		battleNet.setTrueValue("Dancing legs", bool);
		battleNet.setTrueValue("Accelerating midbody", bool);
		battleNet.setTrueValue("Ferocious eyes", bool);
		
		battleNet.addNode("Attacking", new MajorityVote(), "Dancing legs", "Accelerating midbody", "Ferocious eyes");
		battleNet.setTrueValue("Attacking", bool);
		
		battleNet.addNode("Block", new DeterministicOR(), "Attacking");
		battleNet.setTrueValue("Block", bool);
		
		battleNet.addNode("Attack", new DeterministicNot(), "Attacking");
		battleNet.setTrueValue("Attack", !bool);
		
		Set<Object> battleTargets = new HashSet<Object>();
		battleTargets.add("Block");
		battleTargets.add("Attack");
		
		Set<Object> hidden = new HashSet<Object>();
		hidden.add("Attacking");
		
		MinigameController battleController = new MinigameController(battleNet, battleTargets);
		battleController.setGameMode(1);
		battleController.setHiddenNodes(hidden);
		battleController.setFailureResult("Restart");
		battleController.setSuccessResult(new WelcomeToSchoolScript(controller));
		
		controller.addMinigame(battleController, 3, new Object[]{""});
		
		controller.run();
	}



	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}
	
	

}
