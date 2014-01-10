package bayesGame.separationGame;

import org.apache.commons.collections15.Factory;

public class TravelEdge {
	
	 public int id;
	 public boolean canTravelDownwards = true;
	 public boolean canTravelUpwards = false;

	 public TravelEdge(int id){
		 
		 this.id = id;
		 
	 }
	 
	 public TravelEdge(int id, boolean canTravelUpwards){
		 
		 this.id = id;
		 this.canTravelUpwards = canTravelUpwards;
		 
	 }
	 
	 public TravelEdge(int id, boolean canTravelDownwards, boolean canTravelUpwards){
		 
		 this.id = id;
		 this.canTravelDownwards = canTravelDownwards;
		 this.canTravelUpwards = canTravelUpwards;
		 
	 }
	 
	 
	public boolean equals(Object other){
			
		boolean result = false;
		if (other instanceof TravelEdge){
			TravelEdge theOther = (TravelEdge)other;
			result = (this.id == theOther.id);
		}
			
		return result;
	}
		
	public int hashCode(){
			
		return id;
	}
	 
	 
	 
	 
	 
		public static class TravelEdgeFactory implements Factory<TravelEdge>{
			
			private static int nodeCount = 10;
			private static TravelEdgeFactory instance = new TravelEdgeFactory();

			public TravelEdge create() {
				TravelEdge bn = new TravelEdge(nodeCount);
				nodeCount++;
				return bn;
			}
			
			public static TravelEdgeFactory getInstance(){
				return instance;
			}
			
			
			
			
		}
	 
	 
}
