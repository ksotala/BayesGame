package bayesGame.levelcontrollers;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.events.SpokeAboutJacenEvent;
import bayesGame.world.GameCharacter;
import bayesGame.world.GameCharacters;
import bayesGame.world.World;

public class MeetingCherylScript extends Script {
	
	public static boolean metCheryl = false;
	public static boolean befriendedCheryl = false;
	
	private final GameCharacter gossip = GameCharacters.createCharacter(GameCharacter.GENDER.GIRL);




	@Override
	public void run() {
		if (metCheryl){
			new LoopScript().run();
		} else {
			GameCharacters.add(gossip);
			
			controller.addText("As you're leaving the lecture, you spot a cheerful-looking girl who's picking up her books. She sees you and smiles.");
			controller.addText(gossip + ": 'Hey! I'm " + gossip + ". I saw you looking around to find this classroom when I came in, are you a first-year too?'");
			controller.addText("'Yeah.'");
			controller.addText(gossip + ": 'That's great!'");
			controller.addText("She leans closer to you and lowers her voice, grinning mischievously.");
			controller.addText(gossip + ": 'You met any of the boys yet? Anything juicy you could tell me about them?'");
			controller.addProcessEventQueue();
			controller.run();			
			
			MiniScript JacenScript = new MiniScript("Um... well, there's this boy Jacen who thought there could be no monsters near the academy...") {
				public void run() {
					World.insertEvent(2, new SpokeAboutJacenEvent());
					controller.addText(name);
					controller.addText(gossip + ": 'Really?' she grins. 'That's cute. Thanks. Lemme give you some hints in return.'");
					getGossipFromCheryl();
					gossip.befriend();
					befriendedCheryl = true;
				}
			};
			
			MiniScript nopeScript = new MiniScript("Umm... sorry, not really.") {
				public void run() {
					controller.addText(name);
					controller.addText(gossip + ": 'Oh, okay. Well, the girls, then?'");
					controller.addText("'...sorry.'");
					controller.addText(gossip + ": 'Ah well.'");
					controller.addProcessEventQueue();
					controller.run();	
					Finish();
				}				
			};
			
			controller.addDialog("Say to " + gossip, JacenScript, nopeScript);
		}

	}
	
	private void Finish(){
		controller.addText("She glances at her phone.");
		controller.addText(gossip + ": 'Oh hey, I gotta go. It was great talking with you, we should catch up later!'");
		
		if (befriendedCheryl){
			controller.addText("You have befriended " + gossip + "!");
		}
		
		controller.addProcessEventQueue();
		controller.run();
		
		metCheryl = true;
		
		new LoopScript().run();
	}
	
	private void getGossipFromCheryl(){
		GameCharacter boy = GameCharacters.createCharacter(GameCharacter.GENDER.BOY);
		GameCharacter cruelGirl = GameCharacters.createCharacter(GameCharacter.GENDER.GIRL);
		GameCharacter biGirl = GameCharacters.createCharacter(GameCharacter.GENDER.GIRL);
		
		controller.addText(gossip + ": 'I heard there's this second-year, " + boy + ", who's really cute and smart, but who's been afraid of talking to girls ever since he asked this " + cruelGirl + " girl out and she laughed in his face. In public. But he's cool, so if you're looking for a boyfriend, you should check him out.'");
		controller.addText("'Um-'");
		controller.addText(gossip + ": 'Or if you're more into girls, there's this other second-year, " + biGirl + ". She's got a boyfriend now, but I'm sure that they're flexible about that kind of thing. I know that she's slept with a girl before.'");
		controller.addText("'Er-'");
		controller.addText(gossip + ": 'Oh, do you have someone already? That's alright. It's still useful to know what your options are, in case you'd break up or want to add some spice to your life.'");
		controller.addText("'So uhh...'");
		controller.addProcessEventQueue();
		controller.run();

		MiniScript thanks = new MiniScript("'Uhh... thanks.'"){
			public void run(){
				controller.addText(name);
				controller.addText(gossip + ": 'Hey, no problem.' She beams at you. 'I'm always happy to help my friends when it comes to matters of love. Just let me know how it goes for you guys, or gals, or threesomes, or whoever. I don't care, I'm open-minded!'");
				controller.addText("You'd kind of noticed that already.");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		MiniScript you = new MiniScript("'So, how about YOU?'"){
			public void run(){
				controller.addText(name);
				controller.addText(gossip + ": 'Oh, me?' She grins. 'Don't have anyone yet. Waiting for someone SPECIAL. Won't take just anyone.'");
				controller.addText("Yeah, you've got difficulties seeing this girl together with an ordinary person.");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		MiniScript know = new MiniScript("'Just how do you know of all this? Didn't you just get here?'"){
			public void run(){
				controller.addText(name);
				controller.addText(gossip + ": 'Oh, you need to know these things.' She beams, apparently happy that you asked. 'It's my special trade, finding out this kind of thing. If you hear anything you think I might be interested in, just let me know!'");
				controller.addText("As long as you don't mind half the school knowing it too...");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		
		controller.addDialog("Say to " + gossip, thanks, you, know);
		controller.addProcessEventQueue();
		controller.run();
		


	}




}
