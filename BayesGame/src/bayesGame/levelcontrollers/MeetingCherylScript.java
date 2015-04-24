package bayesGame.levelcontrollers;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.events.SpokeAboutJacenEvent;
import bayesGame.world.GameCharacter;
import bayesGame.world.GameCharacters;
import bayesGame.world.World;

public class MeetingCherylScript extends Script {
	
	public static boolean metCheryl = false;
	public static boolean befriendedCheryl = false;




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
					controller.addText(name);
					controller.addText("Cheryl: 'Really?' she grins. 'That's cute.'");
					getGossipFromCheryl();
					cheryl.befriend();
					befriendedCheryl = true;
				}
			};
			
			MiniScript nopeScript = new MiniScript("Umm... sorry, not really.") {
				public void run() {
					controller.addText(name);
					controller.addText("Cheryl: 'Oh, okay. Well, the girls, then?'");
					controller.addText("'...sorry.'");
					controller.addText("Cheryl: 'Ah well.'");
					controller.addProcessEventQueue();
					controller.run();	
					Finish();
				}				
			};
			
			controller.addDialog("Say to Cheryl", JacenScript, nopeScript);
		}

	}
	
	private void Finish(){
		controller.addText("She glances at her phone.");
		controller.addText("Cheryl: 'Oh hey, I gotta go. It was great talking with you, we should catch up later!'");
		
		if (befriendedCheryl){
			controller.addText("You have befriended Cheryl!");
		}
		
		controller.addProcessEventQueue();
		controller.run();
		
		metCheryl = true;
		
		new LoopScript().run();
	}
	
	private void getGossipFromCheryl(){
		controller.addText("Cheryl: 'I heard there's this second-year, Leo, who's really cute and smart, but who's been afraid of talking to girls ever since he asked this Lisa girl out and Lisa laughed in his face. In public. But he's cool, so if you're looking for a boyfriend, you should check him out.'");
		controller.addText("'Um-'");
		controller.addText("Cheryl: 'Or if you're more into girls, there's this other second-year, Meri. She's got a boyfriend now, I'm pretty sure that they're flexible about that kind of thing. I know that she's slept with a girl before.'");
		controller.addText("'Er-'");
		controller.addText("Cheryl: 'Oh, do you have someone already? That's alright. It's still useful to know what your options are, in case you'd break up or want to add some spice to your life.'");
		controller.addText("'So uhh...'");
		controller.addProcessEventQueue();
		controller.run();

		MiniScript thanks = new MiniScript("'Uhh... thanks.'"){
			public void run(){
				controller.addText(name);
				controller.addText("Cheryl: 'Hey, no problem.' She beams at you. 'I'm always happy to help my friends when it comes to matters of love. Just let me know how it goes for you guys, or gals, or threesomes, or whoever. I don't care, I'm open-minded!'");
				controller.addText("You'd kind of noticed that already.");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		MiniScript you = new MiniScript("'So, how about YOU?'"){
			public void run(){
				controller.addText(name);
				controller.addText("Cheryl: 'Oh, me?' She grins. 'Don't have anyone yet. Waiting for someone SPECIAL. Won't take just anyone.'");
				controller.addText("Yeah, you've got difficulties seeing this girl together with an ordinary person.");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		MiniScript know = new MiniScript("'Just how do you know of all this? Didn't you just get here?'"){
			public void run(){
				controller.addText(name);
				controller.addText("Cheryl: 'Oh, you need to know these things.' She beams, apparently happy that you asked. 'It's my special trade, finding out this kind of thing. If you hear anything you think I might be interested in, just let me know!'");
				controller.addText("As long as you don't mind half the school knowing it too...");
				controller.addProcessEventQueue();
				Finish();
			}
		};
		
		controller.addDialog("Say to Cheryl", thanks, you, know);
		controller.addProcessEventQueue();
		controller.run();
		


	}




}
