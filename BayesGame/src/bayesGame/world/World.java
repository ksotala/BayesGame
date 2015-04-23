package bayesGame.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

import bayesGame.levelcontrollers.Script;
import bayesGame.levelcontrollers.events.SpokeAboutJacenEvent;

public class World {
	
	private static Day day;
	private static List<Script> futureEvents;
	private static List<Integer> futureTimes;
	
	public World(){
		day = new Day(1);
		futureEvents = new ArrayList<Script>();
		futureTimes = new ArrayList<Integer>();
		
	}

	public static Day getDate(){
		return day;
	}

	public static void insertEvent(int i, Script event) {
		if (i < 0){
			i = 0;
		}
		futureEvents.add(event);
		futureTimes.add(i);
	}
	
	public static void timeAdvanced(boolean daychanged){
		for (int x = 0; x < futureTimes.size(); x++){
			int i = futureTimes.get(x);
			if (i <= 0){
				Script s = futureEvents.get(x);
				s.run();
				futureTimes.remove(x);
				futureEvents.remove(x);
			}
			if (daychanged){
				i--;
				futureTimes.set(x, i);				
			}
		}
		
	}

		
}
