package bayesGame.bayesbayes;

import java.util.HashSet;
import java.util.Set;

public class OptionNodeOption {
	
	private final String description;
	private String positiveResponse;
	private String negativeResponse;
	private Set<String> requirements;
	
	private int timeSpent;
	

	public OptionNodeOption(String description) {
		this.description = description;
		this.requirements = new HashSet<String>();
		this.setTimeSpent(1);
	}


	public String getPositiveResponse() {
		return positiveResponse;
	}


	public void setPositiveResponse(String positiveResponse) {
		this.positiveResponse = positiveResponse;
	}


	public String getNegativeResponse() {
		return negativeResponse;
	}


	public void setNegativeResponse(String negativeResponse) {
		this.negativeResponse = negativeResponse;
	}


	public void addRequirement(String requirement) {
		requirements.add(requirement);
	}
	
	public boolean hasRequirements(){
		return !requirements.isEmpty();
	}

	public String getDescription() {
		return description;
	}


	public int getTimeSpent() {
		return timeSpent;
	}


	public void setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
	}


	
	

	

}
