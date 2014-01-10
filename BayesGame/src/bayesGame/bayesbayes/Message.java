package bayesGame.bayesbayes;

import java.util.HashMap;

import org.apache.commons.math3.fraction.Fraction;

public class Message {
	
	protected final Object[] scope;
	protected final Fraction[] message;
	protected final HashMap<Object,Integer> strides;
	protected final BayesNode sender;

	public Message(Object[] scope, Fraction[] message, HashMap<Object,Integer> strides, BayesNode sender) {
		this.scope = scope;
		this.message = message;
		this.strides = strides;
		this.sender = sender;
	}
	
	public Message(BayesNode sender){
		this(null, null, null, sender);
	}
	
	public Message(Object[] scope, Fraction[] message, HashMap<Object,Integer> strides){
		this(scope, message, strides, null);
	}
	
	/**
	 * Compare the specified object with this one for equality. Two messages are defined to be equal
	 * if they have the same sender: note that message contents are NOT taken into account in the
	 * comparison.
	 * 
	 * @param other the object to be compared
	 * @return true if the given object is also a Message and has the same sender
	 */
	public boolean equals(Object other){
		
		boolean result = false;
		
		if (other instanceof Message){
			Message theOther = (Message)other;
			result = (this.sender.equals(theOther.sender));
		}
		
		return result;
	}
	
	/**
	 * Compute a hash code.
	 * 
	 * @return the hash code
	 */
	public int hashCode(){
		
		return sender.hashCode();
	}

}
