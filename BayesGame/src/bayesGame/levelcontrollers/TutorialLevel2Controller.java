package bayesGame.levelcontrollers;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.ui.DefaultInterfaceView;
import bayesGame.ui.swinglisteners.AnyKeyListener;

public class TutorialLevel2Controller extends Controller {
	
	private DefaultInterfaceView UI;
	private int level = 0;
	private int part = 0;
	
	private BayesNet net;
	
	private boolean awaitingkeypresses;
	private boolean awaitingmousemessage;

	public TutorialLevel2Controller() {
		UI = new DefaultInterfaceView();
		UI.addKeyListener(new AnyKeyListener(this));
		awaitingkeypresses = true;
		awaitingmousemessage = false;
		advanceTutorial();
	}
	
	private void advanceTutorial(){
		switch(level){
		case 0:
			introChat();
			break;
		}
	}
	
	private void introChat(){
		switch(part){
		case 0:
			net = new BayesNet();
			
			net.addNode("Mailman");
			net.addDeterministicOr("Dad", "Mailman");
			net.addNode("Mom");
			net.addDeterministicOr("Opin", "Mom", "Dad");

			net.observe("Mom", false);
			net.observe("Mailman", true);
			net.updateBeliefs();
			net.observe("Dad");
			net.observe("Opin");

			UI.setGraph(net);
			UI.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
			
			UI.addTextMore("Celia: Okay, so we know that dad heard about the treasure from the mailman. And didn't know anything else about the treasure, just that the mailman had spoken about it. So we should find out how the mailman knows about it!");
			break;
		case 1:
			UI.addTextMoreClear("Opin: Right. So, our house is the fourth one that the mailman visits every morning, so that's three people that the mailman might have spoken with before.");
			
			net.addNode("Neighbor 1");
			net.addNode("Neighbor 2");
			net.addNode("Neighbor 3");
			net.forceConnectNodes("Neighbor 1", "Mailman");
			net.forceConnectNodes("Neighbor 2", "Mailman");
			net.forceConnectNodes("Neighbor 3", "Mailman");
			net.makeDeterministicOr("Mailman");
			net.observe("Mailman", true);
			
			UI.redrawGraph();
			break;
		case 2:
			UI.addTextMoreClear("Opin: And we also know that your mom will always talk with Neighbor 3 about the latest magical vibrations in runestones, right in the morning before the mailman has even made his rounds yet...");
			
			net.forceConnectNodes("Neighbor 3", "Mom");
			net.makeDeterministicOr("Mom");
			net.observe("Mom", false);
			
			UI.redrawGraph();
			break;
		case 3:
			UI.addTextMoreClear("Celia: We do?");
			break;
		case 4:
			UI.addTextMoreClear("Opin: Indeed we do! It's useful to know as much as you can about the people you live with, so I've made it a habit to learn stuff like this. And now it pays off!");
			break;
		case 5:
			UI.addTextClear("Opin: I'm pretty sure that Neighbor 3 would have told Mom about the treasure, if she'd known about it. So what does that tell us about the things that Neighbor 3 knows?");
			UI.addTutorialText("As before, choose the state that this implies with the left mouse button, and then right-click.");
			break;
		case 6:
			net.observe("Neighbor 3", false);
			
			UI.updateGraph();
			UI.addTextMoreClear("Opin: Right! Now, I happen to know that the people in this village have very regular habits, so assuming that this was an ordinary morning, the whole picture of who has been talking to who looks like this...");
			break;
		case 7:
			net.addNodes("4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16");
			net.forceConnectNodes("4", "Neighbor 3");
			net.forceConnectNodes("5", "4");
			net.forceConnectNodes("5", "6");
			net.forceConnectNodes("7", "4");
			net.forceConnectNodes("9", "7");
			net.forceConnectNodes("9", "10");
			net.forceConnectNodes("10", "8");
			net.forceConnectNodes("10", "11");
			net.forceConnectNodes("7", "8");
			net.forceConnectNodes("8", "Neighbor 2");
			net.forceConnectNodes("11", "Neighbor 1");
			net.forceConnectNodes("12", "10");
			net.forceConnectNodes("13", "12");
			net.forceConnectNodes("14", "12");
			net.forceConnectNodes("15", "13");
			net.forceConnectNodes("16", "13");
			net.forceConnectNodes("16", "14");
			
			net.observe("Neighbor 3", false);
			
			UI.redrawGraph();
			
			UI.addTextMoreClear("Celia: ...you've got to be kidding me.");
			break;
		case 8:
			UI.addTextMoreClear("Opin: Huh?");
			break;
		case 9:
			UI.addTextMoreClear("Celia: How do you know all this?");
			break;
		case 10:
			UI.addTextMoreClear("Opin: Knowledge is power! Like I told you, I find it useful to collect information that may come in handy one day. Like now.");
			break;
		case 11:
			UI.addTextMoreClear("Celia: Right. So what do we do now?");
			break;
		case 12:
			UI.addTextMoreClear("Opin: Well, we could just talk to everyone. But that would take a lot of time, and somebody else might find the treasure in the meanwhile! So how about we figure out who are the people that might know about it, and who are the people who can't know about it. If we do that, we can save a lot of time.");
			break;
		case 13:
			UI.addTextMoreClear("Celia: Hmm, okay. So, how does this work...");
			break;
		case 14:
			UI.clearText();
			UI.addTutorialText("Use what you've learned to make guesses of what everyone knows. The people that you have enough information to make a guess of are highlighted: left-click on any of them to switch between 'knows', 'does not know', and 'might know'. Right-click to check your guess. Try to get as few guesses wrong as you can.");			
		}
		part++;
	}
	
	

	@Override
	public void keyMessage(Object o) {
		if (awaitingkeypresses){
			advanceTutorial();
		}

	}

	@Override
	public void mouseMessage(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void genericMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void genericMessage(Object o) {
		// TODO Auto-generated method stub

	}

}
