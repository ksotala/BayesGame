package bayesGame.world;

public class World {
	
	private static Day day;
	
	public World(){
		day = new Day(1);
	}

	public static Day getDate(){
		return day;
	}

		
}
