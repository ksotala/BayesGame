package bayesGame.levelcontrollers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChoiceMenu implements Iterable<ChoiceMenuChoice> {
	
	private List<ChoiceMenuChoice> choices;
	private String description;
	
	public ChoiceMenu(){
		choices = new ArrayList<ChoiceMenuChoice>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addChoice(ChoiceMenuChoice choice){
		choices.add(choice);
	}

	@Override
	public Iterator<ChoiceMenuChoice> iterator() {
		return choices.iterator();
	}
	
	public void setController(LevelController controller){
		for (ChoiceMenuChoice c : this){
			c.updateScriptController(controller);
		}
	}

}
