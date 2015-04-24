package bayesGame.levelcontrollers;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.events.SpokeAboutJacenEvent;
import bayesGame.world.GameCharacter;
import bayesGame.world.GameCharacters;
import bayesGame.world.World;

public class MeetingCherylScript extends Script {
	
	public static boolean metCheryl = false;




	@Override
	public void run() {
		if (metCheryl){
			new LoopScript().run();
		} else {
			final GameCharacter cheryl = new GameCharacter("Cheryl");
			GameCharacters.add(cheryl);
			
			controller.addText("As you're leaving the lecture, you spot a cheerful-looking girl who's picking up her books. She sees you and smiles.");
			controller.addText("Cheryl: 'Hey! I'm Cheryl. I saw you looking around to find this classroom when I came in, are you a first-year too?'");
			controller.addText("'Yeah.'");
			controller.addText("Cheryl: 'That's great!'");
			controller.addText("She leans closer to you and lowers her voice, grinning mischievously.");
			controller.addText("Cheryl: 'You met any of the boys yet? Anything juicy you could tell me about them?'");
			controller.addProcessEventQueue();
			controller.run();			
			
			MiniScript JacenScript = new MiniScript("Um... well, there's this boy Jacen who thought there could be no monsters near the academy...") {
				public void run() {
					World.insertEvent(2, new SpokeAboutJacenEvent());
					controller.addText("Cheryl: 'Really?' she grins. 'That's cute.'");
					getGossipFromCheryl();
					cheryl.befriend();
				}
			};
			
			MiniScript nopeScript = new MiniScript("Umm... sorry, not really.") {
				public void run() {
					controller.addText("Cheryl: 'Oh, okay. Well, the girls, then?'");
					controller.addDialog("Say to Cheryl", "...sorry.", "Nope.", "Nuh-huh.");
					controller.addText("Cheryl: 'Ah well.'");
					controller.addProcessEventQueue();
					controller.run();	
				}				
			};
			
			controller.addDialog("Say to Cheryl", JacenScript, nopeScript);

			
			controller.addText("She glances at her phone.");
			controller.addText("Cheryl: 'Oh hey, I gotta go. It was great talking with you, we should catch up later!'");
			
			
			
			
			
			
			
			metCheryl = true;
			
			new LoopScript().run();
		}

	}
	
	private void getGossipFromCheryl(){
		controller.addText("Cheryl: 'I heard there's this second-year, Leo, who's really cute and smart, but who's been afraid of talking to girls ever since he asked this Lisa girl out and Lisa laughed in his face. In public. But he's cool, so if you're looking for a boyfriend, you should check him out.'");
		controller.addText("'Um-'");
		controller.addText("Cheryl: 'Or if you're more into girls, there's this third-year, Meri. She's got a boyfriend now, but I know she's slept with a girl before, and I'm pretty sure that they're flexible about that kind of thing.'");
		controller.addText("'Er-'");
		controller.addText("Cheryl: 'Oh, do you have someone already? That's alright. It's still useful to know what your options are, in case you'd break up or want to add some spice to your life.'");
		controller.addProcessEventQueue();
		controller.run();
/*
		int choice = controller.showDialog("'Uhh... thanks.'", "'So, how about YOU?'", "'Just how do you know of all this? Didn't you just get here?'");
		if (choice == 0){
			controller.addText("Cheryl: 'Hey, no problem.' She beams at you. 'I'm always happy to help my friends when it comes to matters of love. Just let me know how it goes for you guys, or gals, or threesomes, or whoever. I don't care, I'm open-minded!'");
			controller.addText("You'd kind of noticed that already.");
		} else if (choice == 1){
			controller.addText("Cheryl: 'Oh, me?' She grins. 'Don't have anyone yet. Waiting for someone SPECIAL. Won't take just anyone.'");
			controller.addText("Yeah, you've got difficulties seeing this girl together with an ordinary person.");
		} else if (choice == 2){
			controller.addText("Cheryl: 'Oh, you need to know these things.' She beams, apparently happy that you asked. 'It's my special trade, finding out this kind of thing. If you hear anything you think I might be interested in, just let me know!'");
			controller.addText("As long as you don't mind half the school knowing it too...");
		}
		*/
	}




}
