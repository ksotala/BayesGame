package bayesGame.world;

import bayesGame.levelcontrollers.ChoiceMenu;
import bayesGame.levelcontrollers.ChoiceMenuChoice;
import bayesGame.levelcontrollers.LectureScript;

public class Day {
	
	private final int DEFAULT_LENGTH = 5;
	private final int DEFAULT_CLASSES = 3;
	
	private int length;
	private int classes;
	
	private int number;
	private int timeofday;
	
	public Day(int number){
		length = DEFAULT_LENGTH;
		classes = DEFAULT_CLASSES;
		
		timeofday = 0;
		this.number = number;
	}
	
	public void advanceTimeOfDay(){
		timeofday++;
	}
	
	public int timeLeft(){
		return (length - timeofday);
	}
	
	public int classesLeft(){
		return Math.max(0, (classes - timeofday));
	}
	
	public ChoiceMenu getChoices(){
		ChoiceMenu menu = new ChoiceMenu();
			
		ChoiceMenuChoice lectureChoice = new ChoiceMenuChoice();
		lectureChoice.setDescription("Go to lectures");
		lectureChoice.setScript(new LectureScript());
		if (this.classesLeft() <= 0){
			lectureChoice.enabled = false;
		}
		menu.addChoice(lectureChoice);
		
		ChoiceMenuChoice selfStudy = new ChoiceMenuChoice();
		selfStudy.setDescription("Study in the library");
		selfStudy.enabled = false;
		menu.addChoice(selfStudy);
		
		ChoiceMenuChoice hangOut = new ChoiceMenuChoice();
		hangOut.setDescription("Hang out with friends");
		hangOut.enabled = false;
		menu.addChoice(hangOut);
		
		return menu;
	}
	
	
	public Day nextDay(){
		return new Day(number+1);
	}

	public int date() {
		return number;
	}

}
