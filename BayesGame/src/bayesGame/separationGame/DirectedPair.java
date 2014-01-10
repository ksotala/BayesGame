package bayesGame.separationGame;


public class DirectedPair {

	public BooleanNode node;
	public boolean up;
	
	DirectedPair(BooleanNode node, boolean up){
		
		this.node = node;
		this.up = up;
	}
	
	public boolean equals(Object other){
		boolean result = false;
		if (other instanceof DirectedPair){
			DirectedPair otherPair = (DirectedPair) other;
			result = (this.node == otherPair.node && this.up == otherPair.up);
		}
		
		return result;
	}
	
	public int hashCode(){
		
		int x = node.getId();
		int y = 0;
		
		if (up){
			y = 1;
		}
		
		return (41 * (41 + x) + y);
	}
	
}
