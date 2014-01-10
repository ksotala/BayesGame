package bayesGame.bayesbayes;

public class ReactionNode extends BayesNode {

	public final int reactionType;
	public final String flavorText;
	public final String topic;
	
	public ReactionNode(String description, int reactionType, String flavorText, String topic) {
		super(description);
		this.reactionType = reactionType;
		this.flavorText = flavorText;
		this.topic = topic;
	}
	
	public boolean equals(Object other){
		
		boolean result = false;
		
		if (other instanceof ReactionNode){
			ReactionNode theOther = (ReactionNode)other;
			result = (this.type.equals(theOther.type) && (this.topic.equals(theOther.topic)));
		}
		
		return result;
	}
	
	public int hashCode(){
		
		int result = 42;
		
		int a = type.hashCode();
		result = 37 * result + a;
		
		int b = topic.hashCode();
		result = 37 * result + b;
		
		return result;
	}


}
